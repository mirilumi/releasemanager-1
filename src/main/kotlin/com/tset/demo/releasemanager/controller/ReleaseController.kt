package com.tset.demo.releasemanager.controller

import com.tset.demo.releasemanager.dtos.DeployDto
import com.tset.demo.releasemanager.jpa.SystemVersion
import com.tset.demo.releasemanager.service.SaleManagerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/")
class ReleaseController(val saleManagerService: SaleManagerService) {

    @GetMapping("services")
    fun getReleases(@RequestParam("systemVersion", required = true) systemVersion: Int): SystemVersion {
        return saleManagerService.findBySystemVersion(systemVersion)
    }

    @PostMapping("deploy")
    fun deployService(@RequestBody deployDto: DeployDto): SystemVersion {
        return saleManagerService.deploy(deployDto)
    }
}