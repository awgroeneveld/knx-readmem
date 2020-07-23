package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

data class Union(
    @XmlAttribute(name = "SizeInBit")
    val sizeInBit: Int? = null,
    @field:XmlElement(name = "Memory")
    override val memory: Memory? = null,
    @field:XmlElement(name = "Parameter")
    val parameters: MutableList<Parameter>? = LinkedList()
) : ParameterOrUnion
