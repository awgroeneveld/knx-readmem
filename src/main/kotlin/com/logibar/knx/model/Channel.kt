package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElements

data class Channel(
    @XmlAttribute(name = "Name")
    val name: String? = null,
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlAttribute(name = "Number")
    val number: Int? = null,
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @field:XmlElements(
        XmlElement(
            name = "ParameterBlock",
            type = ParameterBlock::class
        ),
        XmlElement(
            name = "choose",
            type = Choose::class
        )
    )
    val items: MutableList<UiElement>? = LinkedList()
)
