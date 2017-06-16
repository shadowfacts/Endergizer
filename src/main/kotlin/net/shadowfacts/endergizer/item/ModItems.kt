package net.shadowfacts.endergizer.item

import net.shadowfacts.shadowmc.item.ModItems

/**
 * @author shadowfacts
 */
object ModItems: ModItems() {

	var voltageMeter: ItemVoltageMeter? = null

	override fun init() {
		voltageMeter = register(ItemVoltageMeter().init())
	}

}