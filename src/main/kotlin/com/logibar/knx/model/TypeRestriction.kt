package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

data class TypeRestriction(
    @XmlAttribute(name = "Base")
    val base: String? = null,
    @XmlAttribute(name = "SizeInBit")
    val sizeInBit: Int? = null,
    @field:XmlElement(name = "Enumeration")
    val enumerations: MutableList<Enumeration>? = LinkedList()
)
