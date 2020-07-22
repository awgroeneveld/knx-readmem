package com.logibar.knx.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(namespace = "http://knx.org/xml/project/20", localName = "KNX")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Knx(@JacksonXmlProperty(localName = "ManufacturerData") val manufacturerData:ManufacturerData)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ManufacturerData( @JacksonXmlProperty(localName = "Manufacturer") val manufacturer: Manufacturer) {

}
@JsonIgnoreProperties(ignoreUnknown = true)
data class Manufacturer(
    @JacksonXmlProperty(localName = "RefId")
    val refId:String,
    @JacksonXmlElementWrapper
    val applicationPrograms:List<ApplicationProgram>) {

}

@JsonIgnoreProperties(ignoreUnknown = true)
data class ApplicationProgram(val id: String, val static: Static)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Static(
    val code:Code,
    @JacksonXmlElementWrapper
    val parameterTypes:List<ParameterType>,
    @JacksonXmlProperty(localName = "Parameters")
    val parametersAndUnions:ParametersAndUnions

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Code(
    @JacksonXmlProperty(localName = "AbsoluteSegment")
    @JacksonXmlElementWrapper(useWrapping = false)
    val absoluteSegments: List<AbsoluteSegment>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class AbsoluteSegment(val id: String, val address:Int, val size:Int)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ParameterType(val id: String, val name: String, val typeNumber:TypeNumber?, val typeRestriction: TypeRestriction?)

//@JsonIgnoreProperties(ignoreUnknown = true)
data class TypeRestriction(
    val base:String,
    val sizeInBit: Int,
    @JacksonXmlProperty(localName = "Enumeration")
    @JacksonXmlElementWrapper(useWrapping = false)
    val enumerations:List<Enumeration>
) {

}

data class Enumeration(val text:String, val value:Int, val id: String, val displayOrder: Int, val binaryValue: String?)

data class TypeNumber (val sizeInBit:Int, val type: ParamValueType, val minInclusive: Long, val maxInclusive: Long ) {

}

//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonDeserialize(using=ParametersAndUnionsDeserializer::class)
 class ParametersAndUnions() {
    //    @JacksonXmlProperty()
    @JacksonXmlProperty(localName = "Parameter")
    @JacksonXmlElementWrapper(useWrapping = false)
    var parameters: List<Parameter> =listOf()
        get() = field
        set(value){
            field=field+value
        }

        @JacksonXmlProperty(localName = "Union")
    @JacksonXmlElementWrapper(useWrapping = false)
    private var unions: List<Union> = listOf()
        get() = field
        set(value){
            field=field+value
        }
}

//class ParametersAndUnionsDeserializer(vc:Class<*>?): StdDeserializer<ParametersAndUnions>(vc) {
//    constructor():this(null)
//
//    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ParametersAndUnions {
//        val node=p.readValuesAs(JsonNode::class.java)
//        val nodes=node.asSequence().toList()
//        val node2=p.codec.readValue(p, JsonNode::class.java)
//        return ParametersAndUnions(listOf(), listOf())
//    }
//
//}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Parameter (val id:String, val name: String, val parameterType: String, val text: String, val access: Access?, val value: Int?, val memory:Memory?)

data class Union(
    val sizeInBit: Int,
    val memory: Memory,
    @JacksonXmlProperty(localName = "Parameter")
    @JacksonXmlElementWrapper(useWrapping = false)
    val parameters: List<Parameter>
)

data class Memory (val codeSegment: String, val offset:Int, val bitOffset:Int)

enum class ParamValueType {
    unsignedInt
}

enum class Access{
    None, Read
}
