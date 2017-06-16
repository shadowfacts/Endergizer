package net.shadowfacts.endergizer

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import net.shadowfacts.endergizer.block.ModBlocks
import net.shadowfacts.endergizer.item.ModItems
import net.shadowfacts.endergizer.util.CommandEndergizer

/**
 * @author shadowfacts
 */
@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = DEPENDENCIES, modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object Endergizer {

//	Content
	val blocks = ModBlocks
	val items = ModItems

	@Mod.EventHandler
	fun preInit(event: FMLPreInitializationEvent) {
		EConfig.init(event.modConfigurationDirectory)

		blocks.init()
		items.init()

		MinecraftForge.EVENT_BUS.register(EventHandler)
	}

	@Mod.EventHandler
	fun init(event: FMLInitializationEvent) {
		GameRegistry.register(RecipeBattery)
	}

	@Mod.EventHandler
	fun serverStarting(event: FMLServerStartingEvent) {
		event.registerServerCommand(CommandEndergizer)
	}

}
