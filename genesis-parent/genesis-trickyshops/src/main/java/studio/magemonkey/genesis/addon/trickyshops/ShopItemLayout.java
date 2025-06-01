package studio.magemonkey.genesis.addon.trickyshops;

import org.bukkit.configuration.ConfigurationSection;
import studio.magemonkey.genesis.core.GenesisBuy;
import studio.magemonkey.genesis.events.GenesisLoadShopItemEvent;
import studio.magemonkey.genesis.managers.ClassManager;

import java.util.ArrayList;
import java.util.List;

public class ShopItemLayout {
    private       VariableConfigurationSection vs;
    private final List<VariableLine>           input = new ArrayList<>();

    public ShopItemLayout(ConfigurationSection section) {
        ConfigurationSection in  = section.getConfigurationSection("input");
        ConfigurationSection out = section.getConfigurationSection("output");

        if (out != null) {
            this.vs = new VariableConfigurationSection(out, this);
        }

        if (in != null) {
            for (String path : in.getKeys(true)) {
                String s = in.getString(path);
                this.input.add(new VariableLine(path, s));
            }

            List<String> rest = in.getStringList("rest");
            if (rest != null) {
                this.calculations = new ArrayList<>();
                for (String line : rest) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String variable    = parts[0].trim();
                        String calculation = parts[1].trim();
                        this.calculations.add(new VariableCalculation(variable, calculation));
                    }
                }
            }
        }
    }

    private List<VariableCalculation> calculations;

    public String transform(String s, boolean include_calculations) {
        if (s != null) {
            for (VariableLine line : getVariableLines()) {
                s = line.transform(s);
            }
            if (this.calculations != null && include_calculations) {
                for (VariableCalculation calculation : this.calculations) {
                    s = calculation.transform(s, this);
                }
            }
        }
        return s;
    }


    public List<GenesisBuy> loadShopItem(GenesisLoadShopItemEvent event) {
        if (this.vs != null) {
            updateVariables(event.getConfigurationSection());
            return ClassManager.manager.getBuyItemHandler()
                    .createBuyItem(event.getShop(), event.getShopItemName(), this.vs);
        }
        return null;
    }


    public void updateVariables(ConfigurationSection section) {
        for (VariableLine line : this.input) {
            line.loadCurrentValues(section);
        }
    }

    public List<VariableLine> getVariableLines() {
        return this.input;
    }
}
