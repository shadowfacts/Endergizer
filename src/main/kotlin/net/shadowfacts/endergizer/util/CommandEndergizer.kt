package net.shadowfacts.endergizer.util

import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import net.minecraft.command.WrongUsageException
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.TextComponentString
import net.shadowfacts.endergizer.energy.EnergyManager

/**
 * @author shadowfacts
 */
object CommandEndergizer: CommandBase() {

	override fun getName(): String {
		return "endergizer"
	}

	override fun getUsage(sender: ICommandSender?): String {
		return "/endergizer [cmd]"
	}

	override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<out String>) {
		if (args.size == 0) throw WrongUsageException(getUsage(sender))
		when (args[0].toLowerCase()) {
			"reset" -> {
				EnergyManager.reset()
				sender.sendMessage(TextComponentString("Reset energy"))
			}
			else -> throw CommandException("Unknown subcommand ${args[0]}")
		}
	}

}