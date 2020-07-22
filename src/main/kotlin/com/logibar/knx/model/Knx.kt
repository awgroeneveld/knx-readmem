package com.logibar.knx.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import org.intellij.lang.annotations.Language
import java.awt.image.renderable.ParameterBlock

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
    val applicationPrograms:List<ApplicationProgram>,
    @JacksonXmlElementWrapper
    val languages: List<MyLanguage>
    ) {

}

data class MyLanguage(
    val identifier: String,
    val translationUnit: TranslationUnit
){

}

data class TranslationUnit(
    val refId: String,
    @JsonProperty("TranslationElement")
    @JacksonXmlElementWrapper(useWrapping = false)
    val translationElements: List<TranslationElement>
)

data class TranslationElement(
    val refId: String,
    @JsonProperty("Translation")
    @JacksonXmlElementWrapper(useWrapping = false)
    val translations: List<Translation>
)

data class Translation(
    val attributeName: String,
    val text: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ApplicationProgram(val id: String, val static: Static, val dynamic: Dynamic)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Static(
    val code:Code,
    @JacksonXmlElementWrapper
    val parameterTypes:List<ParameterType>,
    @JacksonXmlProperty(localName = "Parameters")
    val parametersAndUnions:ParametersAndUnions,
    @JacksonXmlElementWrapper
    val parameterRefs: List<ParameterRef>,

    val comObjectTable:ComObjectTable,
    @JacksonXmlElementWrapper
    val comObjectRefs: List<ComObjectRef>,
    val addressTable: AddressTable

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Dynamic(
    val channel: Channel
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Channel(
    val name: String,
    val text: String,
    val number: Int,
    val id: String
)


data class ParameterBlock(

)



data class AddressTable(
    val codeSegment: String,
    val offset: Int,
    val maxEntries: Int
) {

}

data class AssociationTable(
    val codeSegment: String,
    val offset: Int,
    val maxEntries: Int
) {

}

data class ComObjectTable(
    val codeSegment: String,
    val offset: Int,
    @JacksonXmlProperty(localName = "ComObject")
    @JacksonXmlElementWrapper(useWrapping = false)
    val comObjects: List<ComObject>
) {

}

data class ComObjectRef(
    val id: String,
    val refId: String,
    val tag: Int,
    val text: String?,
    val functionText: String?,
    val readFlag: EnabledDisabled?,
//    val writeFlag: EnabledDisabled,
//    val communicationFlag: EnabledDisabled,
    val transmitFlag:EnabledDisabled?
//    val updateFlag: EnabledDisabled,
//    val readOnInitFlag: EnabledDisabled
)

data class ComObject(
    val id: String,
    val name: String,
    val text: String,
    val number: Int,
    val functionText: String,
    val objectSize: String,
    val readFlag: EnabledDisabled,
    val writeFlag: EnabledDisabled,
    val communicationFlag: EnabledDisabled,
    val transmitFlag:EnabledDisabled,
    val updateFlag: EnabledDisabled,
    val readOnInitFlag: EnabledDisabled
)

enum class EnabledDisabled{
    Enabled, Disabled
}

data class ParameterRef(
    val id: String,
    val refId: String,
    val tag: Int,
    val displayOrder: Int,
    val value: Int?,
    val text: String?,
    val access: Access?,
    val name: String?
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
    var unions: List<Union> = listOf()
        get() = field
        set(value){
            field=field+value
        }
}
@JsonIgnoreProperties(ignoreUnknown = true)
data class Parameter (
    val id:String,
    val name: String,
    val parameterType: String,
    val text: String,
    val access: Access?,
    val value: Int?,
    val memory:Memory?,
    val englishText: String?=null
)

data class Union(
    val sizeInBit: Int,
    val memory: Memory,
    @JacksonXmlProperty(localName = "Parameter")
    @JacksonXmlElementWrapper(useWrapping = false)
    val parameters: List<ExtendedParameter>
)

data class ExtendedParameter (
    val id:String,
    val name: String,
    val parameterType: String,
    val text: String,
    val access: Access?,
    val value: Int?,
    val memory:Memory?,
    val offset: Int,
    val bitOffset: Int,
    val englishText: String?,
    val defaultUnionParameter: Boolean
)

data class Memory (val codeSegment: String, val offset:Int, val bitOffset:Int)

enum class ParamValueType {
    unsignedInt
}

enum class Access{
    None, Read, ReadWrite
}
