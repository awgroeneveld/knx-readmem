package com.logibar.knx

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.logibar.knx.model.Knx
import com.logibar.knx.model.ParametersAndUnions
import org.junit.jupiter.api.Test
import java.util.*
import javax.xml.stream.XMLInputFactory
import kotlin.experimental.xor
import kotlin.streams.toList

class ReadXml {
    @Test
    fun readXML() {
//    val inputFactory = XMLInputFactory.newFactory()
//    inputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false)
//    val myDeserializerModule=SimpleModule().apply {
//        addDeserializer(ParametersAndUnions::class.java, ParametersAndUnionsDeserializer())
//    }
        val mapper = XmlMapper().apply {
            registerModule(KotlinModule())
            registerModule(JaxbAnnotationModule())
//        registerModule(myDeserializerModule)
            configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        }
//        val mapper=XmlMapper().registerModule(KotlinModule())
        val f = this::class.java.getResourceAsStream("/dimmer.xml")
        val knx = mapper.readValue(f, Knx::class.java)


        val bytePos = 0x4546
        val device: Byte = 31
        val sw: Byte = 30
        val changes = device.xor(sw)

        val bitset = BitSet.valueOf(ByteArray(1) { changes })
        val indexes = bitset.stream()
            .toList()
        val memAddresses = indexes.map { bytePos + it }
val manufacturer=knx.manufacturerData.manufacturer
        val prog = manufacturer.applicationPrograms.first()
        val codeSegment = prog.static.code.absoluteSegments.sortedByDescending { segment -> segment.address }
            .first { it.address <= memAddresses.min()!! }
        val offset=bytePos-codeSegment.address
        val offsetBits = memAddresses.map { it - codeSegment.address }

        val translations=manufacturer.languages.first().translationUnit.translationElements
        val parameters =
            prog.static.parametersAndUnions.parameters
                .filter { it.memory != null && it.memory!!.codeSegment == codeSegment.id }
                .filter { it.memory!!.offset==offset}
        val translatedParameters=parameters
            .map{param->param.copy(englishText = translations.firstOrNull{
                    translationElement -> translationElement.refId==param.parameterType}
                ?.translations
                ?.sortedBy { translation ->  translation.attributeName }
                ?.firstOrNull()
                ?.text)
            }

        val t=parameters.map{param->translations.firstOrNull{
                translationElement -> translationElement.refId==param.parameterType}}

        val unions =
            prog.static.parametersAndUnions.unions
                .filter {it.memory.codeSegment == codeSegment.id }
                .filter { it.memory.offset==offset}
        println(parameters)
        println(unions)
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
