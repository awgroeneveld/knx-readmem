package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlIDREF

data class Memory(
    @XmlIDREF
    @XmlAttribute(name = "CodeSegment")
    val codeSegment: AbsoluteSegment? = null,
    @XmlAttribute(name = "Offset")
    val offset: Int? = null,
    @XmlAttribute(name = "BitOffset")
    val bitOffset: Int? = null
)
