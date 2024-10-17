package com.akirachix.dishhub

data class Fruits(
    var name: String,
    var quantity: Int,
    var avatar: String,
    var isSelected: Boolean = false, // To track the selection state
    val id: Any
) {

}
