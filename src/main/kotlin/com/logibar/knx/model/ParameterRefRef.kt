package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlIDREF

data class ParameterRefRef(
    @XmlIDREF
    @XmlAttribute(name = "RefId")
    val parameterReference: ParameterRef? = null
) : UiElement {
    override fun toLogString(
        indent: Int,
        translationSet: TranslationSet,
        deviceChanges: Map<String, ParameterMemory>
    ):String {
        val parameter=parameterReference!!.parameter!!
        val restriction=parameter.parameterType!!.typeRestriction
        val deviceDifferentValue=deviceChanges[parameter.id]?.value
        val defaultValue= getDefaultValue()
        val postfix=if (deviceDifferentValue==null || defaultValue==deviceDifferentValue) "" else "\t<CHANGED>${deviceDifferentValue}<CHANGED>"
        var parameterText="Value: ${defaultValue}, underlying parameter with id: ${parameter.id} $postfix"
        if (restriction!=null) {
            val currentEnum=restriction.enumerations!!.first { it.value==parameter.value }

//            val addOn="(=${translationSet.getText(currentEnum.id!!,indentString(indent))?:currentEnum.text})\n${indentString(indent+1)}Options:\n"
            val addOn="\n${indentString(indent+1)}Options:\n"
            val addOn2=restriction.enumerations!!
                .map { "${indentString(indent+2)}- ${it.value} (${translationSet.getText(it.id!!,indentString(indent))?:it.text})" }
                .joinToString("\n")
            parameterText+=addOn
            parameterText+=addOn2
        }
        return "${indentString(indent)}Parameter ${translationSet.getText(parameterReference,indentString(indent))} $parameterText"
    }

    private fun getDefaultValue() =
        parameterReference!!.value ?: parameterReference!!.parameter!!.value

    override fun accept(visitor: UiElementVisitor) {
        visitor.visit(this)
    }

    override fun hasChanges(deviceChanges: Map<String, ParameterMemory>): Boolean {
        val deviceValue=deviceChanges[parameterReference!!.parameter!!.id]
        return deviceValue!=null && deviceValue.value!=getDefaultValue()
    }
}
