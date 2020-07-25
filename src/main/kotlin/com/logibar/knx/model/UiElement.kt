package com.logibar.knx.model


interface UiElement {
    fun indentString(indent: Int) = "  ".repeat(indent)
    fun toLogString(indent: Int, translationSet: TranslationSet): String
    fun accept(visitor: UiElementVisitor)
}

interface UiElementVisitor {
    fun visit(parameterBlock: ParameterBlock):Boolean
    fun visit(choose: Choose):Boolean
    fun visit(comObjectRefRef: ComObjectRefRef):Boolean
    fun visit(parameterRefRef: ParameterRefRef):Boolean
    fun visit(whenToActivate: WhenToActivate):Boolean
}

class UIElementTranslator(private val translationSet: TranslationSet) : UiElementVisitor {

    override fun visit(parameterBlock: ParameterBlock):Boolean {
        parameterBlock.translatedText = translationSet.getText(parameterBlock.id!!)
            ?: parameterBlock.parameterRef?.translation
        return true
    }

    override fun visit(choose: Choose) = true
    override fun visit(comObjectRefRef: ComObjectRefRef) = true
    override fun visit(parameterRefRef: ParameterRefRef) = true
    override fun visit(whenToActivate: WhenToActivate) = true
}

class UiElementDefaultValuesProvider() : UiElementVisitor {
    private var parameterDefaultValuesById = HashMap<String, Int>()
    private var lastChoose:Choose?=null

    fun getDefaultValuesById()=parameterDefaultValuesById.toMap()

    private fun addIfNotNull(parameterReference: ParameterRef?) {
        if (parameterReference?.value != null)
            parameterDefaultValuesById[parameterReference.id!!] = parameterReference.value!!
    }


    override fun visit(parameterBlock: ParameterBlock):Boolean {
        addIfNotNull(parameterBlock.parameterRef)
        return true
    }

    override fun visit(choose: Choose):Boolean {
        this.lastChoose=choose
        addIfNotNull(choose.parameterRef)
        return true
    }

    override fun visit(comObjectRefRef: ComObjectRefRef):Boolean {
        return true
    }

    override fun visit(parameterRefRef: ParameterRefRef):Boolean {
        addIfNotNull(parameterRefRef.parameterReference)
        return true
    }

    override fun visit(whenToActivate: WhenToActivate):Boolean {
        return whenToActivate.isActivated(lastChoose?.parameterRef?.value?:lastChoose?.parameterRef?.parameter?.value)
    }

}
