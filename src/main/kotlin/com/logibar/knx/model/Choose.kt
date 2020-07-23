package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlIDREF

data class Choose(
    @XmlIDREF
    @XmlAttribute(name = "ParamRefId")
    val parameterRef: ParameterRef? = null,
    @field:XmlElement(name = "when")
    val whenToActivate: MutableList<WhenToActivate>? = LinkedList()
) : UiElement {
    override fun toLogString(indent: Int, translationSet: TranslationSet):String {
        val itemsText= whenToActivate?.joinToString("\n") { it.toLogString(indent + 1, translationSet) }
        return "${indentString(indent)}Choose parameter ${translationSet.getText(parameterRef, indentString(indent))}\n" +
                "$itemsText"
    }
}
