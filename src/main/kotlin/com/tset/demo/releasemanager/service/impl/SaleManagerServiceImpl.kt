package com.tset.demo.releasemanager.service.impl

import com.tset.demo.releasemanager.jpa.ServiceDeployedRepository
import com.tset.demo.releasemanager.jpa.SystemVersionRepository
import com.tset.demo.releasemanager.jpa.SystemVersion
import com.tset.demo.releasemanager.jpa.TsetService
import com.tset.demo.releasemanager.dtos.DeployDto
import com.tset.demo.releasemanager.exceptions.CustomException
import com.tset.demo.releasemanager.exceptions.ErrorCode
import com.tset.demo.releasemanager.exceptions.MessageKey
import com.tset.demo.releasemanager.service.SaleManagerService
import java.util.*
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class SaleManagerServiceImpl(
    val serviceDeployedRepository: ServiceDeployedRepository,
    val systemVersionRepository: SystemVersionRepository
) : SaleManagerService {
    override fun deploy(deployDto: DeployDto): SystemVersion {
        val optionalServiceDeployed = findByName(deployDto.name)
        return if (optionalServiceDeployed.isEmpty)
            systemVersionRepository.saveAndFlush(SystemVersion().apply {
                val services = serviceDeployedRepository.findAll()
                services.add(TsetService().apply {
                    this.name = deployDto.name
                    this.serviceVersion = deployDto.version
                })
                this.tsetServices = services.toMutableSet()
            })
        else
            systemVersionRepository.findByTsetServices(optionalServiceDeployed.get())
    }

    override fun findByName(name: String): Optional<TsetService> {
        return serviceDeployedRepository.findByName(name)
    }

    override fun findByVersion(version: Int): Optional<TsetService> {
        return serviceDeployedRepository.findByServiceVersion(version)
    }

    override fun findBySystemVersion(version: Int): SystemVersion {
        val optionalEntity = systemVersionRepository.findById(version)
        if (optionalEntity.isPresent)
            return optionalEntity.get()
        else
            throw CustomException(ErrorCode.RESOURCE_NOT_FOUND, MessageKey.ENTITY_NOT_FOUND, version)
    }
}