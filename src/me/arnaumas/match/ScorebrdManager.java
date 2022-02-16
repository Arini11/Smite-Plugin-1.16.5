package me.arnaumas.match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.arnaumas.UhcMain;

public class ScorebrdManager {
	
	private static final UhcMain plugin = UhcMain.getInstance();
	private static final TeamsManager teams = TeamsManager.getInstance();
	static final ScoreboardManager scManager = plugin.getServer().getScoreboardManager();
	static Scoreboard uhcScoreboard = scManager.getNewScoreboard();
	private static Objective infoObjective;
	private static Objective lifeObjective;
	//private static Score timer, totalTime, episodeScore, fullLine;
	//private static List<Score> blankLines = new ArrayList<Score>();

	static String sPrefix,mPrefix = "";
	
	public static void setUp() {
		uhcScoreboard = scManager.getNewScoreboard();
		infoObjective = uhcScoreboard.registerNewObjective("Info", "dummy", ChatColor.GOLD + "UHC");
		lifeObjective = uhcScoreboard.registerNewObjective("Health", "health", "health", RenderType.HEARTS);
		lifeObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		updateHealth();
		refreshSidebar();
		for(Player player: Bukkit.getOnlinePlayers()) {
			player.setScoreboard(uhcScoreboard);
		}
	}
	
	/* Updates the health on the list scoreboard */
	public static void updateHealth() {
		for(Player player: Bukkit.getOnlinePlayers()) {
			Score lifeScore = lifeObjective.getScore(player.getName());
			lifeScore.setScore((int) player.getHealth());
			lifeObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		}
	}

	/* Refreshes the scoreboard sidebar
	* @param minutes - the minutes that will be displayed in the sidebar
	* @param seconds - the seconds that will be displayed in the sidebar
	* @param episode - the episode that will be displayed in the sidebar
	* @param total - the total time that the match has been going on */
	static void refreshSidebar() {
		Bukkit.broadcastMessage("refresh sidebar");
		infoObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		//timer = infoObjective.getScore( ChatColor.GRAY + "  »  " + PluginStrings.SCOREBOARD_TIME_LEFT.toString() + ChatColor.GRAY + minutes + ":" + seconds);
		//totalTime = infoObjective.getScore( ChatColor.GRAY + "  »  " + PluginStrings.SCOREBOARD_TOTAL_TIME.toString() + ChatColor.GRAY + total);
		//episodeScore = infoObjective.getScore( ChatColor.GRAY + "  »  " + PluginStrings.SCOREBOARD_EPISODE.toString() + ChatColor.GRAY + episode);
		//fullLine = infoObjective.getScore(ChatColor.GRAY + "§m                                ");
		//blankLines.addAll(Arrays.asList(infoObjective.getScore(""), infoObjective.getScore(" "), infoObjective.getScore("  "), infoObjective.getScore("   "), infoObjective.getScore("    "), infoObjective.getScore("     ")));
		//blankLines.get(0).setScore(8);
		
		for(Player p: Bukkit.getServer().getOnlinePlayers()) {
			int index = 11;
			Score equip = infoObjective.getScore("Team "+teams.getEquip(p.getName()));
	        equip.setScore(index--);
	        for(String jugador : teams.getCompanys(p.getName())) {
	        	Score j = infoObjective.getScore("   " + jugador);
	        	j.setScore(index--);
	        }     
			p.setScoreboard(uhcScoreboard);
		}
	}

	/* Changes the sidebar title
	* @param newTitle - the new title of the sidebar objective */
	static void refreshSidebarTitle(String newTitle) {
		infoObjective.setDisplayName(newTitle);
	}

	public static void rmPlayer(Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}

}