package pl.grzegorz2047.anarchy.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import pl.grzegorz2047.anarchy.AnarchyGuide;
import pl.grzegorz2047.api.AntiLogout;

public class PlayerPvpInteraction implements Listener {
    private final AnarchyGuide anarchyGuide;
    private AntiLogout antiLogout;

    public PlayerPvpInteraction(AnarchyGuide anarchyGuide, AntiLogout antiLogout) {
        this.anarchyGuide = anarchyGuide;
        this.antiLogout = antiLogout;
    }

    @EventHandler
    private void entityDMGbyEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player attacked = null;
        Player attacker = null;
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (damager instanceof Player && entity instanceof Player) {
            attacked = (Player) entity;
            attacker = (Player) damager;

        } else if (damager instanceof Arrow && entity instanceof Player) {
            attacked = (Player) entity;
            ProjectileSource attackerEntity = ((Arrow) damager).getShooter();
            if (attackerEntity instanceof Player) {
                attacker = (Player) attackerEntity;
            }

        } else if (damager instanceof Snowball && entity instanceof Player) {
            attacked = (Player) entity;
            ProjectileSource attackerEntity = ((Snowball) damager).getShooter();
            if (attackerEntity instanceof Player) {
                attacker = (Player) attackerEntity;
            }
        }
        if (attacker != null) {
            this.antiLogout.updateFightData(attacker, attacked);
        }
    }

}
