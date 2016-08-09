package net.shadowfacts.endergizer.block.battery

import net.darkhax.tesla.api.implementation.BaseTeslaContainer
import net.darkhax.tesla.capability.TeslaCapabilities
import net.minecraft.item.EnumDyeColor
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.shadowfacts.endergizer.energy.EnderTeslaContainer
import net.shadowfacts.shadowmc.ShadowMC
import net.shadowfacts.shadowmc.network.PacketRequestTEUpdate
import net.shadowfacts.shadowmc.tileentity.BaseTileEntity
import java.util.*

/**
 * @author shadowfacts
 */
class TileEntityEnderBattery : BaseTileEntity() {

	val DEFAULT: UUID = UUID.fromString("41C82C87-7AfB-4024-BA57-13D2C99CAE77")

	var tesla: BaseTeslaContainer = EnderTeslaContainer(this)

	var color1 = EnumDyeColor.WHITE
	var color2 = EnumDyeColor.WHITE

	var owner: UUID? = null

	override fun writeToNBT(tag: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(tag)

		tag.setInteger("color1", color1.ordinal)
		tag.setInteger("color2", color2.ordinal)
		if (owner != null) tag.setUniqueId("owner", owner)

		return tag
	}

	override fun readFromNBT(tag: NBTTagCompound) {
		super.readFromNBT(tag)

		color1 = EnumDyeColor.byMetadata(tag.getInteger("color1"))
		color2 = EnumDyeColor.byMetadata(tag.getInteger("color2"))

		owner = if (tag.hasUniqueId("owner")) tag.getUniqueId("owner") else DEFAULT

		tesla = EnderTeslaContainer(this)
	}

	override fun onLoad() {
		if (world.isRemote) {
			ShadowMC.network.sendToServer(PacketRequestTEUpdate(this))
		}
	}

	override fun hasCapability(capability: Capability<*>?, facing: EnumFacing?): Boolean {
		if (capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER) {
			val orientation = world.getBlockState(pos).getValue(ORIENTATION)
			return facing == null || facing == orientation || facing == orientation.opposite
		}
		return super.hasCapability(capability, facing)
	}

	override fun <T : Any?> getCapability(capability: Capability<T>?, facing: EnumFacing?): T {
		if (capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER) {
			val orientation = world.getBlockState(pos).getValue(ORIENTATION)
			if (facing == null || facing == orientation || facing == orientation.opposite) {
				return tesla as T
			}
		}
		return super.getCapability(capability, facing)
	}

}