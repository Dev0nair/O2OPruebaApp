package com.igonris.o2opruebaapp.repository.model

data class Beer(
    var id: String,
    var name: String,
    var description: String,
    var image_url: String,
    var tagline: String,
    var abv: Double
) {

}
