package me.arnaumas.match;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import me.arnaumas.UhcMain;
import me.arnaumas.commands.TeamsCommand;

public class TeamsManager {
	
	private static TeamsManager instance = null;
	private UhcMain uhc = UhcMain.getInstance();
	private File fitxer;
	private YamlConfiguration teamsConfig;
	private final static String TMS = TeamsCommand.getTms();
	
	public TeamsManager() {
		
		if(!uhc.getDataFolder().exists()) {
			uhc.getDataFolder().mkdirs();
		}
		fitxer = new File(uhc.getDataFolder()+"/teams.yml");
		teamsConfig = YamlConfiguration.loadConfiguration(fitxer);
		if(!fitxer.exists()) {
			try {
				fitxer.createNewFile();
				uhc.getLogger().info(TMS+"Fitxer de configuració generat correctament.");
				loadDefaults();
				saveConfig();
			} catch(IOException e) {
				e.printStackTrace();
				uhc.getLogger().severe(TMS+"No s'ha pogut guardar el fitxer de configuració.");
			}
		}
	}
	
	private void loadDefaults() {
		teamsConfig.createSection("teams");
		uhc.getLogger().info(TMS+"Establerts els valors predeterminats.");
	}
	
	public void saveConfig() {
		try {
			teamsConfig.save(fitxer);
			uhc.getLogger().info(TMS+"teams.yml guardat correctament.");
		} catch (IOException e) {
			uhc.getLogger().severe(TMS+"No s'ha pogut trobar el fitxer.");
			e.printStackTrace();
			return;
		}
	}
	
	public static TeamsManager getInstance() {
        if (instance == null)
        	instance = new TeamsManager();
        return instance;
    }

	public boolean crearEquip(String equip) {
		if(!teamsConfig.isSet("teams." + equip)) {
			teamsConfig.createSection("teams." + equip).set("jugadors", null);
			teamsConfig.getConfigurationSection("teams." + equip).set("mida", 2);
			teamsConfig.getConfigurationSection("teams." + equip).set("color", null);
			Bukkit.broadcastMessage(TMS+"Equip §5"+equip+"§r creat!");
			return true;
		} else {
			return false;
		}
		
	}
	
	public void setColorEquip(String equip, String color) {
		if(teamsConfig.isSet("teams." + equip)) {
			teamsConfig.getConfigurationSection("teams." + equip).set("color", color);
			Bukkit.broadcastMessage(TMS+"Color de l'equip §5"+equip+"§r actualitzat!");
		}
	}
	
	public boolean eliminarEquip(String equip) {
		if(teamsConfig.getConfigurationSection("teams." + equip) != null) {
			teamsConfig.set("teams." + equip, null);
			Bukkit.broadcastMessage(TMS+"Equip §5"+equip+"§r eliminat!");
			return true;
		} else return false;
	}
	
	public String afegirJugador(String jugador, String equip) {
		String error = "";
		if(!teamsConfig.isSet("teams." + equip)) {
			error = "L'equip no existeix";
		} else if(teamsConfig.getConfigurationSection("teams." + equip).getList("jugadors") == null || !teamsConfig.getConfigurationSection("teams." + equip).getList("jugadors").contains(jugador)) {
			if(teamsConfig.getConfigurationSection("teams." + equip).getList("jugadors") == null) {
				List<String> ls = new ArrayList<String>(Arrays.asList());
				System.out.println("ok");
				ls.add(jugador);
				teamsConfig.getConfigurationSection("teams." + equip).set("jugadors", ls);
				Bukkit.broadcastMessage(TMS+"Jugador §5"+jugador+"§r afegit a l'equip §5"+equip);
				return error;
			} else {
				uhc.getLogger().info("La llista de jugadors membres de l'equip existeix");
				List<String> ls = teamsConfig.getConfigurationSection("teams." + equip).getStringList("jugadors");
				if(ls.size() == teamsConfig.getConfigurationSection("teams." + equip).getInt("mida")) {
					error = "L'equip és ple";
				} else {
					ls.add(jugador);
					teamsConfig.getConfigurationSection("teams." + equip).set("jugadors", ls);
					Bukkit.broadcastMessage(TMS+"Jugador §5"+jugador+"§r afegit a l'equip §5"+equip);
				}
			}
		} else {
			error = "El jugador ja està registrat";
		}
		return error;
	}

	public String getEquips() {
		String equips = "";
		if(teamsConfig.getConfigurationSection("teams").getKeys(false).isEmpty()) {
			equips = "§5Encara no hi ha equips!§r  ";
		} else {
			for(String equip : teamsConfig.getConfigurationSection("teams").getKeys(false)) {
				equips += "§5"+equip+"§r";
				for(String jugador : teamsConfig.getConfigurationSection("teams." + equip).getStringList("jugadors")){
					equips += "\n   -"+jugador+"\n";
				}
			}	
		}
		return equips;
	}
	
	public List<String> getEquipsLlista() {
		List<String> llista = new ArrayList<String>();
		if(!teamsConfig.getConfigurationSection("teams").getKeys(false).isEmpty()) {
			for(String equip : teamsConfig.getConfigurationSection("teams").getKeys(false)) {
				llista.add(equip);
			}
		}
		return llista;
	}

	public String treureJugador(String jugador, String equip) {
		String error = "ok";
		if(!teamsConfig.isSet("teams." + equip)) {
			error = "L'equip no existeix";
		} else {
			ConfigurationSection section = teamsConfig.getConfigurationSection("teams." + equip);
			if(section.getStringList("jugadors") == null || !section.getStringList("jugadors").contains(jugador)) {
				error = "El jugador "+jugador+" no està dins de l'equip";
			}
			List<String> ls = teamsConfig.getConfigurationSection("teams." + equip).getStringList("jugadors");
			ls.remove(jugador);
			teamsConfig.getConfigurationSection("teams." + equip).set("jugadors", ls);
			Bukkit.getLogger().severe(ls.toString());
			Bukkit.broadcastMessage(TMS+"Jugador §5"+jugador+"§r tret de l'equip §5"+equip);				
		}
		return error;	
	}	
	
	public List<String> getCompanys(String jugador) {
		List<String> llista;
		for(String equip : teamsConfig.getConfigurationSection("teams").getKeys(false)) {
			if(teamsConfig.getConfigurationSection("teams." + equip).getStringList("jugadors").contains(jugador)) {
				llista = teamsConfig.getConfigurationSection("teams." + equip).getStringList("jugadors");
				llista.remove(jugador);
				return llista;
			}
		}
		return new ArrayList<String>();
	}
	
	public List<String> getCompanysFull(String equip) {
		for(String e : teamsConfig.getConfigurationSection("teams").getKeys(false)) {
			if(e.equalsIgnoreCase(equip)) {
				return teamsConfig.getConfigurationSection("teams." + e).getStringList("jugadors");
			}
		}
		return new ArrayList<String>();
	}
	
	public String getEquip(String jugador) {
		for(String equip : teamsConfig.getConfigurationSection("teams").getKeys(false)) {
			if(teamsConfig.getConfigurationSection("teams." + equip).getStringList("jugadors").contains(jugador)) {
				return equip;
			}
		}
		return "";
	}
	
}
