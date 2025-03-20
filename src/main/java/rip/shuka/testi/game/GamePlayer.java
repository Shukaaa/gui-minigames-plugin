package rip.shuka.testi.game;

import org.bukkit.entity.Player;

public record GamePlayer<INV>(Player player, INV inventoryHolder) { }
