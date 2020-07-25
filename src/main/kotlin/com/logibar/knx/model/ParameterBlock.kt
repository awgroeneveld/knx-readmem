package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElements
import javax.xml.bind.annotation.XmlIDREF

data class ParameterBlock(
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlAttribute(name = "Name")
    val name: String? = null,
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlIDREF
    @XmlAttribute(name = "ParamRefId")
    val parameterRef: ParameterRef? = null,
    @field: XmlElements(
        XmlElement(
            name = "ParameterRefRef",
            type = ParameterRefRef::class
        ),
        XmlElement(
            name = "choose",
            type = Choose::class
        )
    )
    val items: MutableList<UiElement>? = LinkedList()
) : UiElement {
    var translatedText: String?=null

    override fun toLogString(indent: Int, translationSet: TranslationSet): String {
        val itemText=items?.map { it.toLogString(indent+1, translationSet) }?.joinToString(separator = "\n")
        return "${indentString(indent)}ParameterBlock ${translationSet.getText(parameterRef,indentString(indent))?:text}\n$itemText"
    }

    override fun accept(visitor: UiElementVisitor) {
        visitor.visit(this)
        items!!.forEach { it.accept(visitor) }
    }
}
