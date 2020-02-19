package org.faucetmc.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.faucetmc.server.config.ServerProperties;
import org.faucetmc.world.World;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Faucet {

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private static Faucet instance;

    private static Logger logger = LogManager.getLogger(Faucet.class);
    private Yaml yaml = new Yaml();

    private ServerProperties properties;

    private List<World> worlds = new LinkedList<>();

    public Faucet() {
        instance = this;
        logger.info("Loading Properties");
        {
            File propertiesFile = new File("server.yml");
            if(!propertiesFile.exists()) {
                properties = new ServerProperties();
                properties.save(propertiesFile);
            } else {
                try (FileInputStream in = new FileInputStream(propertiesFile)) {
                    properties = yaml.loadAs(in, ServerProperties.class);
                } catch (IOException e) {
                    logger.fatal("Could not create new [server.properties] file. Error: {}", e.getMessage());
                }
            }
        }
        logger.info("Loading World");
        worlds.add(new World(properties.getLevelName()));
    }

    public ServerProperties getProperties() {
        return properties;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static Faucet getInstance() {
        return instance;
    }
}
