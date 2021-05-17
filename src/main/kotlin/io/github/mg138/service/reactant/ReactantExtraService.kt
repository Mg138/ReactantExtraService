package io.github.mg138.service.reactant

import dev.reactant.reactant.core.ReactantPlugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

@ReactantPlugin(["io.github.mg138.service.reactant"])
class ReactantExtraService : JavaPlugin() {
    companion object {
        lateinit var instance: ReactantExtraService
        lateinit var logger: Logger
    }

    override fun onEnable() {
        instance = this
        Companion.logger = this.logger
    }
}