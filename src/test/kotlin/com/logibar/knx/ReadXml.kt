package com.logibar.knx


import com.logibar.knx.model.Knx
import org.junit.jupiter.api.Test
import java.awt.print.Book
import java.io.StringWriter
import java.util.*
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import javax.xml.stream.XMLInputFactory
import kotlin.experimental.xor
import kotlin.streams.toList
class ReadXml {
    @Test
    fun readXML() {
         val context = JAXBContext.newInstance(Knx::class.java);
        val f = this::class.java.getResourceAsStream("/dimmer.xml")
        val knx = context.createUnmarshaller().unmarshal(f) as Knx

        val writer=StringWriter()
        val marshaller=context.createMarshaller()
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(knx, writer)
        println(writer.toString())

//        val decoded=Base64.getDecoder().decode("MAUAAAEAAAABAAAAAQABAf//AYABAQEBCAAIIAAAAAAAAQD/EAAAAAAAAAAAAAAAAAAAAAAAAAz/DP8MAAAAAAAAAAAAAAAAAAAAAAAAAAH//wGAAQEBAQgACCAAAAAAAAEA/xAAAAAAAAAAAAAAAAAgAAAAAAAM/wz/DAAAAAAAAAAAAAAAAAAAAAAAAAAB//8BgAEBAQEIAAggAAAAAAABAP8QAAAAAAAAAAAAAAAAIAAAAAAADP8M/wwAAAAAAAAAAAAAAAAAAAAAAAAAAf//AYABAQEBCAAIIAAAAAAAAQD/EAAAAAAAAAAAAAAAACAAAAAAAAz/DP8MAAAAAAAAAAAAAAAAAAAAAAAAAAQAAAAAAQIDBAUGBwggQGCAoMDg/wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA5AFd6Q/8AAQAA")

        val bytePos = 0x4546
        val device: Byte = 31
        val sw: Byte = 30
        val changes = device.xor(sw)

        val bitset = BitSet.valueOf(ByteArray(1) { changes })
        val indexes = bitset.stream()
            .toList()
        val memAddresses = indexes.map { bytePos + it }
       // val manufacturer=knx.manufacturerData.manufacturer
//        val prog = manufacturer.applicationPrograms.first()
//        val codeSegment = prog.static.code.absoluteSegments.sortedByDescending { segment -> segment.address }
//            .first { it.address <= memAddresses.min()!! }
//        val offset=bytePos-codeSegment.address
//        val offsetBits = memAddresses.map { it - codeSegment.address }
//
//        val translations=manufacturer.languages.first().translationUnit.translationElements
//        val parameters =
//            prog.static.parametersAndUnions.parameters
//                .filter { it.memory != null && it.memory!!.codeSegment == codeSegment.id }
//                .filter { it.memory!!.offset==offset}
//        val translatedParameters=parameters
//            .map{param->param.copy(englishText = translations.firstOrNull{
//                    translationElement -> translationElement.refId==param.parameterType}
//                ?.translations
//                ?.sortedBy { translation ->  translation.attributeName }
//                ?.firstOrNull()
//                ?.text)
//            }
//
//        val t=parameters.map{param->translations.firstOrNull{
//                translationElement -> translationElement.refId==param.parameterType}}
//
//        val unions =
//            prog.static.parametersAndUnions.unions
//                .filter {it.memory.codeSegment == codeSegment.id }
//                .filter { it.memory.offset==offset}
//        println(parameters)
//        println(unions)
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
