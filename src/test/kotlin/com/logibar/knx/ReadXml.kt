package com.logibar.knx


import com.logibar.knx.model.AbsoluteSegment
import com.logibar.knx.model.Knx
import com.logibar.knx.model.Memory
import com.logibar.knx.model.TranslateExtensions.translate
import com.logibar.knx.model.TranslationSet
import com.logibar.knx.model.UIElementTranslator
import com.logibar.knx.model.UiElementDefaultValuesProvider
import com.logibar.knx.util.ParamaterMemoryUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import tuwien.auto.calimero.IndividualAddress
import tuwien.auto.calimero.link.KNXNetworkLinkIP
import tuwien.auto.calimero.link.medium.TPSettings
import tuwien.auto.calimero.mgmt.ManagementClientImpl
import java.io.StringWriter
import java.lang.Integer.min
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.util.*
import java.util.regex.Pattern
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.ValidationEvent
import javax.xml.bind.ValidationEventHandler
import javax.xml.bind.ValidationEventLocator
import javax.xml.bind.helpers.DefaultValidationEventHandler
import kotlin.experimental.and
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

    fun readCodeSegment(codeSegment: AbsoluteSegment): ByteArray {
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
        val dest = client.createDestination(lineAddress, true)
        var startAddress = codeSegment.address!!
        val codeSegmentEndAddress = codeSegment.endAddress()
        val numBytes = 4
        var bytes = min(codeSegmentEndAddress - startAddress, numBytes)
        try {
            while (bytes != 0) {
                val x = client.readMemory(dest, startAddress, bytes)
                if (x.size < bytes) {
                    throw Exception("Read less bytes than expected in segment: ${codeSegment.id}, start address ${startAddress}, bytes $bytes")
                }

                buf.put( x)
                startAddress += bytes
                Thread.sleep(50)
                bytes = min(codeSegmentEndAddress - startAddress, numBytes)
            }
        } catch (e: Throwable) {
            println(e.message)
        } finally {
            dest.close()
//            dest.destroy()
            client.close()
            networkLink.close()
        }
        return buf.array()
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

        val codeSegmentsForParameters=prog.static!!.parametersAndUnions!!.parameterOrUnions!!.filter { it.memory != null }
            .map { it.memory!!.codeSegment!! }
        val inputByCodeSegment =codeSegmentsForParameters
            .distinct()
            .map { it to readCodeSegment(it) }
//            .map { it to it.data } //
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

        pmu.values.forEach { parameterMemory ->
            val bar = inputByCodeSegment[parameterMemory.segment]!!
            val extractedBytes =
                selectBits(bar, parameterMemory.bitOffset, parameterMemory.numberOfBits, parameterMemory.relativeOffset)

            val value = if (extractedBytes.size == 0) 0 else fromByteArray(extractedBytes)
            parameterMemory.value = value
        }

        val deviceChanges = pmu.entries.filter { it.value.defaultValue != it.value.value }

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


    @Test
    fun showUI() {
        val context = JAXBContext.newInstance(Knx::class.java);
        val f = this::class.java.getResourceAsStream("/dimmer.xml")

        val unmarshaller = context.createUnmarshaller()
        unmarshaller.eventHandler = (MyValidationEventHandler());
        val knx = unmarshaller.unmarshal(f) as Knx

        val manufacturer = knx.manufacturerData!!.manufacturer
        val prog = manufacturer!!.applicationPrograms!!.first()
        val codeSegments = prog.static!!.code!!.absoluteSegments!!

        val codeSegmentsForParameters=prog.static!!.parametersAndUnions!!.parameterOrUnions!!.filter { it.memory != null }
            .map { it.memory!!.codeSegment!! }
        val inputByCodeSegment =codeSegmentsForParameters
            .distinct()
            .map { it to it.data!! }
            .toMap()

        val translationsById =
            manufacturer.languages!!.first().translationUnit!!.translationElements!!.map { it.refId!! to it }
                .toMap()
        val translationSet =
            TranslationSet(translationsById)



        val pmu = ParamaterMemoryUtil(knx).paramaterMemoryById

        pmu.values.forEach { parameterMemory ->
            val bar = inputByCodeSegment[parameterMemory.segment]!!
            val extractedBytes =
                selectBits(bar, parameterMemory.bitOffset, parameterMemory.numberOfBits, parameterMemory.relativeOffset)

            val value = if (extractedBytes.size == 0) 0 else fromByteArray(extractedBytes)
            parameterMemory.value = value
        }

        val channel = prog.dynamic!!.channel!!
        prog.static!!.translate(translationSet)
        channel.accept(UIElementTranslator(translationSet))

        val defaultValuesProvider=UiElementDefaultValuesProvider()
        channel.accept(defaultValuesProvider)

        channel.items!!
            .forEach { println(it.toLogString(0, translationSet)) }
    }


    fun fromByteArray(pbytes: ByteArray): Int {
        val bytes = if (pbytes.size < 4) ByteArray(4).apply {
            set(3, if (pbytes.isNotEmpty()) pbytes[0] else 0)
            set(2, if (pbytes.size >= 2) pbytes[1] else 0)
            set(1, if (pbytes.size >= 3) pbytes[2] else 0)
            set(0, if (pbytes.size >= 4) pbytes[3] else 0)
        } else pbytes
        return bytes[0].toInt() and 0xFF shl 24 or
                (bytes[1].toInt() and 0xFF shl 16) or
                (bytes[2].toInt() and 0xFF shl 8) or
                (bytes[3].toInt() and 0xFF shl 0)
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

    fun powerOf2(x: Int) = if (x == 0) 1 else 2 shl x - 1


    @Test
    fun testPowerOf2() {
        Assertions.assertEquals(1, powerOf2(0))
        Assertions.assertEquals(2, powerOf2(1))
        Assertions.assertEquals(4, powerOf2(2))
        Assertions.assertEquals(8, powerOf2(3))
        Assertions.assertEquals(256, powerOf2(8))
    }

    @Test
    fun bytearrays() {
        val x = ByteArray(4)
        x[0] = 4.toByte()
        x[1] = 255.toByte()
        x[2] = 255.toByte()
        x[3] = 255.toByte()
        val y = BitSet.valueOf(x)
        //y.flip(1)
        val a = y[0, 1]
        val b = y[1, 1]
        val c = y[0, 2]
        val d = y[5, 6]
        val e = y[8 - 4, 8 - 1]
        val eb = e.toByteArray()
        println(eb[0].toInt() and 0xFF)

        val start = 0
        val count = 2
        val and = andFactor(start, count)

        System.out.println("b and 0xFF")
    }

    @Test
    fun testSelectDualByteInArray() {
        Assertions.assertEquals(
            listOf(9),
            selectBits(arrayOf(4.toByte(), 128.toByte()).toByteArray(), 5, 4).map { it.toInt() }
        )
        Assertions.assertEquals(
            listOf(32, 8),
            selectBits(arrayOf(4.toByte(), 1.toByte()).toByteArray(), 5, 11).map { it.toInt() }
        )
//        Assertions.assertEquals(2, selectBits(arrayOf(4.toByte()).toByteArray(), 5, 2)[0])
//        Assertions.assertEquals(3, selectBits(arrayOf(12.toByte()).toByteArray(), 4, 2)[0])
//        Assertions.assertEquals(1, selectBits(arrayOf(128.toByte()).toByteArray(), 0, 1)[0])
    }


    @Test
    fun testSelectSingleByteInArray() {
        Assertions.assertEquals(1, selectBits(arrayOf(4.toByte()).toByteArray(), 5, 1)[0])
        Assertions.assertEquals(2, selectBits(arrayOf(4.toByte()).toByteArray(), 5, 2)[0])
        Assertions.assertEquals(3, selectBits(arrayOf(12.toByte()).toByteArray(), 4, 2)[0])
        Assertions.assertEquals(1, selectBits(arrayOf(128.toByte()).toByteArray(), 0, 1)[0])
    }


    @Test
    fun testSelectBits() {
        Assertions.assertEquals(1, selectBits(4, 5, 1))
        Assertions.assertEquals(2, selectBits(4, 5, 2))
        Assertions.assertEquals(3, selectBits(12, 4, 2))
        Assertions.assertEquals(1, selectBits(128.toByte(), 0, 1))
//        Assertions.assertEquals(4, selectBits(12, 5, 1))
//        Assertions.assertEquals(4, selectBits(4, 5, 2))
    }

    @Test
    fun testAndFactor() {
        Assertions.assertEquals(4, andFactor(5, 1))
        Assertions.assertEquals(6, andFactor(5, 2))
        Assertions.assertEquals(7, andFactor(5, 3))
        Assertions.assertEquals(12, andFactor(4, 2))
    }


    fun selectBits(bytes: ByteArray, offsetBits: Int, numberOfBits: Int, startIndex: Int = 0): ByteArray {
        var index = startIndex
        val leftShift =
            if (offsetBits + numberOfBits >= 16) (8 - offsetBits)
            else (if (offsetBits + numberOfBits > 8) (offsetBits + numberOfBits) % 8 else 0)
        val byteCount = 1 + (numberOfBits - 1) / 8
        val buf = ByteBuffer.allocate(byteCount)
        var handledBits = min(8 - offsetBits, numberOfBits)
        var prevValue: Int? = selectBits(bytes[index++], offsetBits, handledBits)
        while (handledBits < numberOfBits) {
            val remainingBits = numberOfBits - handledBits
            val bitCount = if (remainingBits < 8) remainingBits else 8
            val byte = bytes[index++]
            buf.put(((prevValue!! shl leftShift) + selectBits(byte, 0, leftShift)).toByte())
            prevValue = if (bitCount <= offsetBits) null else selectBits(byte, offsetBits, bitCount - offsetBits)
            handledBits += bitCount
        }
        if (prevValue != null) {
            buf.put((prevValue shl leftShift).toByte())
        }
        return buf.array()

    }

    private fun selectBits(byte: Byte, offsetBit: Int, count: Int) =
        ((byte.toInt() and andFactor(offsetBit, count)) shr (8 - offsetBit - count))

    private fun andFactor(start: Int, count: Int) = (powerOf2(8 - start) - powerOf2(8 - start - count))
}
