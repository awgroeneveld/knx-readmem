package com.logibar.knx.model



data class ParameterMemory(
    val parameter: Parameter,
    val segment: AbsoluteSegment,
    val offset: Int,
    val bitOffset: Int,
    val numberOfBits: Int,
    val relativeOffset: Int,
    val defaultValue: Int,
    var value: Int? = null
){
    fun startPosInBits(): Long{
        return offset.toLong()*8+bitOffset
    }

    fun endPosInBits():Long{
        return startPosInBits()+numberOfBits
    }
}

