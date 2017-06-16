package net.shadowfacts.endergizer.block

import net.shadowfacts.endergizer.block.battery.BlockEnderBattery
import net.shadowfacts.shadowmc.block.ModBlocks

/**
 * @author shadowfacts
 */
object ModBlocks: ModBlocks() {

	var enderBattery: BlockEnderBattery? = null

	override fun init() {
		enderBattery = register(BlockEnderBattery().init())
	}

}