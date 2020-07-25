package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlIDREF

data class ParameterRefRef(
    @XmlIDREF
    @XmlAttribute(name = "RefId")
    val parameterReference: ParameterRef? = null
) : UiElement {
    override fun toLogString(indent: Int, translationSet: TranslationSet):String {
        val parameter=parameterReference!!.parameter!!
        val restriction=parameter.parameterType!!.typeRestriction
        var parameterText="Value: ${parameter.value}"
        if (restriction!=null) {
            val currentEnum=restriction.enumerations!!.first { it.value==parameter.value }

            val addOn="(=${translationSet.getText(currentEnum.id!!,indentString(indent))?:currentEnum.text})\n${indentString(indent+1)}Options:\n"
            val addOn2=restriction.enumerations!!
                .map { "${indentString(indent+2)}- ${it.value} (${translationSet.getText(it.id!!,indentString(indent))?:it.text})" }
                .joinToString("\n")
            parameterText+=addOn
            parameterText+=addOn2
        }
        return "${indentString(indent)}Parameter ${translationSet.getText(parameterReference,indentString(indent))} $parameterText"
    }

    override fun accept(visitor: UiElementVisitor) {
        visitor.visit(this)
    }
}
