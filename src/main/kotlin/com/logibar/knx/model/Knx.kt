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
    val applicationPrograms: MutableList<ApplicationProgram>? = LinkedList(),
    @field:XmlElementWrapper(name = "Languages")
    @field:XmlElement(name = "Language")
    val languages: MutableList<MyLanguage>? = LinkedList()
)


data class ApplicationProgram(
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @field:XmlElement(name = "Static")
    val static: Static? = null,
    @field:XmlElement(name = "Dynamic")
    val dynamic: Dynamic? = null
)

data class Static(
    @field:XmlElement(name = "Code")
    val code: Code? = null,
    @field:XmlElementWrapper(name = "ParameterTypes")
    @field:XmlElement(name = "ParameterType")
    val parameterTypes: MutableList<ParameterType>? = LinkedList(),
    @field:XmlElement(name = "Parameters")
    val parametersAndUnions: ParametersAndUnions? = null,
    @field:XmlElementWrapper(name = "ParameterRefs")
    @field:XmlElement(name = "ParameterRef")
    val parameterRefs: MutableList<ParameterRef>? = LinkedList(),
    @field:XmlElement(name = "ComObjectTable")
    val comObjectTable: ComObjectTable? = null,
    @field:XmlElementWrapper(name = "ComObjectRefs")
    @field:XmlElement(name = "ComObjectRef")
    val comObjectRefs: MutableList<ComObjectRef>? = LinkedList(),
    @field:XmlElement(name = "AddressTable")
    val addressTable: AddressOrAssociationTable? = null,
    @field:XmlElement(name = "AssociationTable")
    val associationTable: AddressOrAssociationTable? = null
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
    val data: ByteArray? = null,
    @field:XmlElement(name = "Mask")
    @field:XmlSchemaType(name = "base64Binary")
    val mask: ByteArray? = null
)


data class ParameterType(
    @XmlID
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlAttribute(name = "Name")
    val name: String? = null,
    @field:XmlElement(name = "TypeNumber")
    val typeNumber: TypeNumber? = null,
    @field:XmlElement(name = "TypeRestriction")
    val typeRestriction: TypeRestriction? = null,
    @field:XmlElement(name = "TypeNone")
    val typeNone: TypeNone? = null
)

class TypeNone()

data class TypeRestriction(
    @XmlAttribute(name = "Base")
    val base: String? = null,
    @XmlAttribute(name = "SizeInBit")
    val sizeInBit: Int? = null,
    @field:XmlElement(name = "Enumeration")
    val enumerations: MutableList<Enumeration>? = LinkedList()
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
    @XmlAttribute(name = "SizeInBit")
    val sizeInBit: Int? = null,
    @XmlAttribute(name = "Type")
    val type: ParamValueType? = null,
    @XmlAttribute(name = "MinInclusive")
    val minInclusive: Long? = null,
    @XmlAttribute(name = "MaxInclusive")
    val maxInclusive: Long? = null
)

enum class ParamValueType {
    unsignedInt
}


class ParametersAndUnions() {
    @field:XmlElements(
        XmlElement(name = "Parameter", type = Parameter::class),
        XmlElement(name = "Union", type = Union::class)
    )
    val parameterOrUnions: MutableList<ParameterOrUnion>? = LinkedList()

}

interface ParameterOrUnion {
    val memory: Memory?
}

class Parameter(
    @XmlID
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlAttribute(name = "Name")
    val name: String? = null,
    @XmlIDREF
    @XmlAttribute(name = "ParameterType")
    val parameterType: ParameterType? = null,
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlAttribute(name = "Access")
    val access: Access? = null,
    @XmlAttribute(name = "Value")
    val value: Int? = null,
    @field:XmlElement(name = "Memory")
    override val memory: Memory? = null,
    @field: XmlElement(name = "Property")
    val property: Property? = null,
    @XmlAttribute(name = "Offset")
    val offset: Int? = null,
    @XmlAttribute(name = "BitOffset")
    val bitOffset: Int? = null,
    @XmlAttribute(name = "DefaultUnionParameter")
    val defaultUnionParameter: Boolean? = null
) : ParameterOrUnion


data class Property(
    @XmlAttribute(name = "ObjectIndex")
    val objectIndex: Int? = null,
    @XmlAttribute(name = "PropertyId")
    val propertyId: Int? = null,
    @XmlAttribute(name = "Offset")
    val offset: Int? = null,
    @XmlAttribute(name = "BitOffset")
    val bitOffset: Int? = null
)

