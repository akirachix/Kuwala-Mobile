package com.akirachix.dishhub

data class Vegetables(
    var id: Int,
    var name: String,
    var quantity: Int = 0,
    var isSelected: Boolean = false,
    var avatar:String,
)
