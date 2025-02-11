package com.example.petmap.data.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

val retrofit: Retrofit = Retrofit.Builder().run {
    baseUrl("http://118.190.210.87")
    addConverterFactory(GsonConverterFactory.create())
    build()
}

val petMapApi: PetMapApi = retrofit.create(PetMapApi::class.java)

interface PetMapApi {
    @POST("api/v1/login")
    suspend fun login(@Body getUser: GetUser): Empty

    @GET("api/v1/message/{userName}")
    suspend fun getMessages(@Path("userName") userName: String): List<Message>

    @POST("api/v1/broadcast/pet/lost")
    suspend fun broadcastPetLostMessage(@Body msg: BroadcastPetLostMessage): Empty

    @GET("api/v1/user/info/full/{userName}")
    suspend fun getUserFullInfo(@Path("userName") userName: String): UserFullInfo

    @PUT("api/v1/pet/location")
    suspend fun updatePetLocation(@Body param: UpdatePetLocation): Empty
}

class Empty

/** 获取用户请求 */
data class GetUser(val userName: String, val password: String)

/** 消息 */
data class Message(
    val sender: String,
    val receiver: String,
    val content: String,
)

/** 广播宠物丢失信息 */
data class BroadcastPetLostMessage(val petName: String, val owner: String)

/** 用户数据 */
data class UserFullInfo(
    val user: User,
    val home: Home?,
    val pets: List<Pet>,
)

data class User(val userName: String, val password: String)

data class Home(val owner: String, val longitude: Double, val latitude: Double)

data class Pet(
    /** 宠物名 */
    val petName: String,
    /** 主人名 */
    val owner: String,
    /** 经度 */
    val longitude: Double,
    /** 纬度 */
    val latitude: Double,
)

data class UpdatePetLocation(
    /** 宠物名 */
    val petName: String,
    /** 主人名 */
    val owner: String,
    /** 经度 */
    val longitude: Double,
    /** 纬度 */
    val latitude: Double,
)