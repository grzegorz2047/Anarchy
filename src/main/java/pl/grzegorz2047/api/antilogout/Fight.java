package pl.grzegorz2047.api.antilogout;

public class Fight {
    private String secondOpponent;
    private String firstOpponent;
    private Long lastHitTime;
    public static final int COOLDOWN = 30;//ile trzeba poczekac przed logoutem w sek

    public Fight(String firstOpponent, String secondOpponent, Long lastHitTime) {
        this.firstOpponent = firstOpponent;
        this.secondOpponent = secondOpponent;
        this.lastHitTime = lastHitTime;
    }

    public Long getEndCooldown() {
        return lastHitTime + COOLDOWN * 1000;
    }

    public String getSecondOpponent() {
        return secondOpponent;
    }

    public String getFirstOpponent() {
        return firstOpponent;
    }

    public Long getLastHitTime() {
        return lastHitTime;
    }

    public void setLastHitTime(Long lastHitTime) {
        this.lastHitTime = lastHitTime;
    }

    public int getCooldown() {
        return COOLDOWN;
    }

    public void setSecondOpponent(String secondOpponent) {
        this.secondOpponent = secondOpponent;
    }

    public void setFirstOpponent(String firstOpponent) {
        this.firstOpponent = firstOpponent;
    }
}
