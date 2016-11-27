package net.shadowfacts.endergizer.compat.jei

import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.BlankRecipeWrapper
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.ItemStack
import net.shadowfacts.endergizer.Endergizer
import net.shadowfacts.endergizer.RecipeBattery
import net.shadowfacts.endergizer.util.setColors

/**
 * @author shadowfacts
 */
class BatteryRecipeWrapper(val recipe: RecipeBattery) : BlankRecipeWrapper(), ICraftingRecipeWrapper {

	override fun getIngredients(ingredients: IIngredients) {
		// inputs
		val wool = ItemStack(Blocks.WOOL)
		val pearl = ItemStack(Items.ENDER_PEARL)
		val redstone = ItemStack(Blocks.REDSTONE_BLOCK)
		ingredients.setInputs(ItemStack::class.java, mutableListOf(wool, pearl, wool, wool, redstone, wool, wool, pearl, wool))

		// outputs
		val stack = ItemStack(Endergizer.blocks.enderBattery)
		stack.setColors(EnumDyeColor.WHITE)
		ingredients.setOutput(ItemStack::class.java, stack)
	}

}