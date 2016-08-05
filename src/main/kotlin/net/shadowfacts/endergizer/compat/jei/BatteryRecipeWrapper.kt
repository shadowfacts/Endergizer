package net.shadowfacts.endergizer.compat.jei

import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper
import mezz.jei.plugins.vanilla.VanillaRecipeWrapper
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
class BatteryRecipeWrapper(val recipe: RecipeBattery) : VanillaRecipeWrapper(), ICraftingRecipeWrapper {

	override fun getOutputs(): MutableList<ItemStack> {
		val stack = ItemStack(Endergizer.blocks.enderBattery)
		stack.setColors(EnumDyeColor.WHITE)
		return mutableListOf(stack)
	}

	override fun getInputs(): MutableList<Any?> {
		val wool = ItemStack(Blocks.WOOL)
		val pearl = ItemStack(Items.ENDER_PEARL)
		val redstone = ItemStack(Blocks.REDSTONE_BLOCK)
		return mutableListOf(wool, pearl, wool, wool, redstone, wool, wool, pearl, wool)
	}

}