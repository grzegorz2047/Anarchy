package pl.grzegorz2047.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.grzegorz2047.api.antilogout.Fight;

import java.util.*;

public class AntiLogout {
    private HashMap<String, Fight> fightList = new HashMap<>();
    private AntiLogoutBarGenerator antiLogoutBarGenerator = new AntiLogoutBarGenerator();

    private boolean ANTI_LOGOUT_ENABLED = true;
    private Messages messages;


    public AntiLogout(Messages messages) {
        this.messages = messages;
    }

    public void clearFight(Player playerKilled, Player killer) {
        String playerKilledName = playerKilled.getName();
        if (killer != null) {
            String killerName = killer.getName();
            fightList.remove(killerName);
        }
        fightList.remove(playerKilledName);
    }

    public void updateFightData(Player firstOpponent, Player secondOpponent) {
        //Pole do optymalizacji ramu
        Fight fight = new Fight(firstOpponent.getName(), secondOpponent.getName(), System.currentTimeMillis());
        fightList.put(firstOpponent.getName(), fight);
        fightList.put(secondOpponent.getName(), fight);
        sendAntiLogoutMessage(firstOpponent, secondOpponent, fight);
        String attackedMsg = this.messages.get("anarchy.fight.attacked");
        firstOpponent.sendMessage(attackedMsg);
        secondOpponent.sendMessage(attackedMsg);
    }

    private boolean hasFightExpired(long cooldown) {
        return cooldown <= System.currentTimeMillis();
    }

    private void sendCanLogoutMsg(Player p) {
        if (p == null) {
            return;
        }
        ActionBar.sendActionBar(p, this.messages.get("anarchy.fight.canLogout"));
    }

    private void checkExpiredFights() {
        List<String> toDelete = new ArrayList<>();
        for (Map.Entry<String, Fight> entry : fightList.entrySet()) {
            // Bukkit.getLogger().log(Level.INFO, "checkFights_for_enter");
            if (hasFightExpired(entry.getValue().getEndCooldown())) {
                String playerName = entry.getKey();
                toDelete.add(playerName);
                Player p = Bukkit.getPlayer(playerName);
                sendCanLogoutMsg(p);
            }
        }
        removeExpiredFights(toDelete);
    }

    private void removeExpiredFights(List<String> toDelete) {
        for (String user : toDelete) {
            fightList.remove(user);
        }
    }


    public void update() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isPlayerDuringFight(player.getName())) {
                sendAntiLogoutMessage(player, fightList.get(player.getName()));
            }
        }
        checkExpiredFights();
    }


    public void sendAntiLogoutMessage(Player firstOpponent, Fight fight) {
        long secCalculate = (fight.getEndCooldown() - System.currentTimeMillis()) / 1000;
        System.out.println(secCalculate + " secCalculate");
        ActionBar.sendActionBar(firstOpponent, antiLogoutBarGenerator.generateActionBarAntilogout(secCalculate));
    }

    public void sendAntiLogoutMessage(Player firstOpponent, Player secondOpponent, Fight fight) {
        sendAntiLogoutMessage(firstOpponent, fight);
        sendAntiLogoutMessage(secondOpponent, fight);
    }

    private boolean isPlayerDuringFight(String name) {
        return fightList.containsKey(name);
    }

}

