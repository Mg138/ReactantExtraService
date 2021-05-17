package io.github.mg138.service.reactant.service

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.ListenerOptions
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import io.github.mg138.service.reactant.ReactantExtraService
import org.bukkit.Bukkit

@Component
class PacketService : LifeCycleHook {
    class PacketAdapterService(private val protocolManager: ProtocolManager) {
        private fun PacketAdapter.register() = protocolManager.addPacketListener(this)

        class Registering(private val packetAdapterService: PacketAdapterService) {
            fun PacketType.onReceiving(
                priority: ListenerPriority = ListenerPriority.NORMAL,
                vararg options: ListenerOptions,
                listener: (PacketEvent) -> Unit
            ) = packetAdapterService.onReceiving(listener, setOf(this), priority, *options)

            fun onReceiving(
                types: Iterable<PacketType>,
                priority: ListenerPriority = ListenerPriority.NORMAL,
                vararg options: ListenerOptions,
                listener: (PacketEvent) -> Unit
            ) = packetAdapterService.onReceiving(listener, types, priority, *options)

            fun PacketType.onSending(
                priority: ListenerPriority = ListenerPriority.NORMAL,
                vararg options: ListenerOptions,
                listener: (PacketEvent) -> Unit
            ) = packetAdapterService.onSending(listener, setOf(this), priority, *options)

            fun onSending(
                types: Iterable<PacketType>,
                priority: ListenerPriority = ListenerPriority.NORMAL,
                vararg options: ListenerOptions,
                listener: (PacketEvent) -> Unit
            ) = packetAdapterService.onSending(listener, types, priority, *options)
        }

        fun onSending(
            listener: (PacketEvent) -> Unit,
            types: Iterable<PacketType>,
            priority: ListenerPriority,
            vararg options: ListenerOptions
        ) = object : PacketAdapter(ReactantExtraService.instance, priority, types, *options) {
            override fun onPacketSending(event: PacketEvent) {
                listener(event)
            }
        }.also { it.register() }

        fun onReceiving(
            listener: (PacketEvent) -> Unit,
            types: Iterable<PacketType>,
            priority: ListenerPriority,
            vararg options: ListenerOptions
        ) = object : PacketAdapter(ReactantExtraService.instance, priority, types, *options) {
            override fun onPacketReceiving(event: PacketEvent) {
                listener(event)
            }
        }.also { it.register() }

        operator fun invoke(registering: Registering.() -> Unit) {
            Registering(this).apply(registering)
        }
    }

    lateinit var protocolManager: ProtocolManager
    lateinit var packetAdapterService: PacketAdapterService

    var enabled = false
        private set

    override fun onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) return

        this.protocolManager = ProtocolLibrary.getProtocolManager()
        this.packetAdapterService = PacketAdapterService(protocolManager)

        this.enabled = true
    }
}