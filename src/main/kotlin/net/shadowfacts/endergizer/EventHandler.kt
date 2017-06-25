package net.shadowfacts.endergizer

import net.minecraft.nbt.CompressedStreamTools
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.DimensionManager
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.shadowfacts.endergizer.energy.EnergyManager
import java.io.File

/**
 * @author shadowfacts
 */
@Mod.EventBusSubscriber(modid = MODID)
object EventHandler {

	@SubscribeEvent
	fun onWorldSave(event: WorldEvent.Save) {
		val tag = NBTTagCompound()
		EnergyManager.writeToNBT(tag)
		val file = File(DimensionManager.getCurrentSaveRootDirectory(), "$NAME.nbt")
		CompressedStreamTools.write(tag, file)
	}

	@SubscribeEvent
	fun onWorldLoad(event: WorldEvent.Load) {
		val file = File(DimensionManager.getCurrentSaveRootDirectory(), "$NAME.nbt")
		if (file.exists()) {
			val tag = CompressedStreamTools.read(file)!!
			EnergyManager.readFromNBT(tag)
		}
	}

}