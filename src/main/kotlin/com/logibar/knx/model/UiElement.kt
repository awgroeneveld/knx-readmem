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

class UIElementTranslator(private val translationSet: TranslationSet) : UiElementVisitor {

    override fun visit(parameterBlock: ParameterBlock) {
        parameterBlock.translatedText = translationSet.getText(parameterBlock.id!!)
            ?:parameterBlock.parameterRef?.translation
    }

    override fun visit(choose: Choose) = Unit
    override fun visit(comObjectRefRef: ComObjectRefRef)=Unit
    override fun visit(parameterRefRef: ParameterRefRef) =Unit
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
