package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

data class TranslationUnit(
    @XmlAttribute(name = "RefId")
    val refId: String? = null,
    @field:XmlElement(name = "TranslationElement")
    val translationElements: MutableList<TranslationElement>? = LinkedList()
)
