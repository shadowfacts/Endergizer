package net.shadowfacts.endergizer

import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.world.World
import net.shadowfacts.endergizer.util.setColors

/**
 * @author shadowfacts
 */
object RecipeBattery : IRecipe {

	override fun getRemainingItems(inv: InventoryCrafting): Array<ItemStack?> {
		return kotlin.arrayOfNulls(inv.sizeInventory)
	}

	override fun getRecipeOutput(): ItemStack = ItemStack(Endergizer.blocks.enderBattery)

	override fun getRecipeSize(): Int = 9

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

}