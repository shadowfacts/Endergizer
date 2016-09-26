package net.shadowfacts.endergizer.compat.jei

import mezz.jei.api.*
import mezz.jei.api.JEIPlugin

/**
 * @author shadowfacts
 */
@JEIPlugin
class JEIPlugin : BlankModPlugin() {

	override fun register(registry: IModRegistry) {
		registry.addRecipeHandlers(ERecipeHandler)
	}

}