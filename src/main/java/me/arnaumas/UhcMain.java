package me.arnaumas;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.arnaumas.items.CustomRecipes;
import me.arnaumas.commands.CommandsFacade;
import me.arnaumas.events.EventsFacade;

public class UhcMain extends JavaPlugin{

	private static UhcMain instance;
	
	public static UhcMain getInstance() {
		return instance;
	}

	@SuppressWarnings("unused")
	@Override
	public void onEnable() {
		String classpath = System.getProperty("java.class.path");
		   String[] classPathValues = classpath.split(File.pathSeparator);
		   System.out.println("CP\n"+Arrays.toString(classPathValues));
		instance = this;
		getServer().getConsoleSender().sendMessage("Servidor Iniciat Correctament");
		this.getServer().getPluginManager().registerEvents(new EventsFacade(), this);
		this.getCommand("uhc").setExecutor(new CommandsFacade());
        CustomRecipes recipes = new CustomRecipes();
        //ScorebrdManager.setUp();
        runnable();
        //Load the config
        SLoadConfig();
	}
	
	private void SLoadConfig(){
        //  Load the config
        try {
            saveDefaultConfig();
            Bukkit.getConsoleSender().sendMessage("[§cSkin Changer§r] §aCONFIG LOAD SUCCESFULLY");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
	

	public void runnable() {
		 new BukkitRunnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				for (LivingEntity e : getServer().getWorld("world").getLivingEntities()) {
					if(e.getType().equals( EntityType.ZOMBIE )) {
						e.setCustomName(e.getType() + "§4[" + ChatColor.RED + e.getHealth()
							+ "§8/§c" + e.getMaxHealth() + "§4]");
						e.setCustomNameVisible(true);
					}

				}

			}

		}.runTaskTimerAsynchronously(this, 0, 5);
		
	}
	
	public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
