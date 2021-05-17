package io.github.mg138.service.reactant.service

import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import io.lumine.xikage.mythicmobs.MythicMobs
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper
import org.bukkit.Bukkit

@Component
class MythicMobService : LifeCycleHook {
    lateinit var mythicMobs: MythicMobs
    lateinit var mythicMobHelper: BukkitAPIHelper

    var enabled = false
        private set

    override fun onEnable() {
        val mythicMobs = Bukkit.getPluginManager().getPlugin("MythicMobs") as? MythicMobs ?: return

        this.mythicMobs = mythicMobs
        this.mythicMobHelper = mythicMobs.apiHelper

        this.enabled = true
    }
}