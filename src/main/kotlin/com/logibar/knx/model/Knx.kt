package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlElements
import javax.xml.bind.annotation.XmlID
import javax.xml.bind.annotation.XmlIDREF
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlSchemaType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "KNX")
data class Knx(
    @field:XmlElement(name = "ManufacturerData")
    val manufacturerData: ManufacturerData? = null,
    @XmlAttribute(name = "CreatedBy")
    val createdBy: String? = null
) {

}

@XmlAccessorType(XmlAccessType.FIELD)
data class ManufacturerData(
    @field:XmlElement(name = "Manufacturer")
    val manufacturer: Manufacturer? = null
)

@XmlAccessorType(XmlAccessType.FIELD)
data class Manufacturer(
    @XmlAttribute(name = "RefId")
    val refId: String? = null,
    @field:XmlElementWrapper(name = "ApplicationPrograms")
    @field:XmlElement(name = "ApplicationProgram")
    val applicationPrograms: MutableList<ApplicationProgram>? = LinkedList()
//    val languages: List<MyLanguage>
)


data class ApplicationProgram(
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @field:XmlElement(name = "Static")
    val static: Static? = null
//    val dynamic: Dynamic?=null
)

data class Static(
    @field:XmlElement(name = "Code")
    val code: Code? = null,
    @field:XmlElementWrapper(name = "ParameterTypes")
    @field:XmlElement(name = "ParameterType")
    val parameterTypes: MutableList<ParameterType>? = LinkedList(),
    @field:XmlElement(name = "Parameters")
    val parametersAndUnions:ParametersAndUnions?=null
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
    val absoluteSegments: MutableList<AbsoluteSegment>? = LinkedList()
)

class AbsoluteSegment(
    @XmlID
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlAttribute(name = "Address")
    val address: Int? = null,
    @XmlAttribute(name = "Size")
    val size: Int? = null,
    @field:XmlElement(name = "Data")
    @field:XmlSchemaType(name = "base64Binary")
    val data: ByteArray? = null
)


data class ParameterType(
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlAttribute(name = "Name")
    val name: String? = null,
    @field:XmlElement(name = "TypeNumber")
    val typeNumber: TypeNumber? = null,
    @field:XmlElement(name = "TypeRestriction")
    val typeRestriction: TypeRestriction? = null
)

data class TypeRestriction(
    @XmlAttribute(name = "Base")
    val base: String? = null,
    @XmlAttribute(name = "SizeInBit")
    val sizeInBit: Int? = null,
    @field:XmlElement(name = "Enumeration")
    val enumerations: MutableList<Enumeration>?=LinkedList()
)

class Enumeration(
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlAttribute(name = "Value")
    val value: Int? = null,
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlAttribute(name = "DisplayOder")
    val displayOrder: Int? = null,
    @XmlAttribute(name = "BinaryValue")
    @field:XmlSchemaType(name = "base64Binary")
    val binaryValue: ByteArray? = null
)

data class TypeNumber(
    @XmlAttribute(name="SizeInBit")
    val sizeInBit: Int?=null,
    @XmlAttribute(name="Type")
    val type: ParamValueType?=null,
    @XmlAttribute(name="MinInclusive")
    val minInclusive: Long?=null,
    @XmlAttribute(name="MaxInclusive")
    val maxInclusive: Long?=null
)

enum class ParamValueType {
    unsignedInt
}


class ParametersAndUnions() {
    @field:XmlElements(
        XmlElement(name="Parameter", type=Parameter::class),
        XmlElement(name="Union", type=Union::class)
    )
    val parameterOrUnions: MutableList<Any>?=LinkedList()

}

data class Parameter (
    @XmlAttribute(name="Id")
    val id:String?=null,
    @XmlAttribute(name="Name")
    val name: String?=null,
    @XmlAttribute(name="ParameterType")
    val parameterType: String?=null,
    @XmlAttribute(name="Text")
    val text: String?=null,
    @XmlAttribute(name="Access")
    val access: Access?=null,
    @XmlAttribute(name="Value")
    val value: Int?=null,
    @field:XmlElement(name="Memory")
    val memory: Memory?=null
)

data class Union(
    @XmlAttribute(name="SizeInBit")
    val sizeInBit: Int?=null,
    @field:XmlElement(name="Memory")
    val memory: Memory?=null,
    @field:XmlElement(name="Parameter")
    val parameters: MutableList<ExtendedParameter>?=LinkedList()
)

data class ExtendedParameter (
    @XmlAttribute(name="Id")
    val id:String?=null,
    @XmlAttribute(name="Name")
    val name: String?=null,
    @XmlAttribute(name="ParameterType")
    val parameterType: String?=null,
    @XmlAttribute(name="Text")
    val text: String?=null,
    @XmlAttribute(name="Access")
    val access: Access?=null,
    @XmlAttribute(name="Value")
    val value: Int?=null,
    @field:XmlElement(name="Memory")
    val memory: Memory?=null,
    @XmlAttribute(name="Offset")
    val offset: Int?=null,
    @XmlAttribute(name="BitOffset")
    val bitOffset: Int?=null,
    @XmlAttribute(name="DefaultUnionParameter")
    val defaultUnionParameter: Boolean?=null
)

data class Memory (
    @XmlIDREF
    @XmlAttribute(name="CodeSegment")
    val codeSegment: AbsoluteSegment?=null,
    @XmlAttribute(name="Offset")
    val offset:Int?=null,
    @XmlAttribute(name="BitOffset")
    val bitOffset:Int?=null
)


enum class Access{
    None, Read, ReadWrite
}

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
//
//
//
//
//
//
//}
//

//@JsonIgnoreProperties(ignoreUnknown = true)
//
//

//
//
//

//