data class Union(
    @XmlAttribute(name = "SizeInBit")
    val sizeInBit: Int? = null,
    @field:XmlElement(name = "Memory")
    override val memory: Memory? = null,
    @field:XmlElement(name = "Parameter")
    val parameters: MutableList<Parameter>? = LinkedList()
) : ParameterOrUnion


data class Memory(
    @XmlIDREF
    @XmlAttribute(name = "CodeSegment")
    val codeSegment: AbsoluteSegment? = null,
    @XmlAttribute(name = "Offset")
    val offset: Int? = null,
    @XmlAttribute(name = "BitOffset")
    val bitOffset: Int? = null
)


enum class Access {
    None, Read, ReadWrite
}


data class ParameterRef(
    @XmlID
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlIDREF
    @XmlAttribute(name = "RefId")
    val parameter: Parameter? = null,
    @XmlAttribute(name = "Tag")
    val tag: Int? = null,
    @XmlAttribute(name = "DisplayOrder")
    val displayOrder: Int? = null,
    @XmlAttribute(name = "Value")
    val value: Int? = null,
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlAttribute(name = "Access")
    val access: Access? = null,
    @XmlAttribute(name = "Name")
    val name: String? = null
)


data class ComObjectTable(
    @XmlIDREF
    @XmlAttribute
    val codeSegment: AbsoluteSegment? = null,
    @XmlAttribute
    val offset: Int? = null,
    @field:XmlElement(name = "ComObject")
    val comObjects: MutableList<ComObject>? = LinkedList()
)

data class ComObject(
    @XmlID
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlAttribute(name = "Name")
    val name: String? = null,
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlAttribute(name = "Number")
    val number: Int? = null,
    @XmlAttribute(name = "FunctionText")
    val functionText: String? = null,
    @XmlAttribute(name = "ObjectSize")
    val objectSize: String? = null,
    @XmlAttribute(name = "ReadFlag")
    val readFlag: EnabledDisabled? = null,
    @XmlAttribute(name = "WriteFlag")
    val writeFlag: EnabledDisabled? = null,
    @XmlAttribute(name = "CommunicationFlag")
    val communicationFlag: EnabledDisabled? = null,
    @XmlAttribute(name = "TransmitFlag")
    val transmitFlag: EnabledDisabled? = null,
    @XmlAttribute(name = "UpdateFlag")
    val updateFlag: EnabledDisabled? = null,
    @XmlAttribute(name = "ReadOnInitFlag")
    val readOnInitFlag: EnabledDisabled? = null
)

enum class EnabledDisabled {
    Enabled, Disabled
}


data class ComObjectRef(
    @XmlID
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlIDREF
    @XmlAttribute(name = "RefId")
    val comObject: ComObject? = null,
    @XmlAttribute(name = "Tag")
    val tag: Int? = null,
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlAttribute(name = "FunctionText")
    val functionText: String? = null,
    @XmlAttribute(name = "ReadFlag")
    val readFlag: EnabledDisabled? = null,
//    val writeFlag: EnabledDisabled,
//    val communicationFlag: EnabledDisabled,
    @XmlAttribute(name = "TransmitFlag")
    val transmitFlag: EnabledDisabled? = null
//    val updateFlag: EnabledDisabled,
//    val readOnInitFlag: EnabledDisabled
)

data class AddressOrAssociationTable(
    @XmlIDREF
    @XmlAttribute(name = "CodeSegment")
    val codeSegment: AbsoluteSegment? = null,
    @XmlAttribute(name = "Offset")
    val offset: Int? = null,
    @XmlAttribute(name = "MaxEntries")
    val maxEntries: Int? = null
)

data class Dynamic(
    @field:XmlElement(name = "Channel")
    val channel: Channel? = null
)

data class Channel(
    @XmlAttribute(name = "Name")
    val name: String? = null,
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlAttribute(name = "Number")
    val number: Int? = null,
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @field:XmlElements(
        XmlElement(name = "ParameterBlock", type = ParameterBlock::class),
        XmlElement(name = "choose", type = Choose::class)
    )
    val items: MutableList<UiElement>? = LinkedList()
)

interface UiElement {
    fun indentString(indent: Int) = "  ".repeat(indent)
    fun toLogString(indent: Int, translationSet: TranslationSet): String
}

class TranslationSet(val translationsById: Map<String, TranslationElement>) {
    fun String.replaceCrLf():String{
        return this.replace("\r"," ").replace("\n", "")
    }
    fun getText(parameterRefRef: ParameterRefRef?): String? {
        return if (parameterRefRef == null) null
        else (getText(parameterRefRef.parameterReference))?.replaceCrLf()
    }

    fun getText(parameterRef: ParameterRef?): String? {
        return if (parameterRef == null) null
        else (translationsById[parameterRef.id]?.getText()?:getText(parameterRef.parameter))?.replaceCrLf()
    }

