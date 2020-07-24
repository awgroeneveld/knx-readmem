package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlID
import javax.xml.bind.annotation.XmlSchemaType

class AbsoluteSegment(
    @XmlID
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlAttribute(name = "Address")
    val address: Int? = null,
    @XmlAttribute(name = "Size")
    val size: Int? = null,
    @field:XmlElement(name = "Data")
    @field:XmlSchemaType(name = "base64Binary")
    val data: ByteArray? = null,
    @field:XmlElement(name = "Mask")
    @field:XmlSchemaType(name = "base64Binary")
    val mask: ByteArray? = null
){
    fun endAddress()=this.address!!+this.size!!
}
