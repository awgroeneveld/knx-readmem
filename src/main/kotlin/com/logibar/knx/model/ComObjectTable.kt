package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlIDREF

data class ComObjectTable(
    @XmlIDREF
    @XmlAttribute
    val codeSegment: AbsoluteSegment? = null,
    @XmlAttribute
    val offset: Int? = null,
    @field:XmlElement(name = "ComObject")
    val comObjects: MutableList<ComObject>? = LinkedList()
)
