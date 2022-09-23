package eu.shindapp.synthmoonauth.utils;

import eu.shindapp.synthmoonauth.SynthMoonAuth;

public class ConfigUtils {

    public ConfigUtils() {
        super();
    }

    public void loadConfiguration() {
        SynthMoonAuth.getInstance().getConfig().options().copyDefaults(true);
        SynthMoonAuth.getInstance().saveConfig();
    }

}
