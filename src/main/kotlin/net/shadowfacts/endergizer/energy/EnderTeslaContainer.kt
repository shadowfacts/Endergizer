package net.shadowfacts.endergizer.energy

import net.darkhax.tesla.api.implementation.BaseTeslaContainer
import net.minecraft.nbt.NBTTagCompound
import net.shadowfacts.endergizer.EConfig
import net.shadowfacts.endergizer.block.battery.TileEntityEnderBattery

/**
 * @author shadowfacts
 */
class EnderTeslaContainer(val te: TileEntityEnderBattery) : BaseTeslaContainer(EConfig.capacity, EConfig.transferRate, EConfig.transferRate) {

	override fun getStoredPower(): Long {
		return EnergyManager.getEnergy(te.owner!!, te.color1, te.color2)
	}

	override fun givePower(Tesla: Long, simulated: Boolean): Long {
		val accepted = Math.min(capacity - storedPower, Math.min(inputRate, Tesla))

		if (!simulated) {
			EnergyManager.setEnergy(te.owner!!, te.color1, te.color2, storedPower + accepted)
		}

		return accepted
	}

	override fun takePower(Tesla: Long, simulated: Boolean): Long {
		val removed = Math.min(storedPower, Math.min(outputRate, Tesla))

		if (!simulated) {
			EnergyManager.setEnergy(te.owner!!, te.color1, te.color2, storedPower - removed)
		}

		return removed
	}

	override fun deserializeNBT(nbt: NBTTagCompound?) {

	}

	override fun serializeNBT(): NBTTagCompound {
		return NBTTagCompound()
	}

}