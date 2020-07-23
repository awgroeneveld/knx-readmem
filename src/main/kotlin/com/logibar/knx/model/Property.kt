package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute

data class Property(
    @XmlAttribute(name = "ObjectIndex")
    val objectIndex: Int? = null,
    @XmlAttribute(name = "PropertyId")
    val propertyId: Int? = null,
    @XmlAttribute(name = "Offset")
    val offset: Int? = null,
    @XmlAttribute(name = "BitOffset")
    val bitOffset: Int? = null
)
