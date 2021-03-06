package net.shadowfacts.endergizer

import com.google.gson.JsonObject
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.NonNullList
import net.minecraft.world.World
import net.minecraftforge.common.crafting.IRecipeFactory
import net.minecraftforge.common.crafting.JsonContext
import net.shadowfacts.endergizer.util.setColors
import net.shadowfacts.shadowmc.recipe.RecipeBase

/**
 * @author shadowfacts
 */
class RecipeBattery: RecipeBase() {

	override fun getRemainingItems(inv: InventoryCrafting) = NonNullList.withSize(inv.sizeInventory, ItemStack.EMPTY)

	override fun getRecipeOutput() = ItemStack(Endergizer.blocks.enderBattery)

	override fun canFit(width: Int, height: Int) = width >= 3 && height >= 3

	override fun getCraftingResult(inv: InventoryCrafting): ItemStack {
		val colors = IntArray(16)
		for (x in 0..2) {
			for (y in 0..2) {
				val stack = inv.getStackInRowAndColumn(x, y)
				if (stack != null && stack.item == Item.getItemFromBlock(Blocks.WOOL)) {
					colors[stack.itemDamage] = colors[stack.itemDamage] + 1
				}
			}
		}
		val result = ItemStack(Endergizer.blocks.enderBattery)
		result.setColors(EnumDyeColor.byMetadata(colors.indexOf(colors.max()!!)))
		return result
	}

	override fun matches(inv: InventoryCrafting, world: World?): Boolean {
		for (y in 0..2) {
			val stack = inv.getStackInRowAndColumn(0, y)
			if (stack == null || stack.item != Item.getItemFromBlock(Blocks.WOOL)) {
				return false
			}
		}
		for (y in 0..2) {
			val stack = inv.getStackInRowAndColumn(2, y)
			if (stack == null || stack.item != Item.getItemFromBlock(Blocks.WOOL)) {
				return false
			}
		}

		var stack = inv.getStackInRowAndColumn(1, 0)
		if (stack == null || stack.item != Items.ENDER_PEARL) return false
		stack = inv.getStackInRowAndColumn(1, 1)
		if (stack == null || stack.item != Item.getItemFromBlock(Blocks.REDSTONE_BLOCK)) return false
		stack = inv.getStackInRowAndColumn(1, 2)
		if (stack == null || stack.item != Items.ENDER_PEARL) return false

		return true
	}

	class Factory: IRecipeFactory {

		override fun parse(context: JsonContext, json: JsonObject) = RecipeBattery()

	}

}