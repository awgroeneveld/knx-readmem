package com.logibar.knx.model

interface UiElement {
    fun indentString(indent: Int) = "  ".repeat(indent)
    fun toLogString(indent: Int, translationSet: TranslationSet): String
}
