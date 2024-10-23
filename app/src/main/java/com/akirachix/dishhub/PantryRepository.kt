

package com.akirachix.dishhub

object PantryRepository {
    val pantryItems: MutableList<PantryItems> = mutableListOf()

    fun addPantryItem(item: PantryItems) {
        pantryItems.add(item)
    }

    fun removePantryItem(item: PantryItems) {
        pantryItems.remove(item)
    }
}


