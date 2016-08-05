package net.shadowfacts.endergizer.compat.jei

import mezz.jei.api.recipe.IRecipeHandler
import mezz.jei.api.recipe.IRecipeWrapper
import mezz.jei.api.recipe.VanillaRecipeCategoryUid
import net.shadowfacts.endergizer.RecipeBattery

/**
 * @author shadowfacts
 */
object ERecipeHandler : IRecipeHandler<RecipeBattery> {

	override fun getRecipeCategoryUid(): String = VanillaRecipeCategoryUid.CRAFTING

	override fun getRecipeCategoryUid(recipe: RecipeBattery): String = recipeCategoryUid

	override fun isRecipeValid(recipe: RecipeBattery): Boolean = true

	override fun getRecipeClass(): Class<RecipeBattery> = RecipeBattery::class.java

	override fun getRecipeWrapper(recipe: RecipeBattery): IRecipeWrapper = BatteryRecipeWrapper(recipe)

}