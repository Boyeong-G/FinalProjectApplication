package com.example.finalprojectapplication

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitConnection{
    companion object {
        private const val BASE_URL_PHP = "http://192.168.219.104/"
        val phpNetworkService: NetworkService
        val phpRetrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL_PHP)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        init{
            phpNetworkService = phpRetrofit.create(NetworkService::class.java)
        }
    }
}

class RetrofitConnectionOrganism{
    companion object {
        private const val BASE_URL = "http://apis.data.go.kr/1400119/ChildService1/"

        var xmlNetworkServiceOrganism : NetworkServiceOrganism
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()
        init{
            xmlNetworkServiceOrganism = xmlRetrofit.create(NetworkServiceOrganism::class.java)
        }
    }
}

class RetrofitConnectionResultOrganism{
    companion object {
        private const val BASE_URL = "http://apis.data.go.kr/1400119/ChildService1/"

        var xmlNetworkServiceResultOrganism : NetworkServiceResultOrganism
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()
        init{
            xmlNetworkServiceResultOrganism = xmlRetrofit.create(NetworkServiceResultOrganism::class.java)
        }
    }
}