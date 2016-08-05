package net.shadowfacts.endergizer

import net.minecraftforge.common.config.Configuration
import java.io.File

/**
 * @author shadowfacts
 */
object EConfig {

	var file: File? = null

	var capacity: Long = 2048
	var transferRate: Long = 256

	fun init(configDir: File) {
		file = File(configDir, "shadowfacts/Endergizer.cfg")
		load()
	}

	fun load() {
		val config = Configuration(file)

		var prop = config.get("general", "capacity", capacity.toInt())
		prop.comment = "The capacity of the Ender Battery"
		capacity = prop.getLong(capacity)

		prop = config.get("general", "transferRate", transferRate.toInt())
		prop.comment = "The transfer rate of the Ender Battery"
		transferRate = prop.getLong(transferRate)

		if (config.hasChanged()) config.save()
	}

}