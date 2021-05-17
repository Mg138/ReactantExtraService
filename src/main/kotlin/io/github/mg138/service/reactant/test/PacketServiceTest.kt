package io.github.mg138.service.reactant.test

import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import io.github.mg138.service.reactant.ReactantExtraService
import io.github.mg138.service.reactant.service.PacketService

@Component
class PacketServiceTest(
    private val packetService: PacketService
) : LifeCycleHook {
    override fun onEnable() {
        if (packetService.enabled) {
            ReactantExtraService.logger.info(
                """PacketService enabled. Testing:
                   - protocolManager: ${packetService.protocolManager}
                   - packetAdapterService: ${packetService.packetAdapterService}""".trimIndent()
            )
        }
    }
}