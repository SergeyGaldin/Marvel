package com.gateway.marvel.core.mock

import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.dto.Comic
import com.gateway.marvel.core.dto.Thumbnail

object MockEntity  {
    fun mockThumbnail() = Thumbnail(
        path = "",
        extension = ""
    )

    fun mockCharacter() = Character(
        id = 0,
        name = "Spider-Man",
        thumbnail = mockThumbnail()
    )

    fun mockComic() = Comic(
        id = 0,
        title = "SPIDER-BOY ANNUAL #1",
        thumbnail = mockThumbnail()
    )
}