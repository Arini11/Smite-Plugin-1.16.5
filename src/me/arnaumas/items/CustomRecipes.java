package me.arnaumas.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import me.arnaumas.UhcMain;

public class CustomRecipes {
	
	public CustomRecipes() {
		gapple();
	}

	private void gapple() {
		ItemManager.createGoldenHead();
    	ItemStack item = ItemManager.goldenHead;
    	NamespacedKey key = new NamespacedKey(UhcMain.getInstance(), "golden_head");
    	ShapedRecipe recipe = new ShapedRecipe(key, item);
    	recipe.shape("LLL", "LSL", "LLL");
    	recipe.setIngredient('L', Material.GOLD_INGOT);
    	recipe.setIngredient('S', Material.PLAYER_HEAD);
    	Bukkit.addRecipe(recipe);
	}

}
