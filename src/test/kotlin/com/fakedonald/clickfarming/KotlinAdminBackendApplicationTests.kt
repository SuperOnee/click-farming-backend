package com.fakedonald.clickfarming

import com.fakedonald.clickfarming.cache.GlobalCache
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.annotation.security.RunAs
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KotlinAdminBackendApplicationTests {

    @Autowired
    private lateinit var globalCache: GlobalCache

    @Test
    fun redisTest() {
//        val list = listOf("1", "2", "3")

        val testData = TestData("123", "456")
        globalCache.set("testData", testData)


        val test = globalCache.get<TestData>("testData")
        println(test)
    }


//    @Test
//    fun contextLoads() {
//        val configList = buildList {
//            for (i in 1..24) add(CommissionConfig(BigDecimal.ZERO))
//        }
//
//        for (i in 1..10) {
//            val entity = SalesMan(
//                username = "测试用户${i}",
//                password = passwordEncoder.encode("123456"),
//                shareCode = generateRandomCode(6),
//                commissionConfig = configList
//            )
//
//            suspend {
//                val savedEntity = salesManRepository.save(entity)
//                println(savedEntity)
//            }
//        }
//    }

}


data class TestData(
    @JsonProperty("param1") val param1: String?,
    @JsonProperty("param2") val param2: String?,
)