package com.logibar.knx


import com.logibar.knx.model.AbsoluteSegment
import com.logibar.knx.model.Knx
import com.logibar.knx.model.Memory
import com.logibar.knx.model.ParameterBlock
import com.logibar.knx.model.TranslationSet
import com.logibar.knx.util.ParamaterMemoryUtil
import org.junit.jupiter.api.Test
import tuwien.auto.calimero.IndividualAddress
import tuwien.auto.calimero.link.KNXNetworkLinkIP
import tuwien.auto.calimero.link.medium.TPSettings
import tuwien.auto.calimero.mgmt.ManagementClientImpl
import java.awt.print.Book
import java.io.StringWriter
import java.lang.Exception
import java.lang.Integer.min
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.util.*
import java.util.regex.Pattern
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import javax.xml.bind.ValidationEvent
import javax.xml.bind.ValidationEventHandler
import javax.xml.bind.ValidationEventLocator
import javax.xml.bind.helpers.DefaultValidationEventHandler
import javax.xml.stream.XMLInputFactory
import kotlin.experimental.xor
import kotlin.streams.toList

class ReadXml {

    class MyValidationEventHandler() : ValidationEventHandler {
        val defaultValidationEventHandler = DefaultValidationEventHandler()
        val ignoredNodes = listOf("LoadProcedures", "Options")
        val regex = Pattern.compile(".*local:\"(.*)\"\\)")

        override fun handleEvent(event: ValidationEvent): Boolean {
            var newEvent = event
            val matcher = regex.matcher(event.message)
            if (matcher.find()) {
                val local = matcher.group(1)
                if (ignoredNodes.contains(local)) {
                    newEvent = object : ValidationEvent {
                        override fun getMessage(): String {
                            return event.message
                        }

                        override fun getLinkedException(): Throwable {
                            return event.linkedException
                        }

                        override fun getLocator(): ValidationEventLocator {
                            return event.locator
                        }

                        override fun getSeverity(): Int {
                            return ValidationEvent.WARNING
                        }
                    }
                }
            }
            return defaultValidationEventHandler.handleEvent(newEvent)

        }

    }

    fun readCodeSegment(codeSegment: AbsoluteSegment): BitSet {
        println("Reading segment starting at ${codeSegment.address}")
        val buf = ByteBuffer.allocate(codeSegment.size!!)
        val lineAddress = IndividualAddress("1.1.10")
        val localHost = "172.19.1.51"
        val gateway = "172.19.4.10"
        val networkLink = KNXNetworkLinkIP.newTunnelingLink(
            InetSocketAddress(localHost, 0),
            InetSocketAddress(gateway, 3671),
            false,
            TPSettings.TP1
        )
        val client = ManagementClientImpl(networkLink)
        val dest = client.createDestination(lineAddress,true)
        var startAddress = codeSegment.address!!
        val codeSegmentEndAddress = codeSegment.endAddress()
        val numBytes=4
        var bytes = min(codeSegmentEndAddress - startAddress, numBytes)
        try {
            while (bytes != 0) {
                val x = client.readMemory(dest, startAddress, bytes)
                if (x.size < bytes) {
                    throw Exception("Read less bytes than expected in segment: ${codeSegment.id}, start address ${startAddress}, bytes $bytes")
                }

                buf.put(startAddress - codeSegment.address!!, x)
                startAddress += bytes
                Thread.sleep(50)
                bytes = min(codeSegmentEndAddress - startAddress, numBytes)
            }
        } catch (e: Throwable) {
            println(e.message)
        } finally {
            dest.destroy()
            client.detach()
        }
        return BitSet.valueOf(buf)
    }

