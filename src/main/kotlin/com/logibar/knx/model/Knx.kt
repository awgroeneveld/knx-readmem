package com.logibar.knx.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonValue
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
    val parameterTypes:List<ParameterType>

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

@JsonIgnoreProperties(ignoreUnknown = true)
data class Enumeration(val text:String, val value:Int, val id: String, val displayOrder: Int)

data class TypeNumber (val sizeInBit:Int, val type: ParamValueType, val minInclusive: Long, val maxInclusive: Long ) {

}

enum class ParamValueType {
    unsignedInt
}
