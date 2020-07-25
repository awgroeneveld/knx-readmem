package com.logibar.knx.model

class TranslationSet(val translationsById: Map<String, TranslationElement>) {
    fun getText(id: String): String? {
        return translationsById[id]?.getText()
    }

    fun getFunctionText(id: String): String? {
        return translationsById[id]?.getFunctionText()
    }

    fun String.replaceCrLf(indent:String):String{
        return this.replace("\r\n", "\n$indent"+" ".repeat("Parameter ".length))
    }
    fun getText(parameterRefRef: ParameterRefRef?, indent: String): String? {
        return if (parameterRefRef == null) null
        else (getText(parameterRefRef.parameterReference, indent))?.replaceCrLf(indent)
    }

    fun getText(parameterRef: ParameterRef?, indent: String): String? {
        return if (parameterRef == null) null
        else (translationsById[parameterRef.id]?.getText()?:getText(parameterRef.parameter, indent))?.replaceCrLf(indent)
    }

    fun getText(parameter: Parameter?, indent:String): String?{
        return if (parameter==null) null
        else ((translationsById[parameter.id]?.getText()?:parameter.text)+ " (value: ${parameter.value})")?.replaceCrLf(indent)
    }

    fun getText(id: String, indent:String): String? {
        return translationsById[id]?.getText()?.replaceCrLf(indent)
    }


}
