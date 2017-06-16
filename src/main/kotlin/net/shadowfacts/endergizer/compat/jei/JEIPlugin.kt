package net.shadowfacts.endergizer.compat.jei

import mezz.jei.api.*
import mezz.jei.api.JEIPlugin
import mezz.jei.api.recipe.IRecipeWrapperFactory
import mezz.jei.api.recipe.VanillaRecipeCategoryUid
import net.shadowfacts.endergizer.RecipeBattery

/**
 * @author shadowfacts
 */
@JEIPlugin
class JEIPlugin: IModPlugin {

	override fun register(registry: IModRegistry) {
		registry.handleRecipes(RecipeBattery::class.java, IRecipeWrapperFactory(::BatteryRecipeWrapper), VanillaRecipeCategoryUid.CRAFTING)
	}

}