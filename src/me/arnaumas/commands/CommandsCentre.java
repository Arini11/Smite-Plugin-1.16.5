package me.arnaumas.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.arnaumas.UhcMain;


public class CommandsCentre implements CommandExecutor {
	
		public static UhcMain plugin = UhcMain.getInstance();
		
		@Override
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if(label.equalsIgnoreCase("teams")) {
				TeamsCommand.run(sender, args);
			}
			else if(label.equalsIgnoreCase("skull")) {
				SkullCommand.run(sender, args);
			}
			return true;	
		}
		
		@SuppressWarnings("unused")
		private String color(String msg) {
	        return ChatColor.translateAlternateColorCodes('&', msg);
	    }

}
