package com.gateway.marvel.core.mapper

interface MapperEntity<DOMAIN, ANOTHER> {
    fun anotherToDomainMap(domain: DOMAIN): ANOTHER
    fun domainToAnotherMap(another: ANOTHER): DOMAIN
    fun anotherToDomainMaps(domainList: List<DOMAIN>): List<ANOTHER>
    fun domainToAnotherMaps(anotherList: List<ANOTHER>): List<DOMAIN>
}