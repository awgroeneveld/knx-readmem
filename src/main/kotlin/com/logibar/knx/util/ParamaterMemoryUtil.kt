package com.logibar.knx.util

import com.logibar.knx.model.Knx
import com.logibar.knx.model.Parameter
import com.logibar.knx.model.Union

class ParamaterMemoryUtil(knx: Knx) {
    val paramaterMemoryById: Map<String, ParameterMemory>

    init {
        val static = knx.manufacturerData!!.manufacturer!!.applicationPrograms!!.first().static!!
        val parameters =
            static.parametersAndUnions!!.parameterOrUnions!!.filter { it is Parameter && it.memory != null }
                .map { it as Parameter }
        paramaterMemoryById = parameters.map { it.id!! to toParameterMemory(it) }
            .toMap()

    }


    private fun toParameterMemory(parameter: Parameter): ParameterMemory {
        val memory = parameter.memory!!
        return ParameterMemory(
            parameter,
            memory.codeSegment!!.address!! + memory.offset!!,
            memory.bitOffset!!,
            parameter.parameterType!!.getSizeInBits(),
            parameter.value!!
        )
    }

    private fun toParameterMemories(union: Union): List<ParameterMemory> {
        val memory = union.memory!!

    }


}
