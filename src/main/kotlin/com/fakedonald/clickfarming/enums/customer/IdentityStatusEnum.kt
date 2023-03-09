package com.fakedonald.clickfarming.enums.customer

import com.fasterxml.jackson.annotation.JsonValue

enum class IdentityStatusEnum(@get:JsonValue val value: String) {

    PENDING_SUBMIT("待提交"),

    PENDING("待审核"),

    PASS("审核通过"),

    FAIL("审核未过")
}