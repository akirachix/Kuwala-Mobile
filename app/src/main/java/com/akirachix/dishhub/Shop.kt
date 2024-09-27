
//package com.akirachix.dishhub
//
//import kotlin.time.DurationUnit
//
//data class ShoppingItem(
//    val id: Int,
//    var name: String,
//    var quantity: string,
//    var isChecked: Boolean = false
//)



package com.akirachix.dishhub

data class ShoppingItem(
    val id: Int, // Unique identifier for each shopping item
    var name: String, // Name of the item, e.g. "Rice"
    var quantityWithUnit: String, // Updated to represent quantity with unit like "2 kg"
    var isChecked: Boolean = false // For tracking if the item has been checked off the list
)