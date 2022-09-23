package eu.shindapp.synthmoonauth.utils;

import eu.shindapp.synthmoonauth.models.PlayerLogin;
import org.bukkit.entity.Player;

public class AuthUtils {

    public boolean playerIsANewPlayer(Player player) {
        try {
            PlayerLogin playerLogin = new PlayerLogin().fetchByPseudo(player.getName());
            if (playerLogin == null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
