package eu.shindapp.synthmoonauth.listeners;

import eu.shindapp.synthmoonauth.SynthMoonAuth;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AuthListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (SynthMoonAuth.getLoggedPlayers().contains(event.getPlayer())) {
            SynthMoonAuth.getLoggedPlayers().remove(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!SynthMoonAuth.getLoggedPlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
