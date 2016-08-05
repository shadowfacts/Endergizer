package net.shadowfacts.endergizer.energy

import net.minecraft.item.EnumDyeColor
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraftforge.common.util.Constants
import net.shadowfacts.endergizer.util.forEach
import java.util.*

/**
 * @author shadowfacts
 */
object EnergyManager {

	private var map: MutableMap<UUID, MutableMap<Frequency, Long>> = mutableMapOf()

	fun getEnergy(owner: UUID, color1: EnumDyeColor, color2: EnumDyeColor): Long {
		if (map.containsKey(owner)) {
			val submap = map[owner]!!
			val freq = Frequency(color1, color2)
			if (submap.containsKey(freq)) {
				return submap[freq]!!
			}
		}
		return 0
	}

	fun setEnergy(owner: UUID, color1: EnumDyeColor, color2: EnumDyeColor, energy: Long) {
		val freq = Frequency(color1, color2)
		if (!map.containsKey(owner)) {
			map[owner] = mutableMapOf(freq to energy)
		} else {
			map[owner]!![freq] = energy
		}
	}

	fun reset() {
		map = mutableMapOf()
	}

	fun writeToNBT(tag: NBTTagCompound) {
		val list = NBTTagList()
		map.forEach {
			val subtag = NBTTagCompound()
			subtag.setUniqueId("owner", it.key)

			val sublist = NBTTagList()

			it.value.forEach {
				val subsubtag = NBTTagCompound()

				subsubtag.setInteger("color1", it.key.color1.metadata)
				subsubtag.setInteger("color2", it.key.color2.metadata)
				subsubtag.setLong("energy", it.value)

				sublist.appendTag(subsubtag)
			}

			subtag.setTag("map", sublist)

			list.appendTag(subtag)
		}
		tag.setTag("map", list)
	}

	fun readFromNBT(tag: NBTTagCompound) {
		val list = tag.getTagList("map", Constants.NBT.TAG_COMPOUND)
		list.forEach {
			if (it is NBTTagCompound) {
				val owner = it.getUniqueId("owner")!!
				val sublist = it.getTagList("map", Constants.NBT.TAG_COMPOUND)
				val submap: MutableMap<Frequency, Long> = mutableMapOf()
				sublist.forEach {
					if (it is NBTTagCompound) {
						val color1 = EnumDyeColor.byMetadata(it.getInteger("color1"))
						val color2 = EnumDyeColor.byMetadata(it.getInteger("color2"))
						val energy = it.getLong("energy")
						submap[Frequency(color1, color2)] = energy
					}
				}
				map[owner] = submap
			}
		}
	}

}

private data class Frequency(val color1: EnumDyeColor, val color2: EnumDyeColor)