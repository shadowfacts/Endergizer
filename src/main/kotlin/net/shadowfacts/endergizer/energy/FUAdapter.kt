package net.shadowfacts.endergizer.energy

import net.darkhax.tesla.api.implementation.BaseTeslaContainer
import net.minecraftforge.energy.IEnergyStorage

/**
 * @author shadowfacts
 */
class FUAdapter(val tesla: BaseTeslaContainer) : IEnergyStorage {

	override fun canExtract(): Boolean {
		return tesla.outputRate > 0
	}

	override fun getMaxEnergyStored(): Int {
		return tesla.capacity.toInt()
	}

	override fun getEnergyStored(): Int {
		return tesla.storedPower.toInt()
	}

	override fun extractEnergy(maxExtract: Int, simulate: Boolean): Int {
		return tesla.takePower(maxExtract.toLong(), simulate).toInt()
	}

	override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
		return tesla.givePower(maxReceive.toLong(), simulate).toInt()
	}

	override fun canReceive(): Boolean {
		return tesla.inputRate > 0
	}

}