package com.tset.demo.releasemanager.service

import com.tset.demo.releasemanager.jpa.SystemVersion
import com.tset.demo.releasemanager.jpa.TsetService
import com.tset.demo.releasemanager.dtos.DeployDto
import java.util.*

interface SaleManagerService {
    fun deploy(deployDto: DeployDto) : SystemVersion
    fun findByName(name: String) : Optional<TsetService>
    fun findByVersion(version: Int): Optional<TsetService>
    fun findBySystemVersion(version: Int): SystemVersion
}