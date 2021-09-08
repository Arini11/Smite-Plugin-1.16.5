package me.arnaumas.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.arnaumas.UhcMain;
import me.arnaumas.match.TeamsManager;

public class TeamsCommand {
	
	private static TeamsManager teams = TeamsManager.getInstance();
	private final static String TMS = "§3[TEAMS]§r ";
	
	public static void run(CommandSender sender, String[] args) {
		
		// Detectar si el comando ha estat executat des de la consola per tal d'enviar els
		// missatges que, de normal, s'enviarien al jugador, a la consola.
		if(sender instanceof Player) {

			Player p = (Player) sender;
			
			if(args.length == 0) {
				p.sendMessage(UhcMain.color(
					  "&LOpcions:&R create, list, join, leave\n"
					+ "     /teams create &Nnom_equip&R\n"
					+ "     /teams delete &Nnom_equip&R\n"
					+ "     /teams join &Nnom_jugador&R &Nnom_equip&R\n"
					+ "     /teams leave &Nnom_jugador&R &Nnom_equip&R\n"
					+ "     /teams list"));
			} else {
				switch(args[0].toLowerCase()) {
					case "create": {
						teams.crearEquip(args[1]);
						teams.saveConfig();
						break;
					}
					case "delete": {
						if(!teams.eliminarEquip(args[1])) {
							sender.sendMessage("§4No existeix cap equip amb aquest nom");
						}
						teams.saveConfig();
						break;
					}
					case "list": {
						sender.sendMessage(UhcMain.color(teams.getEquips()));
						break;
					}
					case "join": {
						String msgError = teams.afegirJugador(args[1], args[2]);
						if(!msgError.equals("")) 
							sender.sendMessage("§4"+msgError);
						teams.saveConfig();
						break;
					}
					case "leave": {
						Bukkit.getLogger().severe("leaveeee");
						String msgError = teams.treureJugador(args[1], args[2]);
						if(!msgError.equals("")) 
							Bukkit.getLogger().severe("§4"+msgError);
						teams.saveConfig();
						break;
					}
					default: {
						sender.sendMessage("§4No es reconeix el comando");
					}
				}
			}
		// SI S'EXECUTA DES DE CONSOLA
		} else {
			if(args.length == 0) {
				Bukkit.getLogger().info(UhcMain.color(
					  "&LOpcions:&R create, list, join, leave\n"
					+ "     /teams create &Nnom_equip&R\n"
					+ "     /teams delete &Nnom_equip&R\n"
					+ "     /teams join &Nnom_jugador&R &Nnom_equip&R\n"
					+ "     /teams leave &Nnom_jugador&R &Nnom_equip&R\n"
					+ "     /teams list"));
			} else {
				switch(args[0].toLowerCase()) {
					case "create": {
						teams.crearEquip(args[1]);
						teams.saveConfig();
						break;
					}
					case "delete": {
						if(!teams.eliminarEquip(args[1])) {
							Bukkit.getLogger().info("§4No existeix cap equip amb aquest nom");
						}
						teams.saveConfig();
						break;
					}
					case "list": {
						Bukkit.getLogger().info(UhcMain.color(teams.getEquips()));
						break;
					}
					case "join": {
						String msgError = teams.afegirJugador(args[1], args[2]);
						if(!msgError.equals("")) 
							Bukkit.getLogger().severe("§4"+msgError);
						teams.saveConfig();
						break;
					}
					case "leave": {
						Bukkit.getLogger().severe("leaveeee");
						String msgError = teams.treureJugador(args[1], args[2]);
						if(!msgError.equals("")) 
							Bukkit.getLogger().severe("§4"+msgError);
						teams.saveConfig();
						break;
					}
					default: {
						Bukkit.getLogger().severe("No es reconeix el comando");
					}
				}
			}
		}
	}

	public static String getTms() {
		return TMS;
	}

}
