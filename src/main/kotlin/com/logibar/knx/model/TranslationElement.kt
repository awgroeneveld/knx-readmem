package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

data class TranslationElement(
    @XmlAttribute(name = "RefId")
    val refId: String? = null,
    @field:XmlElement(name = "Translation")
    val translations: MutableList<Translation>? = LinkedList()
) {
    fun getText() = translations?.firstOrNull { it.attributeName == "Text" }?.text
    fun getFunctionText() = translations?.firstOrNull { it.attributeName == "FunctionText" }?.text
}
