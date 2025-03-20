package rip.shuka.testi.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class InventoryItemStacksUtil {
	public static ItemStack createNamelessItemStack(Material material) {
		ItemStack glassPane = new ItemStack(material);
		ItemMeta meta = glassPane.getItemMeta();

		if (meta != null) {
			meta.displayName(Component.text(" "));
			glassPane.setItemMeta(meta);
		}

		return glassPane;
	}

	public static ItemStack createPlayerHead(String playerName, String displayName) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();

		if (meta != null) {
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
			meta.setOwningPlayer(offlinePlayer);
			meta.displayName(Component.text(displayName)
					.color(NamedTextColor.WHITE)
					.decoration(TextDecoration.ITALIC, false));
			skull.setItemMeta(meta);
		}

		return skull;
	}

	public static ItemStack createBasicItemStack(Material material, String displayName, NamedTextColor color) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();

		if (meta != null) {
			meta.displayName(Component.text(displayName)
					.color(color)
					.decoration(TextDecoration.ITALIC, false));
			item.setItemMeta(meta);
		}

		return item;
	}

	public static ItemStack createBasicItemStack(Material material, Component title, List<Component> description) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();

		if (meta != null) {
			meta.displayName(title);
			meta.lore(description);
			item.setItemMeta(meta);
		}

		return item;
	}
}
