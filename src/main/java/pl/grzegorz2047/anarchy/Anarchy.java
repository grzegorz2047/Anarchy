package pl.grzegorz2047.anarchy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import pl.grzegorz2047.anarchy.exceptions.CantLoadProperties;
import pl.grzegorz2047.anarchy.listeners.*;
import pl.grzegorz2047.api.AntiLogout;
import pl.grzegorz2047.api.Messages;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.logging.Level;

public class Anarchy extends JavaPlugin {

    private AnarchyGuide anarchyGuide;
    private Watcher watcher;
    private BukkitTask task;
    private Properties prop;
    private Messages messages;

    @Override
    public void onEnable() {
        try {
            String pluginFolderUrl = this.getDataFolder().getAbsolutePath() + File.separator;
            messages = loadMessageProperties(pluginFolderUrl, "messages_pl.properties");
            prop = loadProperties(pluginFolderUrl, "plugin.properties", new Properties());
            anarchyGuide = new AnarchyGuide(prop, messages);

        } catch (CantLoadProperties cantLoadProperties) {
            getLogger().log(Level.SEVERE, "Anarchy couldn't start!");
            throw new RuntimeException();
        }
        AntiLogout antiLogout = new AntiLogout(messages);
        watcher = new Watcher(antiLogout);
        task = Bukkit.getScheduler().runTaskTimer(this, watcher, 0l, 20l);
        registerEvents(antiLogout);
        getLogger().info("Anarchy was enabled!");
    }

    private Messages loadMessageProperties(String pluginFolderUrl, String fileName) throws CantLoadProperties {
        return (Messages) loadProperties(pluginFolderUrl, fileName, new Messages());
    }


    private void registerEvents(AntiLogout antiLogout) {
        PlayerBreathingManager playerBreathingManager = new PlayerBreathingManager();

        PluginManager pluginManager = Bukkit.getPluginManager();
        if (anarchyGuide.isForCrackersons()) {
            pluginManager.registerEvents(new AuthMeLogin(anarchyGuide), this);
        } else {
            pluginManager.registerEvents(new PlayerJoin(anarchyGuide), this);
        }
        pluginManager.registerEvents(new PlayerRespawn(anarchyGuide, playerBreathingManager), this);
        pluginManager.registerEvents(new PlayerDeath(anarchyGuide), this);
        pluginManager.registerEvents(new PlayerPvpInteraction(anarchyGuide, antiLogout), this);
        pluginManager.registerEvents(new PlayerQuit(antiLogout), this);
        pluginManager.registerEvents(new SafeLogin(), this);
        ;
        pluginManager.registerEvents(new PlayerBreath(playerBreathingManager), this);
        if (anarchyGuide.useAnarchyChatHandler()) {
            pluginManager.registerEvents(new PlayerChat(anarchyGuide), this);
        }
    }

    private Properties loadProperties(String pluginFolderUrl, String fileName, Properties properties) throws CantLoadProperties {
        String propertyUrl = pluginFolderUrl + fileName;
        boolean exists = Files.exists(Paths.get(propertyUrl));
        if (!exists) {
            createProperties(pluginFolderUrl, propertyUrl, fileName);
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

    private void createProperties(String pluginFolderUrl, String propertyUrl, String filename) {
        try {
            Files.createDirectory(Paths.get(pluginFolderUrl));
            Files.copy(this.getResource(filename), Paths.get(propertyUrl), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        task.cancel();
    }
}
