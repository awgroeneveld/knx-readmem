package com.logibar.knx.model

interface UiElement {
    fun indentString(indent: Int) = "  ".repeat(indent)
    fun toLogString(indent: Int, translationSet: TranslationSet): String
    fun accept(visitor: UiElementVisitor)
}

interface UiElementVisitor {
    fun visit(parameterBlock: ParameterBlock)
    fun visit(choose: Choose)
    fun visit(comObjectRefRef: ComObjectRefRef)
    fun visit(parameterRefRef: ParameterRefRef)
    fun visit(whenToActivate: WhenToActivate)
}

class UIElementTranslator(private val translationsById: Map<String, TranslationElement>) : UiElementVisitor {
    private fun getText(id: String?): String? =
        translationsById[id!!]?.getText()


    private fun getText(parameterReference: ParameterRef): String? =
        getText(parameterReference.id) ?: getText(parameterReference.parameter!!) ?:parameterReference.text

    private fun getText(parameter: Parameter): String? =
        getText(parameter.id)?:parameter.text

    private fun getText(comObjectReference: ComObjectRef) =
        getText(comObjectReference.id) ?: getText(comObjectReference.comObject!!) ?:comObjectReference.text

    private fun getText(comObject: ComObject) =
        getText(comObject.id)?:comObject.text

    override fun visit(parameterBlock: ParameterBlock) {
        parameterBlock.translatedText = getText(parameterBlock.id)
            ?: (if (parameterBlock.parameterRef == null) null else getText(parameterBlock.parameterRef))
    }

    override fun visit(choose: Choose) = Unit

    override fun visit(comObjectRefRef: ComObjectRefRef) {
        val comObjectReference = comObjectRefRef.comObjectReference!!
        comObjectReference.translatedText = getText(comObjectReference)
    }


    override fun visit(parameterRefRef: ParameterRefRef) {
        val parameterReference = parameterRefRef.parameterReference!!
        parameterReference.translatedText = getText(parameterReference)
    }

    override fun visit(whenToActivate: WhenToActivate) = Unit
}

class UIElementPrinter() : UiElementVisitor {
    override fun visit(parameterBlock: ParameterBlock) {
        TODO("Not yet implemented")
    }

    override fun visit(choose: Choose) {
        TODO("Not yet implemented")
    }

    override fun visit(comObjectRefRef: ComObjectRefRef) {
        TODO("Not yet implemented")
    }

    override fun visit(parameterRefRef: ParameterRefRef) {
        TODO("Not yet implemented")
    }

    override fun visit(whenToActivate: WhenToActivate) {
        TODO("Not yet implemented")
    }

}
