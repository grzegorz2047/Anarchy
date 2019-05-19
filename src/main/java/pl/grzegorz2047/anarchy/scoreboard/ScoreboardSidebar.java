package pl.grzegorz2047.anarchy.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardSidebar {
    private void addSidebar(Scoreboard scoreboard) {
        String label = ChatColor.translateAlternateColorCodes('&', "&eKille");
        Objective objective = scoreboard.registerNewObjective("stats", "dummy", label);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§6§l *** Anarchy ***");
        addLineToScoreboard(objective, "kille: 69", 9);
        addLineToScoreboard(objective, "deady: 777", 8);
        addLineToScoreboard(objective, "aha.fajnyserw.pl", 7);
    }

    private void addLineToScoreboard(Objective objective, String data, int position) {
        Score killsScore = objective.getScore(data);
        killsScore.setScore(position);
    }

    public void addHealthbar(Player player, Scoreboard scoreboard) {
        Objective healthObj = scoreboard.registerNewObjective("showhealth", "health", "health");
        healthObj.setDisplaySlot(DisplaySlot.BELOW_NAME);
        healthObj.setDisplayName(" §c§l❤");
        player.setHealth(player.getHealth());
    }

    public void addWholeScoreboard(Player player) {
        ScoreboardManager scoreboardManager = Bukkit.getServer().getScoreboardManager();
        Scoreboard newScoreboard = scoreboardManager.getNewScoreboard();
        player.setScoreboard(newScoreboard);
        Scoreboard scoreboard = player.getScoreboard();
        addSidebar(scoreboard);
        addHealthbar(player, scoreboard);
    }
}
