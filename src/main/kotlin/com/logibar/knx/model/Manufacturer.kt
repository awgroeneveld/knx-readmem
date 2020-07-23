package com.logibar.knx.model

import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper

@XmlAccessorType(XmlAccessType.FIELD)
data class Manufacturer(
    @XmlAttribute(name = "RefId")
    val refId: String? = null,
    @field:XmlElementWrapper(name = "ApplicationPrograms")
    @field:XmlElement(name = "ApplicationProgram")
    val applicationPrograms: MutableList<ApplicationProgram>? = LinkedList(),
    @field:XmlElementWrapper(name = "Languages")
    @field:XmlElement(name = "Language")
    val languages: MutableList<MyLanguage>? = LinkedList()
)
