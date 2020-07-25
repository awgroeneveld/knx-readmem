package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElements

data class WhenToActivate(
    @XmlAttribute
    val test: Int? = null,
    @XmlAttribute
    val default: Boolean? = null,
    @field:XmlElements(
        XmlElement(
            name = "ParameterRefRef",
            type = ParameterRefRef::class
        ),
        XmlElement(
            name = "ComObjectRefRef",
            type = ComObjectRefRef::class
        ),
        XmlElement(
            name = "choose",
            type = Choose::class
        ),
        XmlElement(
            name = "ParameterBlock",
            type = ParameterBlock::class
        )
    )
    val thenItems: MutableList<UiElement>? = LinkedList()
) : UiElement {
    override fun toLogString(indent: Int, translationSet: TranslationSet):String {
        val itemText= thenItems?.joinToString(separator = "\n") { it.toLogString(indent + 1, translationSet) }
        return "${indentString(indent)}WhenToActivate, default: $default, test: $test\n$itemText"
    }

    override fun accept(visitor: UiElementVisitor) {
        visitor.visit(this)
        thenItems!!.forEach { it.accept(visitor) }
    }

    fun isActivated(value: Int?)=
        (default!=null && default) || (test!=null && test==value)
}
