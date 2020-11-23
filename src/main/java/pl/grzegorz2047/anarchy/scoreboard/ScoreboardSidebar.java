package pl.grzegorz2047.anarchy.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardSidebar {

    private String airLabel = "§1§l⬤  ";

    private void addSidebar(Scoreboard scoreboard) {
        String label = ChatColor.translateAlternateColorCodes('&', "&eKille");
        Objective objective = scoreboard.registerNewObjective("stats", "dummy", label);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§6§l     Anarchy    ");
        addLineToScoreboard(objective, "§7kille:§b 69", 2);
        addLineToScoreboard(objective, "§7deady:§b 777", 1);
        addLineToScoreboard(objective, "§6aha.fajnyserw.pl", 0);
        addEntry(scoreboard, objective, airLabel, String.valueOf(30), -1);
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

    public void updateBreathingTime(Player p, long remainingTime) {
        if (remainingTime <= 0) {
            remainingTime = 0;
        } else {
            remainingTime = (int) (remainingTime / 1000);
        }
        Scoreboard scoreboard = p.getScoreboard();
        if (scoreboard.getTeam(airLabel) != null) {
            updateEntry(scoreboard, airLabel, remainingTime);
        }
    }

    public Team addEntry(Scoreboard scoreboard, Objective objective, String name, String value, int position) {
        Team t = scoreboard.registerNewTeam(name);
        t.setPrefix("");
        //t.setPrefix(" §0∙ ");
        t.addEntry(name);
        t.setSuffix(value);
        Score score = objective.getScore(name);
        score.setScore(position);
        return t;
    }

    public void updateEntry(Scoreboard scoreboard, String name, long value) {
        Team t = scoreboard.getTeam(name);
        t.setSuffix(" " + value);
    }
}
