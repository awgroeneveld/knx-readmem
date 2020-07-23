package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper

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
