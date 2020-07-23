package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

data class MyLanguage(
    @XmlAttribute(name = "Identifier")
    val identifier: String? = null,
    @field:XmlElement(name = "TranslationUnit")
    val translationUnit: TranslationUnit? = null
)
