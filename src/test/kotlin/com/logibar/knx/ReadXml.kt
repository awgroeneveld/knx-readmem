package com.logibar.knx

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.logibar.knx.model.Knx
import com.logibar.knx.model.ParametersAndUnions
import org.junit.jupiter.api.Test
import javax.xml.stream.XMLInputFactory

class ReadXml {
@Test
    fun readXML() {
//    val inputFactory = XMLInputFactory.newFactory()
//    inputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false)
//    val myDeserializerModule=SimpleModule().apply {
//        addDeserializer(ParametersAndUnions::class.java, ParametersAndUnionsDeserializer())
//    }
    val mapper=XmlMapper().apply {
        registerModule(KotlinModule())
        registerModule(JaxbAnnotationModule())
//        registerModule(myDeserializerModule)
        configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }
//        val mapper=XmlMapper().registerModule(KotlinModule())
        val f= this::class.java.getResourceAsStream("/dimmer.xml")
        val o=mapper.readValue(f,Knx::class.java)
        val y=o.manufacturerData.manufacturer.applicationPrograms.map{
                applicationProgram -> applicationProgram.static.parameterTypes.filter { it.id=="M-0008_A-301A-11-134F-O000A_PT-Verh.5FBuswdk.5FmitZeitdimmer" }
        }
        println(y.size)
        println(o)
    }
}
