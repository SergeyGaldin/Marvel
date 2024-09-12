package com.gateway.marvel.core.dto

object MockEntity  {
    fun mockThumbnail() = Thumbnail(
        path = "",
        extension = ""
    )

    fun mockCharacter() = Character(
        id = 0,
        name = "Spider-Man",
        description = "",
        thumbnail = mockThumbnail()
    )
}