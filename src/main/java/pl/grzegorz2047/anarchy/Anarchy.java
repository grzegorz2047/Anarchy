package pl.grzegorz2047.anarchy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.grzegorz2047.anarchy.listeners.PlayerJoinListener;

public class Anarchy extends JavaPlugin {

    @Override
    public void onEnable() {
        registerEvents();
        getLogger().info("Anarchy was enabled!");
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }


}