    fun getText(parameter: Parameter?): String?{
        return if (parameter==null) null
        else ((translationsById[parameter.id]?.getText()?:parameter.text)+ " (value: ${parameter.value})")?.replaceCrLf()
    }
}

data class ParameterBlock(
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlAttribute(name = "Name")
    val name: String? = null,
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlIDREF
    @XmlAttribute(name = "ParamRefId")
    val parameterRef: ParameterRef? = null,
    @field: XmlElements(
        XmlElement(name = "ParameterRefRef", type = ParameterRefRef::class),
        XmlElement(name = "choose", type = Choose::class)
    )
    val items: MutableList<UiElement>? = LinkedList()
) : UiElement {
    override fun toLogString(indent: Int, translationSet: TranslationSet): String {
        val itemText=items?.map { it.toLogString(indent+1, translationSet) }?.joinToString(separator = "\n")
        return "${indentString(indent)}ParameterBlock ${translationSet.getText(parameterRef)?:text}\n$itemText"
    }
}

data class Choose(
    @XmlIDREF
    @XmlAttribute(name = "ParamRefId")
    val parameterRef: ParameterRef? = null,
    @field:XmlElement(name = "when")
    val whenToActivate: MutableList<WhenToActivate>? = LinkedList()
) : UiElement {
    override fun toLogString(indent: Int, translationSet: TranslationSet):String {
        val itemsText= whenToActivate?.joinToString("\n") { it.toLogString(indent + 1, translationSet) }
        return "${indentString(indent)}Choose parameter ${translationSet.getText(parameterRef)}\n" +
                "$itemsText"
    }
}

data class ParameterRefRef(
    @XmlIDREF
    @XmlAttribute(name = "RefId")
    val parameterReference: ParameterRef? = null
) : UiElement {
    override fun toLogString(indent: Int, translationSet: TranslationSet):String {
        return "${indentString(indent)}Parameter ${translationSet.getText(parameterReference)}"
    }
}

data class ComObjectRefRef(
    @XmlIDREF
    @XmlAttribute(name = "RefId")
    val comObjectReference: ComObjectRef? = null
) : UiElement {
    override fun toLogString(indent: Int, translationSet: TranslationSet):String {
        val refTrans=translationSet.translationsById[comObjectReference!!.id]
        val comTrans=translationSet.translationsById[comObjectReference!!.comObject!!.id]
        val text=refTrans?.getText()?:comTrans?.getText()
        val functionText=refTrans?.getFunctionText()?:comTrans?.getFunctionText()
        return "${indentString(indent)} Comobject: $text:$functionText, object number: ${comObjectReference.comObject!!.number}"
    }
}


data class WhenToActivate(
    @XmlAttribute
    val test: Int? = null,
    @XmlAttribute
    val default: Boolean? = null,
    @field:XmlElements(
        XmlElement(name = "ParameterRefRef", type = ParameterRefRef::class),
        XmlElement(name = "ComObjectRefRef", type = ComObjectRefRef::class),
        XmlElement(name = "choose", type = Choose::class),
        XmlElement(name = "ParameterBlock", type = ParameterBlock::class)
    )
    val thenItems: MutableList<UiElement>? = LinkedList()
) : UiElement {
    override fun toLogString(indent: Int, translationSet: TranslationSet):String {
        val itemText= thenItems?.joinToString(separator = "\n") { it.toLogString(indent + 1, translationSet) }
        return "${indentString(indent)}WhenToActivate, default: $default, test: $test\n$itemText"
    }
}

data class MyLanguage(
    @XmlAttribute(name = "Identifier")
    val identifier: String? = null,
    @field:XmlElement(name = "TranslationUnit")
    val translationUnit: TranslationUnit? = null
)

data class TranslationUnit(
    @XmlAttribute(name = "RefId")
    val refId: String? = null,
    @field:XmlElement(name = "TranslationElement")
    val translationElements: MutableList<TranslationElement>? = LinkedList()
)

data class TranslationElement(
    @XmlAttribute(name = "RefId")
    val refId: String? = null,
    @field:XmlElement(name = "Translation")
    val translations: MutableList<Translation>? = LinkedList()
) {
    fun getText() = translations?.firstOrNull { it.attributeName == "Text" }?.text
    fun getFunctionText() = translations?.firstOrNull { it.attributeName == "FunctionText" }?.text
}

data class Translation(
    @XmlAttribute(name = "AttributeName")
    val attributeName: String? = null,
    @XmlAttribute(name = "Text")
    val text: String? = null
)
