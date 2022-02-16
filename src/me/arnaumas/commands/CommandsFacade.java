package me.arnaumas.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import me.arnaumas.UhcMain;


public class CommandsFacade implements TabExecutor {
	
		public static UhcMain plugin = UhcMain.getInstance();
		
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			try {
				switch(args[0].toLowerCase()) {
					case "start": {
						StartCommand.start(args);
						break;
					}
					case "preparar": {
						StartCommand.preparar(args);
						break;
					}
					case "teams": {
						TeamsCommand.run(sender, args);
						break;
					}
					case "skull": {
						SkullCommand.run(sender, args);
						break;
					}
					case "raycast": {
						//RaycastCommand.run(sender);
						break;
					}
					case "tp": {
						//TpCommand.run(sender);
						break;
					}
					case "prova":{
						RaycastCommand.proves(sender);
						break;
					}
					default: {
						if(sender instanceof Player) {
							((Player) sender).getPlayer().sendMessage(ChatColor.RED + "No s'ha trobat el comando");
							((Player) sender).getPlayer().sendMessage("args[0] --> "+args[0]);
							((Player) sender).getPlayer().sendMessage("args[1] --> "+args[1]);
							
						} else {
							plugin.getLogger().warning(ChatColor.RED + "No s'ha trobat el comando");
						}
						return false;
					}
				}
			} catch(Exception e) {
				plugin.getLogger().severe(e.getCause().toString());
				return false;
			}
			return true;
		}

		@Override
		public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
			List<String> llista = new ArrayList<String>();
			//System.out.print("len -> "+args.length);
			if(args.length == 1) {
				//System.out.println("okkkk");
				return Arrays.asList("teams","start","skull","preparar");
			}
				if(args[0].equals("skull")) {
					for(Player p : Bukkit.getOnlinePlayers())
						llista.add(p.getName());
				} else if(args[0].equals("teams")) {
					llista.add("create");
					llista.add("delete");
					llista.add("list");
					llista.add("join");
					llista.add("leave");
				} else if(args[0].equals("start")) {
					llista.add("asd");
				} 
			return llista;
		}

}
