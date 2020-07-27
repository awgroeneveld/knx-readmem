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
    val typeNone: TypeNone? = null,
    @field:XmlElement(name = "TypeText")
    val typeText: TypeText? = null

) {
    fun getSizeInBits(): Int {
        if (typeNumber != null)
            return typeNumber.sizeInBit!!
        if (typeRestriction != null)
            return typeRestriction.sizeInBit!!
        return 0
    }

    fun getType():ParamType{
        if (typeNumber!=null)
            return ParamType.NUMBER
        if (typeRestriction!=null)
            return ParamType.RESTRICTION
        if (typeText!=null)
            return ParamType.TEXT
        return ParamType.NONE
    }
}

enum class ParamType(val intValue:Boolean){
    NUMBER(true), RESTRICTION(true), TEXT(false), NONE(false)
}
