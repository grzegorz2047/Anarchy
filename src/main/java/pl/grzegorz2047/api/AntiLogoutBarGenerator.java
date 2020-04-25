package pl.grzegorz2047.api;

import pl.grzegorz2047.api.antilogout.Fight;

public class AntiLogoutBarGenerator {

    String generateActionBarAntilogout(long time) {
        StringBuilder out = new StringBuilder();
        out.append("§6§lLogout za ");
        if (time == 0) {
            out.append("§4▉▉▉▉▉▉▉▉▉▉");
            out.append("§e ").append(time).append(" §6sec.");
            return out.toString();
        }
        float val = (float) time / (float) Fight.COOLDOWN;
        float perc = val * 100;
        out.append(makeHealthString(perc));
        out.append("§e ").append(time).append(" §6sec.");
        return out.toString();
    }

    private String makeHealthString(float perc) {
        String splitColor = "§4";
        String firstColor = "§c";
        String defaultAntiLogoutBar = "▉▉▉▉▉▉▉▉▉▉";
        if (perc <= 10) {
            return splitColor + defaultAntiLogoutBar;
        }

        int splitPosition = ((int) perc / 10) % 10;

        String partBar = defaultAntiLogoutBar.substring(0, splitPosition) + splitColor;
        String finalBar = partBar + defaultAntiLogoutBar.substring(splitPosition);
        finalBar = firstColor + finalBar;
        return finalBar;
    }
}
