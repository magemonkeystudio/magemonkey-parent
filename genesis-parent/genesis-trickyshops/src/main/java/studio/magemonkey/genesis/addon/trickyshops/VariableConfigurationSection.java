package studio.magemonkey.genesis.addon.trickyshops;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VariableConfigurationSection implements ConfigurationSection {
    private final ConfigurationSection section;
    private final ShopItemLayout       layout;

    public VariableConfigurationSection(ConfigurationSection section, ShopItemLayout layout) {
        this.section = section;
        this.layout = layout;
    }

    public void addDefault(String arg0, Object arg1) {
        this.section.addDefault(arg0, arg1);
    }

    @Override
    public @NotNull List<String> getComments(@NotNull String path) {
        return this.section.getComments(path);
    }

    @Override
    public @NotNull List<String> getInlineComments(@NotNull String path) {
        return this.section.getInlineComments(path);
    }

    @Override
    public void setComments(@NotNull String path, @Nullable List<String> comments) {
        this.section.setComments(path, comments);
    }

    @Override
    public void setInlineComments(@NotNull String path, @Nullable List<String> comments) {
        this.section.setInlineComments(path, comments);
    }

    public boolean contains(String arg0) {
        return this.section.contains(arg0);
    }

    public boolean contains(String arg0, boolean arg1) {
        return this.section.contains(arg0, arg1);
    }

    public ConfigurationSection createSection(String arg0) {
        return this.section.createSection(arg0);
    }

    public ConfigurationSection createSection(String arg0, Map<?, ?> arg1) {
        return this.section.createSection(arg0, arg1);
    }

    public Object get(String arg0) {
        return replaceObject(this.section.get(arg0));
    }

    public Object get(String arg0, Object arg1) {
        return replaceObject(this.section.get(arg0, arg1));
    }

    public boolean getBoolean(String arg0) {
        if (isBoolean(arg0)) {
            return replaceBoolean(String.valueOf(this.section.getBoolean(arg0))).booleanValue();
        }
        return replaceBoolean(this.section.getString(arg0)).booleanValue();
    }

    public boolean getBoolean(String arg0, boolean arg1) {
        if (isBoolean(arg0)) {
            return replaceBoolean(String.valueOf(this.section.getBoolean(arg0, arg1))).booleanValue();
        }
        return replaceBoolean(this.section.getString(arg0)).booleanValue();
    }

    public List<Boolean> getBooleanList(String arg0) {
        return this.section.getBooleanList(arg0);
    }

    public List<Byte> getByteList(String arg0) {
        return this.section.getByteList(arg0);
    }

    public List<Character> getCharacterList(String arg0) {
        return this.section.getCharacterList(arg0);
    }

    public Color getColor(String arg0) {
        return this.section.getColor(arg0);
    }

    public Color getColor(String arg0, Color arg1) {
        return this.section.getColor(arg0, arg1);
    }

    public ConfigurationSection getConfigurationSection(String arg0) {
        return this.section.getConfigurationSection(arg0);
    }

    public String getCurrentPath() {
        return this.section.getCurrentPath();
    }

    public ConfigurationSection getDefaultSection() {
        return this.section.getDefaultSection();
    }

    public double getDouble(String arg0) {
        if (isDouble(arg0)) {
            return replaceDouble(String.valueOf(this.section.getDouble(arg0))).doubleValue();
        }
        return replaceDouble(this.section.getString(arg0)).doubleValue();
    }

    public double getDouble(String arg0, double arg1) {
        if (isDouble(arg0)) {
            return replaceDouble(String.valueOf(this.section.getDouble(arg0, arg1))).doubleValue();
        }
        return replaceDouble(this.section.getString(arg0)).doubleValue();
    }

    public List<Double> getDoubleList(String arg0) {
        return this.section.getDoubleList(arg0);
    }

    public List<Float> getFloatList(String arg0) {
        return this.section.getFloatList(arg0);
    }

    public int getInt(String arg0) {
        if (isInt(arg0)) {
            return replaceInt(String.valueOf(this.section.getInt(arg0))).intValue();
        }
        return replaceInt(this.section.getString(arg0)).intValue();
    }

    public int getInt(String arg0, int arg1) {
        if (isInt(arg0)) {
            return replaceInt(String.valueOf(this.section.getInt(arg0, arg1))).intValue();
        }
        return replaceInt(this.section.getString(arg0)).intValue();
    }

    public List<Integer> getIntegerList(String arg0) {
        return this.section.getIntegerList(arg0);
    }

    public ItemStack getItemStack(String arg0) {
        return this.section.getItemStack(arg0);
    }

    public ItemStack getItemStack(String arg0, ItemStack arg1) {
        return this.section.getItemStack(arg0, arg1);
    }

    public Set<String> getKeys(boolean arg0) {
        return this.section.getKeys(arg0);
    }

    public List<?> getList(String arg0) {
        return this.section.getList(arg0);
    }

    public List<?> getList(String arg0, List<?> arg1) {
        return this.section.getList(arg0, arg1);
    }

    public long getLong(String arg0) {
        return this.section.getLong(arg0);
    }

    public long getLong(String arg0, long arg1) {
        return this.section.getLong(arg0, arg1);
    }

    public List<Long> getLongList(String arg0) {
        return this.section.getLongList(arg0);
    }

    public List<Map<?, ?>> getMapList(String arg0) {
        return this.section.getMapList(arg0);
    }

    @Override
    public @Nullable <T> T getObject(@NotNull String path, @NotNull Class<T> clazz) {
        return this.section.getObject(path, clazz);
    }

    @Override
    public @Nullable <T> T getObject(@NotNull String path, @NotNull Class<T> clazz, @Nullable T def) {
        return this.section.getObject(path, clazz, def);
    }

    @Override
    public @Nullable <T extends ConfigurationSerializable> T getSerializable(@NotNull String path,
                                                                             @NotNull Class<T> clazz) {
        return this.section.getSerializable(path, clazz);
    }

    @Override
    public @Nullable <T extends ConfigurationSerializable> T getSerializable(@NotNull String path,
                                                                             @NotNull Class<T> clazz,
                                                                             @Nullable T def) {
        return this.section.getSerializable(path, clazz, def);
    }

    public String getName() {
        return this.section.getName();
    }

    public OfflinePlayer getOfflinePlayer(String arg0) {
        return this.section.getOfflinePlayer(arg0);
    }

    public OfflinePlayer getOfflinePlayer(String arg0, OfflinePlayer arg1) {
        return this.section.getOfflinePlayer(arg0, arg1);
    }

    public ConfigurationSection getParent() {
        return this.section.getParent();
    }

    public Configuration getRoot() {
        return this.section.getRoot();
    }

    public List<Short> getShortList(String arg0) {
        return this.section.getShortList(arg0);
    }

    public String getString(String arg0) {
        return replaceString(this.section.getString(arg0));
    }

    public String getString(String arg0, String arg1) {
        return replaceString(this.section.getString(arg0, arg1));
    }

    public List<String> getStringList(String arg0) {
        return (List) replaceList(this.section.getStringList(arg0));
    }

    public Map<String, Object> getValues(boolean arg0) {
        return this.section.getValues(arg0);
    }

    public Vector getVector(String arg0) {
        return this.section.getVector(arg0);
    }

    public Vector getVector(String arg0, Vector arg1) {
        return this.section.getVector(arg0);
    }

    public boolean isBoolean(String arg0) {
        return this.section.isBoolean(arg0);
    }

    public boolean isColor(String arg0) {
        return this.section.isColor(arg0);
    }

    @Override
    public @Nullable Location getLocation(@NotNull String path) {
        return this.section.getLocation(path);
    }

    @Override
    public @Nullable Location getLocation(@NotNull String path, @Nullable Location def) {
        return this.section.getLocation(path, def);
    }

    @Override
    public boolean isLocation(@NotNull String path) {
        return this.section.isLocation(path);
    }

    public boolean isConfigurationSection(String arg0) {
        return this.section.isConfigurationSection(arg0);
    }

    public boolean isDouble(String arg0) {
        return this.section.isDouble(arg0);
    }

    public boolean isInt(String arg0) {
        return this.section.isInt(arg0);
    }

    public boolean isItemStack(String arg0) {
        return this.section.isItemStack(arg0);
    }

    public boolean isList(String arg0) {
        return this.section.isList(arg0);
    }

    public boolean isLong(String arg0) {
        return this.section.isLong(arg0);
    }

    public boolean isOfflinePlayer(String arg0) {
        return this.section.isOfflinePlayer(arg0);
    }

    public boolean isSet(String arg0) {
        return this.section.isSet(arg0);
    }

    public boolean isString(String arg0) {
        return this.section.isString(arg0);
    }

    public boolean isVector(String arg0) {
        return this.section.isVector(arg0);
    }

    public void set(String arg0, Object arg1) {
        this.section.set(arg0, arg1);
    }

    private Object replaceObject(Object o) {
        if (o instanceof List) {
            return replaceList((List) o);
        }
        if (o instanceof String) {
            return replaceString((String) o);
        }

        return o;
    }

    private List<?> replaceList(List<?> list) {
        if (list != null &&
                !list.isEmpty()) {
            Object o = list.get(0);


            if (o instanceof String) {
                List<String> stringlist = (List) list;
                List<String> newlist    = new ArrayList<>();
                for (String entry : stringlist) {
                    newlist.add(replaceString(entry));
                }
                return newlist;
            }

            if (o instanceof List) {
                List<List<?>> listlist = (List) list;
                List<List<?>> newlist  = new ArrayList<>();
                for (List<?> entry : listlist) {
                    newlist.add(replaceList(entry));
                }
                return newlist;
            }
        }

        return list;
    }

    private String replaceString(String s) {
        return this.layout.transform(s, true);
    }

    private Boolean replaceBoolean(String s) {
        try {
            return Boolean.valueOf(replaceString(s));
        } catch (Exception e) {
            return Boolean.valueOf(s);
        }
    }

    private Double replaceDouble(String s) {
        try {
            return Double.parseDouble(replaceString(s));
        } catch (Exception e) {
            return 0.0D;
        }
    }

    private Integer replaceInt(String s) {
        try {
            return Integer.parseInt(replaceString(s));
        } catch (Exception e) {
            return 0;
        }
    }
}
