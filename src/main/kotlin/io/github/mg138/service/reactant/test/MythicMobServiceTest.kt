package io.github.mg138.service.reactant.test

import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import io.github.mg138.service.reactant.ReactantExtraService
import io.github.mg138.service.reactant.service.MythicMobService

@Component
class MythicMobServiceTest(
    private val mythicMobService: MythicMobService
) : LifeCycleHook {
    override fun onEnable() {
        if (mythicMobService.enabled) {
            ReactantExtraService.logger.info(
                """MythicMobService enabled. Testing:
                   - mythicMobs: ${mythicMobService.mythicMobs}
                   - mythicMobHelper: ${mythicMobService.mythicMobHelper}""".trimIndent()
            )
        }
    }
}