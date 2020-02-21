package org.faucetmc.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.faucetmc.network.NetworkManager;
import org.faucetmc.server.config.ServerProperties;
import org.faucetmc.world.World;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Faucet {

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    public static final Gson GSON_PRETTY_PRINT = new GsonBuilder().setPrettyPrinting().create();
    public static final Gson GSON = new Gson();
    public static MinecraftServer MINECRAFT_SERVER;

    private static Faucet instance;

    private static Logger logger = LogManager.getLogger(Faucet.class);
    private Yaml yaml = new Yaml();

    private ServerProperties properties;

    private NetworkManager networkManager;

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

        MINECRAFT_SERVER = new MinecraftServer();

        logger.info("Starting Network Server");
        {
            try {
                this.networkManager = new NetworkManager();
                this.networkManager.start(InetAddress.getByName(properties.getServerIP()), properties.getServerPort());
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        logger.info("Starting Game Loop");
        MINECRAFT_SERVER.startGameLoop();
    }

    public void doTick() {

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
