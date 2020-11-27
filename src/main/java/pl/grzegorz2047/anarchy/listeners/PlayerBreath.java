package pl.grzegorz2047.anarchy.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import pl.grzegorz2047.anarchy.events.SecondEvent;

public class PlayerBreath implements Listener {

    private PlayerBreathingManager playerBreathingManager;

    public PlayerBreath(PlayerBreathingManager playerBreathingManager) {
        this.playerBreathingManager = playerBreathingManager;
    }


    @EventHandler
    public void onSecond(SecondEvent event) {
        Bukkit.getOnlinePlayers().forEach(x -> {
            Player player = x.getPlayer();
            Block block = player.getEyeLocation().getBlock();
            String name = player.getName();

            playerBreathingManager.playerBreath(player, block, name);
        });
    }

    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent e) {
        ItemStack item = e.getItem();

        if (!item.getType().equals(Material.POTION)) {
            return;
        }

        if (!(item.getItemMeta() instanceof PotionMeta)) {
            return;
        }

        final PotionMeta meta = (PotionMeta) e.getItem().getItemMeta();
        final PotionData data = meta.getBasePotionData();
        if (data.getType() == PotionType.WATER) {
            playerBreathingManager.regainBreath(e.getPlayer());
        }
    }
}
