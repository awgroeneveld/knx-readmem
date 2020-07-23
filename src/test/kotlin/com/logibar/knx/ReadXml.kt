package com.logibar.knx


import com.logibar.knx.model.Knx
import com.logibar.knx.model.Memory
import com.logibar.knx.model.ParameterBlock
import com.logibar.knx.model.TranslationSet
import org.junit.jupiter.api.Test
import java.awt.print.Book
import java.io.StringWriter
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
        val codeSegment = prog.static!!.code!!.absoluteSegments!!.sortedByDescending { segment -> segment.address }
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
        println(parameters)
//        println(unions)
        val channel = prog.dynamic!!.channel!!
        channel.items!!.filter { it is ParameterBlock }
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
