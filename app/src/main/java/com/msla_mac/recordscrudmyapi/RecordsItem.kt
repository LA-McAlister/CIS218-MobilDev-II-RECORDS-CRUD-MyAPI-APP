package com.msla_mac.recordscrudmyapi

class RecordsItem (var recordID: Int, var name: String, var description: String, var price: Double, var rating: Int, var image: String, var dateModified: String, var dateCreated : String  ) {

    fun toCSV(): String {
        return "$recordID, $name, $description, $price, $rating, $image, $dateModified, $dateCreated"
    }

    override fun toString(): String {
        return "$name : $description $price -- $rating $image"
    }
}