package me.arnaumas;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.arnaumas.items.CustomRecipes;
import me.arnaumas.match.ScorebrdManager;
import me.arnaumas.commands.CommandsCentre;
import me.arnaumas.events.EventsListerner;

public class UhcMain extends JavaPlugin{

	private static UhcMain instance;

	@SuppressWarnings("unused")
	@Override
	public void onEnable() {
		instance = this;
		getServer().getConsoleSender().sendMessage("Servidor Iniciat Correctament");
		this.getServer().getPluginManager().registerEvents(new EventsListerner(), this);
		this.getCommand("skull").setExecutor(new CommandsCentre());
		this.getCommand("teams").setExecutor(new CommandsCentre());
        // Cal fer els equips --> UhcTeamsManager.createTeamsFile();
        CustomRecipes recipes = new CustomRecipes();
        ScorebrdManager.setUp();
        
	}
	
	// Retorna una instància de la classe Main, que necessiten alguns mètodes per ésser cridats.
	public static UhcMain getInstance() {
		return instance;
	}
	
	public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
