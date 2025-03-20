package rip.shuka.testi.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import rip.shuka.testi.game.tictactoe.TictactoeInventory;

import java.util.List;

public class InventoryListener implements Listener {
	private final List<Component> customInventoryTitles = List.of(TictactoeInventory.nameComponent);

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		for (Component title : customInventoryTitles) {
			if (event.getView().title().equals(title)) {
				event.setCancelled(true);
				break;
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		for (Component title : customInventoryTitles) {
			if (event.getView().title().equals(title)) {
				event.setCancelled(true);
				break;
			}
		}

		if (event.getClickedInventory() == null) {
			return;
		}

		if (event.getClickedInventory().getHolder() instanceof TictactoeInventory inventory) {
			inventory.click((Player) event.getWhoClicked(), event.getSlot());
		}
	}
}
