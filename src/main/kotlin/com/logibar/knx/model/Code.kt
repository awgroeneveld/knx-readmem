package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlElement

data class Code(
    @field:XmlElement(name = "AbsoluteSegment")
    val absoluteSegments: MutableList<AbsoluteSegment>? = LinkedList()
)
