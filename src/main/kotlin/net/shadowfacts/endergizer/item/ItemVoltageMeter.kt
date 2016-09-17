package net.shadowfacts.endergizer.item

import net.darkhax.tesla.api.ITeslaHolder
import net.darkhax.tesla.capability.TeslaCapabilities
import net.minecraft.client.resources.I18n
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.World
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.energy.IEnergyStorage
import net.shadowfacts.shadowmc.ShadowMC
import net.shadowfacts.shadowmc.item.ItemBase

/**
 * @author shadowfacts
 */
class ItemVoltageMeter : ItemBase("voltageMeter") {

	val MSG_ID = 78454

	fun init(): ItemVoltageMeter {
		creativeTab = CreativeTabs.MISC
		return this
	}

	override fun onItemUseFirst(stack: ItemStack, player: EntityPlayer, world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, hand: EnumHand?): EnumActionResult {
		val te = world.getTileEntity(pos)
		if (te != null) {
			val tesla: ITeslaHolder? = if (te.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER, facing)) te.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, facing) else if (te.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER, null)) te.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, null) else null
			if (tesla != null) {
				val msg = TextComponentString(I18n.format("item.voltageMeter.msg", tesla.storedPower, tesla.capacity, "Tesla"))
				ShadowMC.proxy.sendSpamlessMessage(player, msg, MSG_ID)
				return EnumActionResult.SUCCESS
			}

			val fu: IEnergyStorage? = if (te.hasCapability(CapabilityEnergy.ENERGY, facing)) te.getCapability(CapabilityEnergy.ENERGY, facing) else if (te.hasCapability(CapabilityEnergy.ENERGY, null)) te.getCapability(CapabilityEnergy.ENERGY, null) else null
			if (fu != null) {
				val msg = TextComponentString(I18n.format("item.voltageMeter.msg", fu.energyStored, fu.maxEnergyStored, ""))
				ShadowMC.proxy.sendSpamlessMessage(player, msg, MSG_ID)
				return EnumActionResult.SUCCESS
			}
		}
		return EnumActionResult.PASS
	}

}