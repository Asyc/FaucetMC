package org.faucetmc.profiler;

import org.apache.logging.log4j.Logger;
import org.faucetmc.server.Faucet;

public class Profiler {

    private static final Logger logger = Faucet.getLogger();
    private static final Object LOG_LOCK = new Object();

    private String name;
    private long time;

    public void beginSection(String name) {
        this.name = name;
        this.time = System.nanoTime();
        log("----Profiler Start: Section {}", name);
    }

    public void endSection() {
        String name = this.name;
        long time = this.time;
        Faucet.EXECUTOR_SERVICE.submit(() -> {
            long difference = System.nanoTime() - time;
            long unit = difference / 1000000L;
            String unitName = "milliseconds";
            if(unit <= 0) {
                unitName = "nanoseconds";
                unit = difference;
            }
            log("----Profiler End: Section {} took {} {} to complete----", name, unit, unitName);
        });
    }

    private void log(String text, Object... args) {
        synchronized (LOG_LOCK) {
            logger.debug(text, args);
        }
    }
}
