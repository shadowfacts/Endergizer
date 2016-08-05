package net.shadowfacts.endergizer.compat.jei

import mezz.jei.api.IJeiRuntime
import mezz.jei.api.IModPlugin
import mezz.jei.api.IModRegistry
import mezz.jei.api.JEIPlugin

/**
 * @author shadowfacts
 */
@JEIPlugin
class JEIPlugin : IModPlugin {

	override fun register(registry: IModRegistry) {
		registry.addRecipeHandlers(ERecipeHandler)
	}

	override fun onRuntimeAvailable(jeiRuntime: IJeiRuntime) {

	}

}