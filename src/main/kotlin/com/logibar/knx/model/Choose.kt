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
    override fun toLogString(
        indent: Int,
        translationSet: TranslationSet,
        deviceChanges: Map<String, ParameterMemory>
    ):String {
        val itemsText= whenToActivate!!.mapNotNull {
            val deviceValue = deviceChanges[parameterRef!!.parameter!!.id]
            val defaultValue = parameterRef!!.value ?: parameterRef.parameter!!.value
            val activeDefault = it.isActivated(defaultValue)
            val activeDevice = if (deviceValue != null) it.isActivated(deviceValue.value) else activeDefault
            val prefix = (if (activeDefault) "A" else "-") + (if (activeDevice) "A" else "-")
            if (activeDevice || activeDefault)
                prefix + it.toLogString(indent + 1, translationSet, deviceChanges)
            else
                null
        }
            .joinToString("\n")
        return "${indentString(indent)}Choose parameter ${translationSet.getText(parameterRef, indentString(indent))}\n" +
                "$itemsText"
    }

    override fun accept(visitor: UiElementVisitor) {
        visitor.visit(this)
        whenToActivate!!.forEach { it.accept(visitor) }
    }

    fun getActiveWhens(value: Int?)=
        this.whenToActivate?.filter { it.isActivated(value) }
}
