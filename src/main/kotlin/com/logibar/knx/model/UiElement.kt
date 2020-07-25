package com.logibar.knx.model


interface UiElement {
    fun indentString(indent: Int) = "  ".repeat(indent)
    fun toLogString(
        indent: Int,
        translationSet: TranslationSet,
        deviceChanges: Map<String, ParameterMemory>
    ): String
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

    fun getDefaultValuesById()=parameterDefaultValuesById.toMap()

    private fun addIfNotNull(parameterReference: ParameterRef?) {
        if (parameterReference?.value != null)
            parameterDefaultValuesById[parameterReference.parameter!!.id!!] = parameterReference.value
    }


    override fun visit(parameterBlock: ParameterBlock):Boolean {
        addIfNotNull(parameterBlock.parameterRef)
        return true
    }

    override fun visit(choose: Choose):Boolean {
        addIfNotNull(choose.parameterRef)
        val activeWhens=choose.getActiveWhens(if (choose.parameterRef==null) null else getValue(choose.parameterRef))
        activeWhens?.forEach { visit(it) }
        return false
    }

    private fun getValue(parameterRef: ParameterRef): Int {
        val parameter=parameterRef.parameter!!
        return parameterRef.value?:parameterDefaultValuesById[parameter.id]?:parameter.value!!
    }

    override fun visit(comObjectRefRef: ComObjectRefRef):Boolean {
        return true
    }

    override fun visit(parameterRefRef: ParameterRefRef):Boolean {
        addIfNotNull(parameterRefRef.parameterReference)
        return true
    }

    override fun visit(whenToActivate: WhenToActivate):Boolean {
        return true
    }

}
