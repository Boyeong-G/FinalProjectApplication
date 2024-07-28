package com.example.finalprojectapplication

data class EndangeredData(val level:String?, val num: String?, val name: String?, val spe: String?, val img: String?)

data class PhpResponse(val result : ArrayList<EndangeredData>)