package com.example.finalprojectapplication

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="response")
data class XmlResponse(
    @Element
    val body : orgaXmlBody
)

@Xml(name="body")
data class orgaXmlBody(
    @Element
    val items : orgaXmlItems
)

@Xml(name="items")
data class orgaXmlItems(
    @Element
    val item : MutableList<orgaXmlItem>
)

@Xml(name="item")
data class orgaXmlItem(
    @PropertyElement
    val biogyNm:String?,
    @PropertyElement
    val childLvbngPilbkNo:String?,
    @PropertyElement
    val cprtCtnt:String?,
    @PropertyElement
    val familyKorNm:String?,
    @PropertyElement
    val familyNm:String?,
    @PropertyElement
    val lvbngKrlngNm:String?,
    @PropertyElement
    val lvbngTpcdNm:String?
) {
    constructor() : this(null, null, null, null, null, null, null)
}
