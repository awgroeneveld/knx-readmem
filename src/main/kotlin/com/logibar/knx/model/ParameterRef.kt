package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlID
import javax.xml.bind.annotation.XmlIDREF

data class ParameterRef(
    @XmlID
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlIDREF
    @XmlAttribute(name = "RefId")
    val parameter: Parameter? = null,
    @XmlAttribute(name = "Tag")
    val tag: Int? = null,
    @XmlAttribute(name = "DisplayOrder")
    val displayOrder: Int? = null,
    @XmlAttribute(name = "Value")
    val value: Int? = null,
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlAttribute(name = "Access")
    val access: Access? = null,
    @XmlAttribute(name = "Name")
    val name: String? = null
)
