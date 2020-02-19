package org.faucetmc.profiler;

import org.apache.logging.log4j.Logger;
import org.faucetmc.server.Faucet;

public class Profiler {

    private final Logger logger = Faucet.getLogger();

    private String name;
    private long time;

    public void beginSection(String name) {
        this.time = System.nanoTime();
        this.name = name;
        logger.debug("----Profiler: Begin Section: {}----", name);
    }

    public void endSection() {
        long difference = System.nanoTime() - time;
        logger.debug("----Profiler End: Took {} milliseconds to {}----", difference / 1000000L, name);
        this.name = null;
        this.time = 0;
    }

}
