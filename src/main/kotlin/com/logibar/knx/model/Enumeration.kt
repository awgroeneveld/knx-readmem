package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlSchemaType

class Enumeration(
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlAttribute(name = "Value")
    val value: Int? = null,
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlAttribute(name = "DisplayOder")
    val displayOrder: Int? = null,
    @XmlAttribute(name = "BinaryValue")
    @field:XmlSchemaType(name = "base64Binary")
    val binaryValue: ByteArray? = null
) {
    var translation: String? = null
}

interface VisitableStaticElement {
    fun accept(visitor: StaticElementVisitor)
}


object TranslateExtensions {

    fun Static.translate(translationSet: TranslationSet) {
        parameterTypes?.forEach { it.translate(translationSet) }
        parametersAndUnions?.parameterOrUnions?.forEach { it.translate(translationSet) }
        parameterRefs?.forEach { it.translate(translationSet) }
        comObjectTable?.comObjects?.forEach { it.translate(translationSet) }
        comObjectRefs?.forEach { it.translate(translationSet) }
    }


    fun ParameterOrUnion.translate(translationSet: TranslationSet) {
        if (this is Parameter)
            this.translate(translationSet)
    }

    fun Enumeration.translate(translationSet: TranslationSet) {
        this.translation = translationSet.getText(id!!)?:text
    }

    fun ParameterType.translate(translationSet: TranslationSet) {
        this.typeRestriction?.enumerations?.forEach { it.translate(translationSet) }
    }

    fun Parameter.translate(translationSet: TranslationSet) {
        this.translation = translationSet.getText(id!!) ?: text
    }

    fun ParameterRef.translate(translationSet: TranslationSet) {
        this.translation = translationSet.getText(id!!) ?: parameter!!.translation
    }

    fun ComObject.translate(translationSet: TranslationSet) {
        this.translation = translationSet.getText(id!!) ?: this.text
        this.functionTranslation = translationSet.getFunctionText(id) ?:this.functionText
    }

    fun ComObjectRef.translate(translationSet: TranslationSet){
        this.translation=translationSet.getText(id!!)?:comObject!!.translation
        this.functionTranslation=translationSet.getFunctionText(id)?:comObject!!.functionTranslation
    }




}
