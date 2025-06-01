package studio.magemonkey.genesis.addon.trickyshops;

import lombok.AllArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import studio.magemonkey.genesis.events.GenesisLoadShopItemEvent;

@AllArgsConstructor
public class GenListener implements Listener {
    private final TrickyShops shops;

    @EventHandler
    public void loadShopItem(GenesisLoadShopItemEvent event) {
        ConfigurationSection s      = event.getConfigurationSection();
        String               layout = s.getString("Layout");
        if (layout != null) {
            ShopItemLayout l = this.shops.getLayout(layout);
            if (l != null)
                l.loadShopItem(event).forEach(shopItem -> event.useCustomShopItem(shopItem));
        }
    }
}
