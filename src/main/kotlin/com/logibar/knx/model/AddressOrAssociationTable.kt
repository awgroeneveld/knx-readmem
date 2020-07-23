package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlIDREF

data class AddressOrAssociationTable(
    @XmlIDREF
    @XmlAttribute(name = "CodeSegment")
    val codeSegment: AbsoluteSegment? = null,
    @XmlAttribute(name = "Offset")
    val offset: Int? = null,
    @XmlAttribute(name = "MaxEntries")
    val maxEntries: Int? = null
)
