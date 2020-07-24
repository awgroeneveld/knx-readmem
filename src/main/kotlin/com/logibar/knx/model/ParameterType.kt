package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlID

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
){
    fun getSizeInBits(): Int {
        if (typeNumber!=null)
            return typeNumber.sizeInBit!!
        if (typeRestriction!=null)
            return typeRestriction.sizeInBit!!
        return 0
    }
}
