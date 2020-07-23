package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
data class ManufacturerData(
    @field:XmlElement(name = "Manufacturer")
    val manufacturer: Manufacturer? = null
)
