package pl.grzegorz2047.anarchy;

public class OutsiderData {

    private final long swimOutOfWaterTime;
    private final boolean isFirstTime;
    private long timeOustideWater;
    private long MAX_OUTSIDE_WATER_TIME = 30 * 1000;

    public OutsiderData(long swimOutWaterTime, boolean isFirstTime) {
        this.swimOutOfWaterTime = swimOutWaterTime;
        this.isFirstTime = isFirstTime;
        if(isFirstTime) {
            MAX_OUTSIDE_WATER_TIME = 60 * 1000;
        }
    }

    public void setOutsideTime(long timeOustideWater) {
        this.timeOustideWater = timeOustideWater;
    }

    public boolean isOutOfAir() {
        return timeOustideWater - swimOutOfWaterTime > MAX_OUTSIDE_WATER_TIME;
    }

    public long getRemainingTime() {
        long l = (timeOustideWater + MAX_OUTSIDE_WATER_TIME) - swimOutOfWaterTime;
        if (l >= 0) {
            return l;
        } else {
            return 0;
        }
    }
}
