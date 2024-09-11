package com.gateway.marvel.network.utils

import java.math.BigInteger
import java.security.MessageDigest

fun getMd5Hash(value: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(value.toByteArray()))
        .toString(16)
        .padStart(32, '0')
}