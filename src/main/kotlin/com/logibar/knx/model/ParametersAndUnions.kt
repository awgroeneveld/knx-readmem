package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElements

class ParametersAndUnions() {
    @field:XmlElements(
        XmlElement(
            name = "Parameter",
            type = Parameter::class
        ),
        XmlElement(
            name = "Union",
            type = Union::class
        )
    )
    val parameterOrUnions: MutableList<ParameterOrUnion>? =
        LinkedList()

    fun getAllParameters()=
        parameterOrUnions!!.filterIsInstance<Parameter>().filter { it.memory!=null } +
                parameterOrUnions.filterIsInstance<Union>().flatMap { it.parameters!! }
}
