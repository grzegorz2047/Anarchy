package pl.grzegorz2047.anarchy;

import pl.grzegorz2047.api.AntiLogout;

public class Watcher implements Runnable {
    private final AntiLogout logout;

    private int seconds;

    public Watcher(AntiLogout logout) {
        this.logout = logout;
    }

    @Override
    public void run() {
        seconds++;
        if (seconds % 60 == 0) {
            seconds = 0;
        }

        logout.update();

    }

}
