package me.arnaumas.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.arnaumas.UhcMain;
import me.arnaumas.match.TeamsManager;

public class TeamsCommand {
	
	private static TeamsManager teams = TeamsManager.getInstance();
	private final static String TMS = "�3[TEAMS]�r ";
	
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
				switch(args[1].toLowerCase()) {
					case "create": {
						teams.crearEquip(args[2]);
						teams.saveConfig();
						break;
					}
					case "delete": {
						if(!teams.eliminarEquip(args[2])) {
							sender.sendMessage("�4No existeix cap equip amb aquest nom");
						}
						teams.saveConfig();
						break;
					}
					case "list": {
						sender.sendMessage(UhcMain.color(teams.getEquips()));
						break;
					}
					case "join": {
						String msgError = teams.afegirJugador(args[2], args[3]);
						if(!msgError.equals("")) 
							sender.sendMessage("�4"+msgError);
						teams.saveConfig();
						break;
					}
					case "leave": {
						Bukkit.getLogger().severe("leaveeee");
						String msgError = teams.treureJugador(args[2], args[3]);
						if(!msgError.equals("")) 
							Bukkit.getLogger().severe("�4"+msgError);
						teams.saveConfig();
						break;
					}
					default: {
						sender.sendMessage("�4No es reconeix el comando");
					}
				}
			}
		// SI S'EXECUTA DES DE CONSOLA
		} else {
			Bukkit.getLogger().severe("�4No es pot executar des de consola");
		}
	}

	public static String getTms() {
		return TMS;
	}

}