    @Test
    fun readXML() {
        val context = JAXBContext.newInstance(Knx::class.java);
        val f = this::class.java.getResourceAsStream("/dimmer.xml")

        val unmarshaller = context.createUnmarshaller()
        unmarshaller.eventHandler = (MyValidationEventHandler());
        val knx = unmarshaller.unmarshal(f) as Knx

//        marshall(context, knx)

//        val decoded=Base64.getDecoder().decode("MAUAAAEAAAABAAAAAQABAf//AYABAQEBCAAIIAAAAAAAAQD/EAAAAAAAAAAAAAAAAAAAAAAAAAz/DP8MAAAAAAAAAAAAAAAAAAAAAAAAAAH//wGAAQEBAQgACCAAAAAAAAEA/xAAAAAAAAAAAAAAAAAgAAAAAAAM/wz/DAAAAAAAAAAAAAAAAAAAAAAAAAAB//8BgAEBAQEIAAggAAAAAAABAP8QAAAAAAAAAAAAAAAAIAAAAAAADP8M/wwAAAAAAAAAAAAAAAAAAAAAAAAAAf//AYABAQEBCAAIIAAAAAAAAQD/EAAAAAAAAAAAAAAAACAAAAAAAAz/DP8MAAAAAAAAAAAAAAAAAAAAAAAAAAQAAAAAAQIDBAUGBwggQGCAoMDg/wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA5AFd6Q/8AAQAA")

        val bytePos = 0x4546
        val device: Byte = 31
        val sw: Byte = 30
        val changes = device.xor(sw)

        val bitset = BitSet.valueOf(ByteArray(1) { changes })

        val indexes = bitset.stream()
            .toList()
        val memAddresses = indexes.map { bytePos + it }
        val manufacturer = knx.manufacturerData!!.manufacturer
        val prog = manufacturer!!.applicationPrograms!!.first()
        val codeSegments = prog.static!!.code!!.absoluteSegments!!

        val inputByCodeSegment = prog.static!!.parametersAndUnions!!.parameterOrUnions!!.filter { it.memory!=null }.map{it.memory!!.codeSegment!!}.distinct()
            .map { it to readCodeSegment(it) }
            .toMap()


        val codeSegment = codeSegments.sortedByDescending { segment -> segment.address }
            .first { it.address!! <= memAddresses.min()!! }
        val offset = bytePos - codeSegment.address!!
        val offsetBits = memAddresses.map { it - codeSegment.address!! }

        val translationSet =
            TranslationSet(manufacturer.languages!!.first().translationUnit!!.translationElements!!.map { it.refId!! to it }
                .toMap())
        val parameters =
            prog.static!!.parametersAndUnions!!.parameterOrUnions!!
                .filter { it.memory != null && (it.memory as Memory).codeSegment!!.id == codeSegment.id }
                .filter { it.memory!!.offset == offset }
//
//        val t=parameters.map{param->translations.firstOrNull{
//                translationElement -> translationElement.refId==param.parameterType}}
//
//        val unions =
//            prog.static.parametersAndUnions.unions
//                .filter {it.memory.codeSegment == codeSegment.id }
//                .filter { it.memory.offset==offset}
        val pmu = ParamaterMemoryUtil(knx).paramaterMemoryById

        pmu.values.forEach { parameterMemory-> val bitSet=inputByCodeSegment[parameterMemory.segment]!!
            val extractedBitSet=bitSet[parameterMemory.relativeOffset, parameterMemory.relativeOffset+parameterMemory.numberOfBits+1]
            val value=if (extractedBitSet.length()==0) 0 else extractedBitSet.toLongArray()[0].toInt()
            parameterMemory.value=value
        }

        val deviceChanges=pmu.entries.filter { it.value.defaultValue!=it.value.value }

        val x = LinkedList(pmu.values.sortedBy { it.startPosInBits() }).mapIndexed { index, it -> index to it }
        val nonconsecutive =
            x.filter { if (it.first == 0) false else it.second.startPosInBits() != x[it.first - 1].second.endPosInBits() }
        val endOnByte = x.filter { it.second.endPosInBits() % 8 == 0L }
        val startOnByte = x.filter { it.second.startPosInBits() % 8 == 0L }

//        val startOnByteNew = pmu.values.sta

        println(parameters)
//        println(unions)
        val channel = prog.dynamic!!.channel!!
        channel.items!!
            .forEach { println(it.toLogString(0, translationSet)) }
    }

    private fun marshall(context: JAXBContext, knx: Knx) {
        val writer = StringWriter()
        val marshaller = context.createMarshaller()
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(knx, writer)
        println(writer.toString())
    }

    @Test
    fun doIt() {
        val bytePos = 0x4546
        val device: Byte = 1
        val sw: Byte = 3
        val changes = device.xor(sw)

        val bitset = BitSet.valueOf(ByteArray(1) { changes })
        val items = bitset.stream()
            .toList()

    }
}
