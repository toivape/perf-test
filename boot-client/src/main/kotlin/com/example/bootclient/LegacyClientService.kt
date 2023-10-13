package com.example.bootclient

import org.springframework.stereotype.Service

@Service
class LegacyClientService(val legacyFeignClient: LegacyFeignClient) {

    fun getLegacy(id: Long): Person? = legacyFeignClient.getLegacyById(id)
}
