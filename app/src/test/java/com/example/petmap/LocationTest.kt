package com.example.petmap

import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.LatLng
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LocationTest {
    @Test
    fun latitude_diff() {
        val l = LatLng(35.9518869, 120.1850354)

        // 纬度 正负值
        val l11 = LatLng(l.latitude + 0.0055, l.longitude)
        val d11 = AMapUtils.calculateLineDistance(l, l11)

        val l12 = LatLng(l.latitude - 0.0055, l.longitude)
        val d12 = AMapUtils.calculateLineDistance(l, l12)

        println("lat ±0.0055: $d11, $d12")
        assertTrue(d11 > 500)
        assertTrue(d12 > 500)
    }

    @Test
    fun longitude_diff() {
        val l = LatLng(35.9518869, 120.1850354)

        // 经度 正负值
        val l11 = LatLng(l.latitude, l.longitude + 0.0065)
        val d11 = AMapUtils.calculateLineDistance(l, l11)

        val l12 = LatLng(l.latitude, l.longitude - 0.0065)
        val d12 = AMapUtils.calculateLineDistance(l, l12)

        println("lng ±0.0065: $d11, $d12")
        assertTrue(d11 > 500)
        assertTrue(d12 > 500)
    }
}