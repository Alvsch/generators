package me.alvsch.generators;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.alvsch.generators.commands.GeneratorsCommand;
import me.alvsch.generators.commands.economy.CoinsCommand;
import me.alvsch.generators.commands.economy.SellCommand;
import me.alvsch.generators.events.PlayerEvents;
import me.alvsch.generators.inventory.InventoryEvents;
import me.alvsch.generators.inventory.InventoryHandler;
import me.alvsch.generators.item.ItemHandler;
import me.alvsch.generators.runnable.Runnable;
import me.alvsch.generators.tabcompletion.CoinsTabCompletion;
import me.alvsch.generators.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    private static Main plugin;

    public JsonObject data;

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;

    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;

        getConfig().options().copyDefaults(true);
        saveConfig();

        try {
            data = loadDataFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }


        ItemHandler.init();
        InventoryHandler.init();


        registerEvents();
        registerCommands();
        Runnable runnable = new Runnable();
        runnable.startGens();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        try {
            saveDataFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }


    public void registerCommands() {

        getCommand("generators").setExecutor(new GeneratorsCommand());

        getCommand("coins").setExecutor(new CoinsCommand());
        getCommand("coins").setTabCompleter(new CoinsTabCompletion());

        getCommand("sell").setExecutor(new SellCommand());

    }

    public void registerEvents() {

        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);

    }

    private void saveDataFile() throws IOException {
        File dataFile = new File(getDataFolder(), "data.json");
        FileWriter fileWriter = new FileWriter(dataFile, false);
        fileWriter.write(data.toString());
        fileWriter.close();

    }
    private JsonObject loadDataFile() throws FileNotFoundException {
        // load data.json (generate one if not there)
        // console and IO, instance
        File dataFile = new File(getDataFolder(), "data.json");
        if (!dataFile.exists()) {
            Utils.loadData(this, "data.json");
        }

        Scanner scanner = new Scanner(dataFile);
        StringBuilder data = new StringBuilder();
        while (scanner.hasNextLine()) {
            data.append(scanner.nextLine());
        }
        if(data.toString().isEmpty()) {
            data.append("{");
            data.append("\"players\": {},");
            data.append("\"gens\": {}");
            data.append("}");
        }
        JsonParser parser = new JsonParser();
        return (JsonObject) parser.parse(data.toString());
    }

    public static Economy getEcon() {
        return econ;
    }
    public static Main getPlugin() {
        return plugin;
    }
}
