package net.shadowfacts.endergizer.item

import net.darkhax.tesla.api.ITeslaHolder
import net.darkhax.tesla.capability.TeslaCapabilities
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.World
import net.shadowfacts.shadowmc.ShadowMC
import net.shadowfacts.shadowmc.item.ItemBase

/**
 * @author shadowfacts
 */
class ItemVoltageMeter : ItemBase("voltageMeter") {

	val MSG_ID = 78454

	override fun onItemUseFirst(stack: ItemStack, player: EntityPlayer, world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, hand: EnumHand?): EnumActionResult {
		val te = world.getTileEntity(pos)
		if (te != null) {
			var tesla: ITeslaHolder? = null
			if (te.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER, facing)) {
				tesla = te.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, facing)
			} else if (te.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER, null)) {
				tesla = te.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, null)
			}
			if (tesla != null) {
				val msg = TextComponentString(I18n.format("item.voltageMeter.msg", tesla.storedPower, tesla.capacity))
				ShadowMC.proxy.sendSpamlessMessage(player, msg, MSG_ID)
				return EnumActionResult.SUCCESS
			}
		}
		return EnumActionResult.PASS
	}

}