package com.vinio.firstlab.entity

import kotlinx.serialization.Serializable


@Serializable
data class Character(
    var name: String? = null,
    val culture: String? = null,
    val born: String? = null,
    val titles: List<String>? = null,
    val aliases: List<String>? = null,
    val playedBy: List<String>? = null,
) {}