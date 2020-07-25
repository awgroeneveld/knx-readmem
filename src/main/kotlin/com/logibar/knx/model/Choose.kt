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
        val itemsText= getActiveWhens(true)?.joinToString("\n") { it.toLogString(indent + 1, translationSet) }
        return "${indentString(indent)}Choose parameter ${translationSet.getText(parameterRef, indentString(indent))}\n" +
                "$itemsText"
    }

    override fun accept(visitor: UiElementVisitor) {
        visitor.visit(this)
        whenToActivate!!.forEach { it.accept(visitor) }
    }

    fun getActiveWhens(defaultValue: Boolean)=
        this.whenToActivate?.filter { it.isActivated(parameterRef?.value?:parameterRef?.parameter?.value) }
}
