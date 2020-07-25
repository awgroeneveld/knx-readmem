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


object TranslationExtensions {

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
        this.translation = translationSet.getText(id!!)
    }

    fun ParameterRef.translate(translationSet: TranslationSet) {
        val parameterTranslation=parameter!!.translation
        val refTranslation=translationSet.getText(id!!)
        if (refTranslation!=null) {
            this.translation = refTranslation
            if (parameterTranslation==null){
                parameter.translation=refTranslation
            }
        }
        else {
            this.translation = parameterTranslation
        }
    }

    fun ComObject.translate(translationSet: TranslationSet) {
        this.translation = translationSet.getText(id!!)
        this.functionTranslation = translationSet.getFunctionText(id)
    }

    fun ComObjectRef.translate(translationSet: TranslationSet){
        val objectTranslation = comObject!!.translation
        val refTranslation = translationSet.getText(id!!)
        val objectFunctionTranslation = comObject.functionTranslation
        val refFunctionTranslation = translationSet.getFunctionText(id)

        if (refTranslation!=null) {
            this.translation = refTranslation
            this.functionTranslation = refFunctionTranslation
            if(objectTranslation==null){
                comObject.translation=refTranslation
                comObject.functionTranslation=refFunctionTranslation
            }
        }
        else{
            this.translation = objectTranslation
            this.functionTranslation = objectFunctionTranslation
        }
    }




}
