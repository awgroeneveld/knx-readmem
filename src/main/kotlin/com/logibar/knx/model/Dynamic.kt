package com.logibar.knx.model

import javax.xml.bind.annotation.XmlElement

data class Dynamic(
    @field:XmlElement(name = "Channel")
    val channel: Channel? = null
)
