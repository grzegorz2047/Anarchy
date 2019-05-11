package pl.grzegorz2047.anarchy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.grzegorz2047.anarchy.listeners.PlayerJoinListener;
import pl.grzegorz2047.anarchy.listeners.PlayerRespawn;

public class Anarchy extends JavaPlugin {

    private AnarchyGuide anarchyGuide = new AnarchyGuide();


    @Override
    public void onEnable() {
        registerEvents();
        getLogger().info("Anarchy was enabled!");
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(anarchyGuide), this);
        pluginManager.registerEvents(new PlayerRespawn(anarchyGuide), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }


}
