package com.gateway.marvel.core.mock

import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.dto.Thumbnail

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