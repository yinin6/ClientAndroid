package com.example.clientandroid.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PoetryData (
    @SerialName("id"                ) var id                : String?           = null,
    @SerialName("content"           ) var content           : String?           = null,
    @SerialName("popularity"        ) var popularity        : Int?              = null,
    @SerialName("origin"            ) var origin            : PoetryOrigin?          = PoetryOrigin(),
    @SerialName("matchTags"         ) var matchTags         : ArrayList<String> = arrayListOf(),
    @SerialName("recommendedReason" ) var recommendedReason : String?           = null,
    @SerialName("cacheAt"           ) var cacheAt           : String?           = null

)

@Serializable
data class PoetryOrigin (
    @SerialName("title"     ) var title     : String?           = null,
    @SerialName("dynasty"   ) var dynasty   : String?           = null,
    @SerialName("author"    ) var author    : String?           = null,
    @SerialName("content"   ) var content   : ArrayList<String>? = arrayListOf(),
    @SerialName("translate" ) var translate : ArrayList<String>? = arrayListOf()

)

@Serializable
data class Json2Poetry (
    @SerialName("status"    ) var status    : String? = null,
    @SerialName("data"      ) var data      : PoetryData? = null,
    @SerialName("token"     ) var token     : String? = null,
    @SerialName("ipAddress" ) var ipAddress : String? = null,
    @SerialName("warning" ) var warning : String? = null
)






