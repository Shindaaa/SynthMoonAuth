package eu.shindapp.synthmoonauth.commands;

import eu.shindapp.synthmoonauth.SynthMoonAuth;
import eu.shindapp.synthmoonauth.models.PlayerLogin;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 2) {
                String pwd1 = args[0];
                String pwd2 = args[1];

                if (!pwd1.equals(pwd2)) {
                    player.sendMessage("§cErreur §8• §7Les deux mots de passes ne sont pas identiques.\nVeuillez réessayer.");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                    return false;
                }

                if (new PlayerLogin().fetchByPseudo(player.getName()) != null) {
                    player.sendMessage("§cErreur §8• §7Une personne avec le même pseudo que vous est déjà enregistré sur le serveur.");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                    return false;
                }

                try {
                    PlayerLogin playerLogin = new PlayerLogin();
                    playerLogin.setPseudo(player.getName());
                    playerLogin.setPassword(pwd1);
                    playerLogin.setRecoveryKey(null);
                    playerLogin.save();
                } catch (Exception e) {
                    e.printStackTrace();
                    SynthMoonAuth.getInstance().severe("Error when trying to store login data for player " + player.getName());
                    player.sendMessage("§cErreur §8• &7Une §cerreur §7est survenue pendant §cl'enregistrement de vos données.\n§7Veuillez §ccontacter §7un &cadministrateur §7sur le §cdiscord.");
                    player.sendMessage("§eInfo §8• §7Lien vers le §ediscord: §bhttps://discord.gg/xxxx");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                }

                SynthMoonAuth.getLoggedPlayers().add(player);
                player.sendMessage("§dSynth§5Moon §8• §7Vous avez été §acorrectement enregistré §7sur le serveur.\n§7Amusez vous bien.");
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                //...
                return true;

            } else {
                player.sendMessage("§cErreur §8• §7Le &cformat §7de la commande est incorrecte: §c/register <password> <password>");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                return false;
            }
        }

        return false;
    }
}
