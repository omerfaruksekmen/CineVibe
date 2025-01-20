package com.omerfaruksekmen.cinevibe.data.entity

// The data class containing the response model returned as a result of the CRUD operations performed in the web service.
data class CRUDResponse(var success : Int,
                        var message : String) {
}