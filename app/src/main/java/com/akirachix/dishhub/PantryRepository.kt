//package com.akirachix.dishhub
//
//import PantryItems
////
////object PantryRepository {
////    val pantryItems: MutableList<PantryItems> = mutableListOf()
////}
//object PantryRepository {
//    val pantryItems: MutableList<PantryItems> = mutableListOf()
//
//    fun addPantryItem(item: PantryItems) {
//        pantryItems.add(item)
//    }
//
//    fun getPantryItems(): List<PantryItems> {
//
//    }
//}


//package com.akirachix.dishhub
//
//import PantryItems
//
//object PantryRepository {
//    val pantryItems: MutableList<PantryItems> = mutableListOf()
//
//    fun addPantryItem(item: PantryItems) {
//        pantryItems.add(item)
//    }
//
//    fun getPantryItems(): List<PantryItems> {
//        return pantryItems
//    }
//}
//
//package com.akirachix.dishhub
//
//
//class PantryRepository {
//    companion object {
//        val pantryItems = mutableListOf<PantryItems>()
//
//        fun clearPantry() {
//            pantryItems.clear()
//        }
//
//        fun addPantryItem(item: PantryItems) {
//            pantryItems.add(item)
//        }
//    }
//}

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



