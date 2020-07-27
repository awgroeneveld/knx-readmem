package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlID
import javax.xml.bind.annotation.XmlIDREF

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
    val value: String? = null,
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
) : ParameterOrUnion{
    var translation:String?=null

    fun intValue(): Int =
        if (parameterType!!.getType().intValue)
            value!!.toInt()
        else -1
}
