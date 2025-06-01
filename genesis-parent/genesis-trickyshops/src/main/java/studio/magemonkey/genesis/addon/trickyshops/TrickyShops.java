package studio.magemonkey.genesis.addon.trickyshops;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import studio.magemonkey.genesis.api.GenesisAddon;
import studio.magemonkey.genesis.api.GenesisAddonConfig;
import studio.magemonkey.genesis.managers.config.FileHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TrickyShops extends GenesisAddon {
    private Map<String, ShopItemLayout> layouts;

    public String getAddonName() {
        return "TrickyShops";
    }

    @Override
    public String getRequiredGenesisVersion() {
        return "1.0.0";
    }

    @Override
    public void enableAddon() {
        load();

        getServer().getPluginManager().registerEvents(new GenListener(this), this);
    }

    public void load() {
        File bossShopFolder = getGenesis().getDataFolder();
        File layoutsfile    = new File(bossShopFolder, "/addons/" + getAddonName() + "/layouts.yml");
        if (!layoutsfile.exists()) {
            (new FileHandler()).copyFromJar(this, "layouts.yml");
        }

        GenesisAddonConfig config = new GenesisAddonConfig(this, layoutsfile);
        config.reload();
        FileConfiguration c = config.getConfig();

        this.layouts = new HashMap<>();
        for (String path : c.getKeys(false)) {
            ConfigurationSection section = c.getConfigurationSection(path);
            this.layouts.put(path, new ShopItemLayout(section));
        }
    }

    @Override
    public void genesisReloaded(CommandSender commandSender) {
        load();
    }

    @Override
    public void disableAddon() {}

    @Override
    public void genesisFinishedLoading() {
    }

    public ShopItemLayout getLayout(String s) {
        if (this.layouts != null) {
            return this.layouts.get(s);
        }
        return null;
    }
}
