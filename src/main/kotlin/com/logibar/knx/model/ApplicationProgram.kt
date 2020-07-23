package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

data class ApplicationProgram(
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @field:XmlElement(name = "Static")
    val static: Static? = null,
    @field:XmlElement(name = "Dynamic")
    val dynamic: Dynamic? = null
)
