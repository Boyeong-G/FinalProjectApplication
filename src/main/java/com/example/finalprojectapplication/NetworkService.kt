package com.example.finalprojectapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("PHP_connection.php")
    fun getPhpList(
        @Query("spe") spe:String
    ) : Call<PhpResponse>
}

interface NetworkServiceOrganism {
    @GET("childIlstrSearch")
    fun getXmlList(
        @Query("serviceKey") serviceKey:String,
        @Query("st") st:Int,
        @Query("sw") sw:String,
        @Query("numOfRows") numOfRows:Int,
        @Query("pageNo") pageNo:Int
    ) : Call<XmlResponse>
}

interface NetworkServiceResultOrganism {
    @GET("childIlstrInfo")
    fun getResultXmlList(
        @Query("serviceKey") serviceKey:String,
        @Query("q1") q1:Int
    ) : Call<ResultXmlResponse>
}