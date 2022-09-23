package eu.shindapp.synthmoonauth;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import eu.shindapp.synthmoonauth.commands.LoginCmd;
import eu.shindapp.synthmoonauth.commands.RegisterCmd;
import eu.shindapp.synthmoonauth.models.PlayerLogin;
import eu.shindapp.synthmoonauth.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.sql.SQLException;

public final class SynthMoonAuth extends JavaPlugin {

    private static SynthMoonAuth instance;
    private static JdbcConnectionSource connection;

    private static Dao<PlayerLogin, String> playerLoginDao;

    @Override
    public void onEnable() {
        instance = this;
        new ConfigUtils().loadConfiguration();

        try {
            connection = new JdbcConnectionSource("jdbc:mysql://" + getConfig().getString("mysql.hostname") + ":3306/" + getConfig().getString("mysql.database") + "?autoReconnect=true&wait_timeout=172800", getConfig().getString("mysql.username"), getConfig().getString("mysql.password"));
            playerLoginDao = DaoManager.createDao(connection, PlayerLogin.class);
            TableUtils.createTableIfNotExists(connection, PlayerLogin.class);
        } catch (SQLException e) {
            e.printStackTrace();
            severe("Error when trying to connect to SQL Database! shutting down...");
            Bukkit.getServer().shutdown();
        }

        registerEvents();

        this.getCommand("register").setExecutor(new RegisterCmd());
        this.getCommand("login").setExecutor(new LoginCmd());
    }

    @Override
    public void onDisable() {
        //...
    }

    private void registerEvents() {
        String packageName = getClass().getPackage().getName();
        for (Class<?> clazz : new Reflections(packageName + ".listeners").getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listener, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void info(String msg) {
        instance.getLogger().info(msg);
    }

    public void severe(String msg) {
        instance.getLogger().severe(msg);
    }

    public static SynthMoonAuth getInstance() {
        return instance;
    }

    public static JdbcConnectionSource getConnection() {
        return connection;
    }

    public static Dao<PlayerLogin, String> getPlayerLoginDao() {
        return playerLoginDao;
    }
}
