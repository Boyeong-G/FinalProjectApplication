package com.example.finalprojectapplication

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="response")
data class ResultXmlResponse(
    @Element
    val body : orgaResultXmlBody
)

@Xml(name="body")
data class orgaResultXmlBody(
    @Element
    val item : orgaResultXmlItem
)

@Xml(name="item")
data class orgaResultXmlItem(
    @PropertyElement
    val biogyNm:String?,
    @PropertyElement
    val childLvbngPilbkNo:String?,
    @PropertyElement
    val cprtCtnt:String?,
    @PropertyElement
    val famlKrlngNm:String?,
    @PropertyElement
    val famlNm:String?,
    @PropertyElement
    val genusKrlngNm:String?,
    @PropertyElement
    val genusNm:String?,
    @PropertyElement
    val hbttNm:String?,
    @PropertyElement
    val imgUrl1:String?,
    @PropertyElement
    val imgUrl2:String?,
    @PropertyElement
    val lvbngDscrt:String?,
    @PropertyElement
    val lvbngKrlngNm:String?,
    @PropertyElement
    val lvbngTpcdNm:String?,
    @PropertyElement
    val ordKrlngNm:String?,
    @PropertyElement
    val ordNm:String?,
    @PropertyElement
    val prtctSpecsTpcdNm:String?
) {
    constructor() : this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)
}
