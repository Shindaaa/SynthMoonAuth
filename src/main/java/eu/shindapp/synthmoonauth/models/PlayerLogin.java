package eu.shindapp.synthmoonauth.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import eu.shindapp.synthmoonauth.SynthMoonAuth;
import lombok.Data;

import java.sql.SQLException;

@Data
@DatabaseTable(tableName = "synthmoon_player_login")
public class PlayerLogin {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String pseudo;

    @DatabaseField
    private String password;

    @DatabaseField
    private String recoveryKey;

    public PlayerLogin fetchByPseudo(String pseudo) {
        try {
            PlayerLogin playerLogin = SynthMoonAuth.getPlayerLoginDao().queryBuilder().where().eq("pseudo", pseudo).queryForFirst();
            if (playerLogin != null) {
                return playerLogin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PlayerLogin fetchByPassword(String password) {
        try {
            PlayerLogin playerLogin = SynthMoonAuth.getPlayerLoginDao().queryBuilder().where().eq("password", password).queryForFirst();
            if (playerLogin != null) {
                return playerLogin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PlayerLogin fetchByRecoveryKey(String recoveryKey) {
        try {
            PlayerLogin playerLogin = SynthMoonAuth.getPlayerLoginDao().queryBuilder().where().eq("recoveryKey", pseudo).queryForFirst();
            if (playerLogin != null) {
                return playerLogin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save() {
        try {
            SynthMoonAuth.getPlayerLoginDao().createOrUpdate(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            SynthMoonAuth.getPlayerLoginDao().delete(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
