package com.example.petmap.data.repository

import com.amap.api.maps.model.LatLng
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

val retrofit: Retrofit = Retrofit.Builder().run {
    baseUrl("http://118.190.204.210")
    addConverterFactory(GsonConverterFactory.create())
    build()
}

const val isProduction = true

val petMapApi: PetMapApi = if (isProduction) {
    retrofit.create(PetMapApi::class.java)
} else {
    MockPetMapApi
}

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

object MockPetMapApi : PetMapApi {
    override suspend fun login(getUser: GetUser) = Empty()

    override suspend fun getMessages(userName: String) = messages

    override suspend fun broadcastPetLostMessage(msg: BroadcastPetLostMessage) = Empty()

    override suspend fun getUserFullInfo(userName: String) = userFullInfo

    override suspend fun updatePetLocation(param: UpdatePetLocation) = Empty()

    val userFullInfo: UserFullInfo
        get() = UserFullInfo(
            user = User(
                userName = "kvii",
                password = "123",
            ),
            home = Home(
                owner = "kvii",
                latitude = 35.9518869,
                longitude = 120.1850354,
            ),
            pets = listOf(
                Pet(
                    petName = "猫",
                    owner = "kvii",
                    latitude = 35.9518869 + 0.001, // 防标记重合
                    longitude = 120.1850354 + 0.001,
                )
            )
        )

    val messages: List<Message>
        get() = listOf(
            Message(
                sender = "kvii",
                receiver = "张三",
                content = "请帮我找找走丢的猫吧。",
            ),
            Message(
                sender = "系统",
                receiver = "kvii",
                content = "您的宠物猫已走丢。",
            ),
        )
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

data class Home(val owner: String, val latitude: Double, val longitude: Double) {
    val latLang: LatLng get() = LatLng(latitude, longitude)
}

data class Pet(
    /** 宠物名 */
    val petName: String,
    /** 主人名 */
    val owner: String,
    /** 纬度 */
    val latitude: Double,
    /** 经度 */
    val longitude: Double,
) {
    val latLang: LatLng get() = LatLng(latitude, longitude)
}

data class UpdatePetLocation(
    /** 宠物名 */
    val petName: String,
    /** 主人名 */
    val owner: String,
    /** 纬度 */
    val latitude: Double,
    /** 经度 */
    val longitude: Double,
)