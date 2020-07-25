package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlIDREF

data class ComObjectRefRef(
    @XmlIDREF
    @XmlAttribute(name = "RefId")
    val comObjectReference: ComObjectRef? = null
) : UiElement {
    override fun toLogString(
        indent: Int,
        translationSet: TranslationSet,
        deviceChanges: Map<String, ParameterMemory>
    ):String {
        val refTrans=translationSet.translationsById[comObjectReference!!.id]
        val comTrans=translationSet.translationsById[comObjectReference!!.comObject!!.id]
        val text=refTrans?.getText()?:comTrans?.getText()
        val functionText=refTrans?.getFunctionText()?:comTrans?.getFunctionText()
        return "${indentString(indent)} Comobject: $text:$functionText, object number: ${comObjectReference.comObject!!.number}"
    }

    override fun accept(visitor: UiElementVisitor) {
        visitor.visit(this)
    }

    override fun hasChanges(deviceChanges: Map<String, ParameterMemory>): Boolean {
        return false
    }
}
