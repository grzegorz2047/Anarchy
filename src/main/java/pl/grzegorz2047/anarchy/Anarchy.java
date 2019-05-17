package pl.grzegorz2047.anarchy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.grzegorz2047.anarchy.exceptions.CantLoadProperties;
import pl.grzegorz2047.anarchy.listeners.AuthMeLogin;
import pl.grzegorz2047.anarchy.listeners.PlayerJoinListener;
import pl.grzegorz2047.anarchy.listeners.PlayerRespawn;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.logging.Level;

public class Anarchy extends JavaPlugin {

    private AnarchyGuide anarchyGuide;


    @Override
    public void onEnable() {
        try {
            registerEvents();
        } catch (CantLoadProperties cantLoadProperties) {
            getLogger().log(Level.SEVERE, "Anarchy couldn't start!");
            throw new RuntimeException();
        }
        getLogger().info("Anarchy was enabled!");
    }

    private void registerEvents() throws CantLoadProperties {
        String pluginFolderUrl = this.getDataFolder().getAbsolutePath() + File.separator;
        Properties prop = loadProperties(pluginFolderUrl);
        anarchyGuide = new AnarchyGuide(prop);
        PluginManager pluginManager = Bukkit.getPluginManager();
        if(anarchyGuide.isForCrackersons()){
            System.out.println("Dla krakersow");
            pluginManager.registerEvents(new AuthMeLogin(anarchyGuide), this);
        } else {
            System.out.println("Dla normalnych");
            pluginManager.registerEvents(new PlayerJoinListener(anarchyGuide), this);
        }
        pluginManager.registerEvents(new PlayerRespawn(anarchyGuide), this);
    }

    private Properties loadProperties(String pluginFolderUrl) throws CantLoadProperties {
        Properties properties = new Properties();
        String propertyUrl = pluginFolderUrl + "plugin.properties";
        boolean exists = Files.exists(Paths.get(propertyUrl));
        if (!exists) {
            createProperties(pluginFolderUrl, propertyUrl);
        }
        if (loadproperties(properties, propertyUrl)) return properties;
        throw new CantLoadProperties("Plugin nie zdolal zaladowac ustawien z pliku!");
    }

    private boolean loadproperties(Properties properties, String propertyUrl) {
        try (InputStream input = new FileInputStream(propertyUrl)) {
            properties.load(input);
            return true;
        } catch (IOException io) {
            io.printStackTrace();
        }
        return false;
    }

    private void createProperties(String pluginFolderUrl, String propertyUrl) {
        try {
            Files.createDirectory(Paths.get(pluginFolderUrl));
            Files.copy(this.getResource("plugin.properties"), Paths.get(propertyUrl), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }


}
