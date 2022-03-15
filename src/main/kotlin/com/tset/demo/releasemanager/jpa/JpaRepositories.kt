package com.tset.demo.releasemanager.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable
import java.util.*


@NoRepositoryBean
interface BaseRepository<ENTITY : BaseEntity, ID : Serializable> :
    JpaRepository<ENTITY, ID>
interface ServiceDeployedRepository : BaseRepository<TsetService, Int> {
    fun findByName(name: String) : Optional<TsetService>
    fun findByServiceVersion(version: Int) : Optional<TsetService>
}
interface SystemVersionRepository : BaseRepository<SystemVersion, Int> {
    fun findByTsetServices(tsetService: TsetService) : SystemVersion
}
