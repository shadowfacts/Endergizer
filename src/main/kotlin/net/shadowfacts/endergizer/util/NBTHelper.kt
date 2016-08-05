package net.shadowfacts.endergizer.util

import net.minecraft.item.EnumDyeColor
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraftforge.common.util.Constants
import net.shadowfacts.endergizer.Endergizer

/**
 * @author shadowfacts
 */
fun ItemStack.getColor1(): EnumDyeColor? {
	if (item == Item.getItemFromBlock(Endergizer.blocks.enderBattery) &&
		hasTagCompound() &&
		tagCompound!!.hasKey("color1", Constants.NBT.TAG_INT)) {
		return EnumDyeColor.byMetadata(tagCompound!!.getInteger("color1"))
	}
	return null
}

fun ItemStack.getColor2(): EnumDyeColor? {
	if (item == Item.getItemFromBlock(Endergizer.blocks.enderBattery) &&
			hasTagCompound() &&
			tagCompound!!.hasKey("color2", Constants.NBT.TAG_INT)) {
		return EnumDyeColor.byMetadata(tagCompound!!.getInteger("color2"))
	}
	return null
}

fun ItemStack.setColor1(color: EnumDyeColor) {
	if (item == Item.getItemFromBlock(Endergizer.blocks.enderBattery)) {
		if (!hasTagCompound()) tagCompound = NBTTagCompound()

		tagCompound!!.setInteger("color1", color.metadata)
	}
}

fun ItemStack.setColor2(color: EnumDyeColor) {
	if (item == Item.getItemFromBlock(Endergizer.blocks.enderBattery)) {
		if (!hasTagCompound()) tagCompound = NBTTagCompound()

		tagCompound!!.setInteger("color2", color.metadata)
	}
}

fun ItemStack.setColors(color: EnumDyeColor) {
	if (item == Item.getItemFromBlock(Endergizer.blocks.enderBattery)) {
		if (!hasTagCompound()) tagCompound = NBTTagCompound()

		tagCompound!!.setInteger("color1", color.metadata)
		tagCompound!!.setInteger("color2", color.metadata)
	}
}

fun NBTTagList.forEach(action: (NBTBase) -> Unit) {
	for (i in 0.until(tagCount())) {
		action(get(i))
	}
}