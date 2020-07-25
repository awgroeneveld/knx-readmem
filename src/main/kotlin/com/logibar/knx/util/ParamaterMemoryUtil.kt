package com.logibar.knx.util

import com.logibar.knx.model.Knx
import com.logibar.knx.model.Memory
import com.logibar.knx.model.Parameter
import com.logibar.knx.model.ParameterMemory
import com.logibar.knx.model.Union

class ParamaterMemoryUtil(knx: Knx, private val parameterDefaultValuesByParameter: Map<Parameter, Int>?=null) {

    val paramaterMemoryById: Map<String, ParameterMemory>

    private fun getDefaultValue(parameter: Parameter):Int{
        return parameterDefaultValuesByParameter?.get(parameter)?:parameter.value!!
    }

    init {
        val static = knx.manufacturerData!!.manufacturer!!.applicationPrograms!!.first().static!!
        val parameters =
            static.parametersAndUnions!!.parameterOrUnions!!.filter { it is Parameter && it.memory != null }
                .map { it as Parameter }
        val unions = static.parametersAndUnions!!.parameterOrUnions!!.filter { it is Union && it.memory != null }
            .map { it as Union }
        val paramaterMemories =
            parameters.map { it.id!! to toParameterMemory(it) } +
                unions.flatMap { toParameterMemories(it) }
                    .map { it.parameter.id!! to it }
        paramaterMemoryById=paramaterMemories.toMap()
    }


    private fun toParameterMemory(parameter: Parameter): ParameterMemory {
        val memory = parameter.memory!!
        return toParameterMemory(parameter, memory)
    }

    private fun toParameterMemory(
        parameter: Parameter,
        memory: Memory
    ): ParameterMemory {
        return ParameterMemory(
            parameter = parameter,
            segment=memory.codeSegment!!,
            relativeOffset = memory.offset!!+(parameter.offset?:0),
            offset = memory.codeSegment!!.address!! + memory.offset +(parameter.offset?:0),
            bitOffset = memory.bitOffset!! + (parameter.bitOffset ?: 0),
            numberOfBits = parameter.parameterType!!.getSizeInBits(),
            defaultValue = getDefaultValue(parameter)
        )
    }

    private fun toParameterMemories(union: Union): List<ParameterMemory> {
        val memory = union.memory!!
        return union.parameters!!.map { toParameterMemory(it, memory) }
    }


}
