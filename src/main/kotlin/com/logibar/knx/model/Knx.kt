package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "KNX")
data class Knx(
    @field:XmlElement(name = "ManufacturerData")
    val manufacturerData: ManufacturerData? = null,
    @XmlAttribute(name = "CreatedBy")
    val createdBy: String? = null
)
