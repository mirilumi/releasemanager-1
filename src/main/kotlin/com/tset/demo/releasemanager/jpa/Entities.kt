package com.tset.demo.releasemanager.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.Instant
import java.util.*
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.MappedSuperclass
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.constraints.NotBlank
import kotlin.collections.HashSet

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createdDate: Instant = Instant.now()

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    var lastModifiedDate: Instant = Instant.now()
}

@Entity
class TsetService : BaseEntity(){

    @NotBlank
    @Column(nullable = false, length = 255,unique = true)
    lateinit var name: String

    @Column(nullable = false)
    var serviceVersion: Int = 0

    @ManyToMany(mappedBy = "tsetServices")
    @JsonIgnore
    val systemVersions: MutableSet<SystemVersion> = HashSet(0)
}

@Entity
class SystemVersion : BaseEntity(){

    @ManyToMany(cascade = [CascadeType.ALL])
    var tsetServices: MutableSet<TsetService> = HashSet(0)
}