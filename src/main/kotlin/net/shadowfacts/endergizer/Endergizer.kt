package net.shadowfacts.endergizer

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
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
	}

	@Mod.EventHandler
	fun serverStarting(event: FMLServerStartingEvent) {
		event.registerServerCommand(CommandEndergizer)
	}

	@Mod.EventBusSubscriber
	object RegistrationHandler {

		@JvmStatic
		@SubscribeEvent
		fun registerBlocks(event: RegistryEvent.Register<Block>) {
			event.registry.register(blocks.enderBattery)
			GameRegistry.registerTileEntity(blocks.enderBattery.tileEntityClass, blocks.enderBattery.name)
		}

		@JvmStatic
		@SubscribeEvent
		fun registerItems(event: RegistryEvent.Register<Item>) {
			event.registry.registerAll(items.voltageMeter, blocks.enderBattery.createItemBlock())
		}

		@JvmStatic
		@SubscribeEvent
		fun registerModels(event: ModelRegistryEvent) {
			blocks.enderBattery.initItemModel()
			items.voltageMeter.initItemModel()
		}

	}

}
