package com.logibar.knx.util

import com.logibar.knx.model.Parameter


data class ParameterMemory(
    val parameter: Parameter,
    val offset: Int,
    val bitOffset: Int,
    val numberOfBits: Int,
    val defaultValue: Int,
    var value: Int? = null
)

