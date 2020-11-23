package pl.grzegorz2047.anarchy;

public class OutsiderData {

    private final long timeWhenPlayerGotOutOfWater;
    private final boolean isFirstTime;
    private long timeOustideWater;
    private long MAX_OUTSIDE_WATER_TIME = 30 * 1000;

    public OutsiderData(long swimOutWaterTime, boolean isFirstTime) {
        this.timeWhenPlayerGotOutOfWater = swimOutWaterTime;
        this.isFirstTime = isFirstTime;
        if(isFirstTime) {
            MAX_OUTSIDE_WATER_TIME = 60 * 1000;
        }
    }

    public void setOutsideTime(long timeOustideWater) {
        this.timeOustideWater = timeOustideWater;
    }

    public boolean isOutOfAir() {
        return timeOustideWater - timeWhenPlayerGotOutOfWater > MAX_OUTSIDE_WATER_TIME;
    }

    public long getRemainingTime() {
        long diff = (timeWhenPlayerGotOutOfWater + MAX_OUTSIDE_WATER_TIME) - timeOustideWater;
        if (diff >= 0) {
            return diff;
        } else {
            return 0;
        }
    }
}
