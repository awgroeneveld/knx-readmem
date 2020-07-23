package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAnyElement
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlSchemaType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "KNX")
data class Knx(
    @field:XmlElement(name = "ManufacturerData")
    val manufacturerData:ManufacturerData?=null,
    @XmlAttribute(name="CreatedBy")
    val createdBy: String?=null
){

}

@XmlAccessorType(XmlAccessType.FIELD)
data class ManufacturerData(
    @field:XmlElement(name = "Manufacturer")
    val manufacturer: Manufacturer?=null
)

@XmlAccessorType(XmlAccessType.FIELD)
data class Manufacturer(
    @XmlAttribute(name = "RefId")
    val refId: String?=null,
    @field:XmlElementWrapper(name="ApplicationPrograms")
    @field:XmlElement(name="ApplicationProgram")
    val applicationPrograms:MutableList<ApplicationProgram>? = LinkedList()
//    val languages: List<MyLanguage>
)


data class ApplicationProgram(
    @XmlAttribute(name="Id")
    val id: String?=null,
    @field:XmlElement(name="Static")
    val static: Static?=null
//    val dynamic: Dynamic?=null
)

data class Static(
    @field:XmlElement(name="Code")
    val code:Code?=null
//    @JacksonXmlElementWrapper
//    val parameterTypes:List<ParameterType>,
//    @JacksonXmlProperty(localName = "Parameters")
//    val parametersAndUnions:ParametersAndUnions,
//    @JacksonXmlElementWrapper
//    val parameterRefs: List<ParameterRef>,
//
//    val comObjectTable:ComObjectTable,
//    @JacksonXmlElementWrapper
//    val comObjectRefs: List<ComObjectRef>,
//    val addressTable: AddressTable

)

data class Code(
    @field:XmlElement(name = "AbsoluteSegment")
    val absoluteSegments: MutableList<AbsoluteSegment>?=LinkedList()
)

class AbsoluteSegment(
    @XmlAttribute(name="Id")
    val id: String?=null,
    @XmlAttribute(name="Address")
    val address:Int?=null,
    @XmlAttribute(name="Size")
    val size:Int?=null,
    @field:XmlElement(name="Data")
    @field:XmlSchemaType(name = "base64Binary")
    val data:ByteArray?=null
)

//
//data class MyLanguage(
//    val identifier: String,
//    val translationUnit: TranslationUnit
//){
//
//}
//
//data class TranslationUnit(
//    val refId: String,
//    @JsonProperty("TranslationElement")
//    @JacksonXmlElementWrapper(useWrapping = false)
//    val translationElements: List<TranslationElement>
//)
//
//data class TranslationElement(
//    val refId: String,
//    @JsonProperty("Translation")
//    @JacksonXmlElementWrapper(useWrapping = false)
//    val translations: List<Translation>
//)
//
//data class Translation(
//    val attributeName: String,
//    val text: String
//)
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//data class ApplicationProgram(val id: String, val static: Static, val dynamic: Dynamic)
//

//
//@JsonIgnoreProperties(ignoreUnknown = true)
//data class Dynamic(
//    @XmlElement
//    val channel: JAXBElement<*>
//)
//
//
//
//
//
//data class AddressTable(
//    val codeSegment: String,
//    val offset: Int,
//    val maxEntries: Int
//) {
//
//}
//
//data class AssociationTable(
//    val codeSegment: String,
//    val offset: Int,
//    val maxEntries: Int
//) {
//
//}
//
//data class ComObjectTable(
//    val codeSegment: String,
//    val offset: Int,
//    @JacksonXmlProperty(localName = "ComObject")
//    @JacksonXmlElementWrapper(useWrapping = false)
//    val comObjects: List<ComObject>
//) {
//
//}
//
//data class ComObjectRef(
//    val id: String,
//    val refId: String,
//    val tag: Int,
//    val text: String?,
//    val functionText: String?,
//    val readFlag: EnabledDisabled?,
////    val writeFlag: EnabledDisabled,
////    val communicationFlag: EnabledDisabled,
//    val transmitFlag:EnabledDisabled?
////    val updateFlag: EnabledDisabled,
////    val readOnInitFlag: EnabledDisabled
//)
//
//data class ComObject(
//    val id: String,
//    val name: String,
//    val text: String,
//    val number: Int,
//    val functionText: String,
//    val objectSize: String,
//    val readFlag: EnabledDisabled,
//    val writeFlag: EnabledDisabled,
//    val communicationFlag: EnabledDisabled,
//    val transmitFlag:EnabledDisabled,
//    val updateFlag: EnabledDisabled,
//    val readOnInitFlag: EnabledDisabled
//)
//
//enum class EnabledDisabled{
//    Enabled, Disabled
//}
//
//data class ParameterRef(
//    val id: String,
//    val refId: String,
//    val tag: Int,
//    val displayOrder: Int,
//    val value: Int?,
//    val text: String?,
//    val access: Access?,
//    val name: String?
//)
//

//

//
//@JsonIgnoreProperties(ignoreUnknown = true)
//data class ParameterType(val id: String, val name: String, val typeNumber:TypeNumber?, val typeRestriction: TypeRestriction?)
//
////@JsonIgnoreProperties(ignoreUnknown = true)
//data class TypeRestriction(
//    val base:String,
//    val sizeInBit: Int,
//    @JacksonXmlProperty(localName = "Enumeration")
//    @JacksonXmlElementWrapper(useWrapping = false)
//    val enumerations:List<Enumeration>
//) {
//
//}
//
//data class Enumeration(val text:String, val value:Int, val id: String, val displayOrder: Int, val binaryValue: String?)
//
//data class TypeNumber (val sizeInBit:Int, val type: ParamValueType, val minInclusive: Long, val maxInclusive: Long ) {
//
//}
//
////@JsonIgnoreProperties(ignoreUnknown = true)
////@JsonDeserialize(using=ParametersAndUnionsDeserializer::class)
// class ParametersAndUnions() {
//    //    @JacksonXmlProperty()
//    @JacksonXmlProperty(localName = "Parameter")
//    @JacksonXmlElementWrapper(useWrapping = false)
//    var parameters: List<Parameter> =listOf()
//        get() = field
//        set(value){
//            field=field+value
//        }
//
//    @JacksonXmlProperty(localName = "Union")
//    @JacksonXmlElementWrapper(useWrapping = false)
//    var unions: List<Union> = listOf()
//        get() = field
//        set(value){
//            field=field+value
//        }
//}
//@JsonIgnoreProperties(ignoreUnknown = true)
//data class Parameter (
//    val id:String,
//    val name: String,
//    val parameterType: String,
//    val text: String,
//    val access: Access?,
//    val value: Int?,
//    val memory:Memory?,
//    val englishText: String?=null
//)
//
//data class Union(
//    val sizeInBit: Int,
//    val memory: Memory,
//    @JacksonXmlProperty(localName = "Parameter")
//    @JacksonXmlElementWrapper(useWrapping = false)
//    val parameters: List<ExtendedParameter>
//)
//
//data class ExtendedParameter (
//    val id:String,
//    val name: String,
//    val parameterType: String,
//    val text: String,
//    val access: Access?,
//    val value: Int?,
//    val memory:Memory?,
//    val offset: Int,
//    val bitOffset: Int,
//    val englishText: String?,
//    val defaultUnionParameter: Boolean
//)
//
//data class Memory (val codeSegment: String, val offset:Int, val bitOffset:Int)
//
//enum class ParamValueType {
//    unsignedInt
//}
//
//enum class Access{
//    None, Read, ReadWrite
//}
