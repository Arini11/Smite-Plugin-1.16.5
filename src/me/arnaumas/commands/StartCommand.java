package me.arnaumas.commands;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.arnaumas.UhcMain;
import me.arnaumas.match.RandomTeleports;
import me.arnaumas.match.ScorebrdManager;

public class StartCommand {

	private static long compt;
	private static final UhcMain plugin = UhcMain.getInstance();
	
	public static void start(String[] args) throws InterruptedException {
		try {
			compt = Integer.parseInt(args[1]);
		} catch(ArrayIndexOutOfBoundsException e) {
			compt = 10;
		}
		while(compt > 0) {
			for(Player p: Bukkit.getOnlinePlayers()) { 
				//sendActionBarMessage(player, PluginStrings.START_COUNTDOWN.toString()+ numberColor + "" + ChatColor.BOLD + counter);
				compteEnrere(p);
			}
			compt--;
		}
		for(Player p: Bukkit.getOnlinePlayers()) { 
			p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100, 1);
			p.sendTitle(ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + "INICI" , ChatColor.AQUA + "Passi-ho bé", 0, 5*20, 1*20);
		}
		ScorebrdManager.setUp();
	}

	private static void compteEnrere(Player p) throws InterruptedException {
		ChatColor c;
		float pitch;
		switch((int) compt) {
			case 1:
			case 2:
			case 3: {
				c = ChatColor.RED;
				pitch = 2;
				break;
			}
			case 4:
			case 5: {
				c = ChatColor.YELLOW;
				pitch = 1.5F;
				break;
			}
			default: {
				c = ChatColor.GREEN;
				pitch = 1;
				break;
			}
		}
		p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, pitch);
		p.sendTitle(c + "" + ChatColor.BOLD + compt,"", 0, 20, 0);
		TimeUnit.SECONDS.sleep(1);
	}

	public static void preparar(String[] args) {
		RandomTeleports rnd = new RandomTeleports();
		rnd.prova();
//		for(Player p: Bukkit.getOnlinePlayers()) { 
//			p.setGameMode(GameMode.ADVENTURE);
//			p.teleport(new Location(p.getWorld(), 5, 151, 5));
//		}
//		int x,y=151,z;
//		for(x=0;x<10;x++) {
//			Bukkit.getWorld("world").getBlockAt(x, y, 0).setType(Material.PURPLE_STAINED_GLASS);
//			Bukkit.getWorld("world").getBlockAt(x, y+1, 0).setType(Material.PURPLE_STAINED_GLASS);
//		}
//		for(z=0;z<10;z++) {
//			Bukkit.getWorld("world").getBlockAt(0, y, z).setType(Material.PURPLE_STAINED_GLASS);
//			Bukkit.getWorld("world").getBlockAt(0, y+1, z).setType(Material.PURPLE_STAINED_GLASS);
//		}
//		for(x=0;x<10;x++) {
//			Bukkit.getWorld("world").getBlockAt(x, y, 10).setType(Material.PURPLE_STAINED_GLASS);
//			Bukkit.getWorld("world").getBlockAt(x, y+1, 10).setType(Material.PURPLE_STAINED_GLASS);
//		}
//		for(z=0;z<=10;z++) {
//			Bukkit.getWorld("world").getBlockAt(10, y, z).setType(Material.PURPLE_STAINED_GLASS);
//			Bukkit.getWorld("world").getBlockAt(10, y+1, z).setType(Material.PURPLE_STAINED_GLASS);
//		}
//		for(x=0;x<10;x++) {
//			for(z=0;z<10;z++) {
//				Bukkit.getWorld("world").getBlockAt(x, 150, z).setType(Material.PURPLE_STAINED_GLASS);
//			}
//		}
	}
	

}
