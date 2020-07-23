package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute

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
