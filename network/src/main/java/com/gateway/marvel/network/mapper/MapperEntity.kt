package com.gateway.marvel.network.mapper

import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.dto.Comic
import com.gateway.marvel.core.dto.Thumbnail
import com.gateway.marvel.core.mapper.MapperEntity
import com.gateway.marvel.network.dto.CharacterNetwork
import com.gateway.marvel.network.dto.ComicNetwork
import com.gateway.marvel.network.dto.ThumbnailNetwork
import javax.inject.Inject

class CharacterNetworkMapper @Inject constructor() : MapperEntity<Character, CharacterNetwork> {
    override fun anotherToDomainMap(domain: Character) = CharacterNetwork(
        id = domain.id,
        name = domain.name,
        thumbnail = ThumbnailNetwork(
            domain.thumbnail.path,
            domain.thumbnail.extension
        )
    )

    override fun domainToAnotherMap(another: CharacterNetwork) = Character(
        id = another.id,
        name = another.name,
        thumbnail = Thumbnail(
            another.thumbnail.path,
            another.thumbnail.extension
        )
    )

    override fun anotherToDomainMaps(domainList: List<Character>) = domainList.map {
        anotherToDomainMap(it)
    }

    override fun domainToAnotherMaps(anotherList: List<CharacterNetwork>) = anotherList.map {
        domainToAnotherMap(it)
    }
}

class ComicNetworkMapper @Inject constructor() : MapperEntity<Comic, ComicNetwork> {
    override fun anotherToDomainMap(domain: Comic) = ComicNetwork(
        id = domain.id,
        title = domain.title,
        thumbnail = ThumbnailNetwork(
            domain.thumbnail.path,
            domain.thumbnail.extension
        )
    )

    override fun domainToAnotherMap(another: ComicNetwork) = Comic(
        id = another.id,
        title = another.title,
        thumbnail = Thumbnail(
            another.thumbnail.path,
            another.thumbnail.extension
        )
    )

    override fun anotherToDomainMaps(domainList: List<Comic>) = domainList.map {
        anotherToDomainMap(it)
    }

    override fun domainToAnotherMaps(anotherList: List<ComicNetwork>) = anotherList.map {
        domainToAnotherMap(it)
    }
}