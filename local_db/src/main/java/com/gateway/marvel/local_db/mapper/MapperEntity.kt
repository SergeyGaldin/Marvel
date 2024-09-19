package com.gateway.marvel.local_db.mapper

import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.dto.Comic
import com.gateway.marvel.core.dto.Thumbnail
import com.gateway.marvel.core.mapper.MapperEntity
import com.gateway.marvel.local_db.dto.CharacterLocal
import com.gateway.marvel.local_db.dto.ComicLocal
import com.gateway.marvel.local_db.dto.ThumbnailLocal
import javax.inject.Inject

class CharacterLocalMapper @Inject constructor() : MapperEntity<Character, CharacterLocal> {
    override fun anotherToDomainMap(domain: Character) = CharacterLocal(
        id = domain.id,
        name = domain.name,
        thumbnail = ThumbnailLocal(
            domain.thumbnail.path,
            domain.thumbnail.extension
        ),
        isFavorite = domain.isFavorite
    )

    override fun domainToAnotherMap(another: CharacterLocal) = Character(
        id = another.id,
        name = another.name,
        thumbnail = Thumbnail(
            another.thumbnail.path,
            another.thumbnail.extension
        ),
        isFavorite = another.isFavorite
    )

    override fun anotherToDomainMaps(domainList: List<Character>) = domainList.map {
        anotherToDomainMap(it)
    }

    override fun domainToAnotherMaps(anotherList: List<CharacterLocal>) = anotherList.map {
        domainToAnotherMap(it)
    }
}

class ComicLocalMapper @Inject constructor() : MapperEntity<Comic, ComicLocal> {
    override fun anotherToDomainMap(domain: Comic) = ComicLocal(
        id = domain.id,
        title = domain.title,
        thumbnail = ThumbnailLocal(
            domain.thumbnail.path,
            domain.thumbnail.extension
        ),
        isFavorite = domain.isFavorite
    )

    override fun domainToAnotherMap(another: ComicLocal) = Comic(
        id = another.id,
        title = another.title,
        thumbnail = Thumbnail(
            another.thumbnail.path,
            another.thumbnail.extension
        ),
        isFavorite = another.isFavorite
    )

    override fun anotherToDomainMaps(domainList: List<Comic>) = domainList.map {
        anotherToDomainMap(it)
    }

    override fun domainToAnotherMaps(anotherList: List<ComicLocal>) = anotherList.map {
        domainToAnotherMap(it)
    }
}