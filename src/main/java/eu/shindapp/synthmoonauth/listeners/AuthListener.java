package eu.shindapp.synthmoonauth.listeners;

import eu.shindapp.synthmoonauth.SynthMoonAuth;
import eu.shindapp.synthmoonauth.utils.AuthUtils;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class AuthListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (SynthMoonAuth.getLoggedPlayers().contains(event.getPlayer())) {
            SynthMoonAuth.getLoggedPlayers().remove(event.getPlayer());
        }
        if (new AuthUtils().playerIsANewPlayer(event.getPlayer())) {
            event.getPlayer().getInventory().clear();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (new AuthUtils().playerIsANewPlayer(player)) {
            player.getInventory().addItem(
                    new ItemStack(Material.IRON_HELMET, 1),
                    new ItemStack(Material.IRON_CHESTPLATE,1),
                    new ItemStack(Material.IRON_LEGGINGS,1),
                    new ItemStack(Material.IRON_BOOTS,1),
                    new ItemStack(Material.IRON_PICKAXE, 1),
                    new ItemStack(Material.IRON_AXE, 1),
                    new ItemStack(Material.IRON_SHOVEL, 1),
                    new ItemStack(Material.IRON_HOE, 1),
                    new ItemStack(Material.IRON_SWORD, 1));
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!SynthMoonAuth.getLoggedPlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
