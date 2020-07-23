package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute

data class Translation(
    @XmlAttribute(name = "AttributeName")
    val attributeName: String? = null,
    @XmlAttribute(name = "Text")
    val text: String? = null
)
