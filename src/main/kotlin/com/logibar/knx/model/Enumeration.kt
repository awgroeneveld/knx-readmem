package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlSchemaType

class Enumeration(
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlAttribute(name = "Value")
    val value: Int? = null,
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlAttribute(name = "DisplayOder")
    val displayOrder: Int? = null,
    @XmlAttribute(name = "BinaryValue")
    @field:XmlSchemaType(name = "base64Binary")
    val binaryValue: ByteArray? = null
)
