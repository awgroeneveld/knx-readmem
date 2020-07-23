package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlID

data class ComObject(
    @XmlID
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlAttribute(name = "Name")
    val name: String? = null,
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlAttribute(name = "Number")
    val number: Int? = null,
    @XmlAttribute(name = "FunctionText")
    val functionText: String? = null,
    @XmlAttribute(name = "ObjectSize")
    val objectSize: String? = null,
    @XmlAttribute(name = "ReadFlag")
    val readFlag: EnabledDisabled? = null,
    @XmlAttribute(name = "WriteFlag")
    val writeFlag: EnabledDisabled? = null,
    @XmlAttribute(name = "CommunicationFlag")
    val communicationFlag: EnabledDisabled? = null,
    @XmlAttribute(name = "TransmitFlag")
    val transmitFlag: EnabledDisabled? = null,
    @XmlAttribute(name = "UpdateFlag")
    val updateFlag: EnabledDisabled? = null,
    @XmlAttribute(name = "ReadOnInitFlag")
    val readOnInitFlag: EnabledDisabled? = null
)
