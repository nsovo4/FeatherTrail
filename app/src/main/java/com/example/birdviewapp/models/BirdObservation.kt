package com.example.birdviewapp.models

data class BirdObservation(
    val speciesCode: String,
    val comName: String,
    val sciName: String,
    val loc: Location,
    val obsDt: String,
    val howMany: Int,
    val obsValid: Boolean,
    val obsReviewed: Boolean,
    val locationPrivate: Boolean,
    val region: Region,
    val user: User,
    val subId: String,
    val obsId: String,
    val checklistId: String,
    val presenceNoted: Boolean,
    val hasComments: Boolean,
    val hasRichMedia: Boolean
)

