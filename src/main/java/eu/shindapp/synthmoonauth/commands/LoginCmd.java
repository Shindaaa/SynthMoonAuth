package eu.shindapp.synthmoonauth.commands;

import eu.shindapp.synthmoonauth.SynthMoonAuth;
import eu.shindapp.synthmoonauth.models.PlayerLogin;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1) {
                String pwd = args[0];

                PlayerLogin playerLogin = new PlayerLogin().fetchByPseudo(player.getName());

                if (playerLogin == null) {
                    player.sendMessage("§cErreur §8• §7Aucun compte n'est enregistré avec ce §dpseudo §7sur le serveur.");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                    return false;
                }

                if (!playerLogin.getPassword().equals(pwd)) {
                    player.sendMessage("§cErreur §8• §7Le de passe est incorrecte.\nVeuillez réessayer.");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                    return false;
                }

                SynthMoonAuth.getLoggedPlayers().add(player);
                player.sendMessage("§dSynth§5Moon §8• §7Bon retour parmis nous §d" + player.getName() + " §7!");
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                return true;

            } else {
                player.sendMessage("§cErreur §8• §7Le &cformat §7de la commande est incorrecte: §c/login <password>");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                return false;
            }
        }

        return false;
    }
}
