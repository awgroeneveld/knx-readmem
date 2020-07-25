package com.logibar.knx.model

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlID
import javax.xml.bind.annotation.XmlIDREF

data class ComObjectRef(
    @XmlID
    @XmlAttribute(name = "Id")
    val id: String? = null,
    @XmlIDREF
    @XmlAttribute(name = "RefId")
    val comObject: ComObject? = null,
    @XmlAttribute(name = "Tag")
    val tag: Int? = null,
    @XmlAttribute(name = "Text")
    val text: String? = null,
    @XmlAttribute(name = "FunctionText")
    val functionText: String? = null,
    @XmlAttribute(name = "ReadFlag")
    val readFlag: EnabledDisabled? = null,
//    val writeFlag: EnabledDisabled,
//    val communicationFlag: EnabledDisabled,
    @XmlAttribute(name = "TransmitFlag")
    val transmitFlag: EnabledDisabled? = null
//    val updateFlag: EnabledDisabled,
//    val readOnInitFlag: EnabledDisabled
) {

    var translation: String? = null
    var functionTranslation: String? = null
}
