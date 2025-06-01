package studio.magemonkey.genesis.addon.trickyshops;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class VariableLine {
    @Getter
    private final String              path;
    @Getter
    private final String[]            objects;
    private final Map<String, String> currentValues = new HashMap<>();

    public VariableLine(String path, String s) {
        this(path, s.split(":"));
    }

    public VariableLine(String path, String[] objects) {
        this.path = path;
        this.objects = objects;
    }

    public void loadCurrentValues(ConfigurationSection config) {
        this.currentValues.clear();
        if (config.contains(getPath())) {
            String   s     = config.getString(getPath());
            String[] parts = s.split(":");
            int      i     = 0;
            byte     b;
            int      j;
            String[] arrayOfString1;
            for (j = (arrayOfString1 = getObjects()).length, b = 0; b < j; ) {
                String variable = arrayOfString1[b];
                if (parts.length > i) {
                    this.currentValues.put(variable, parts[i]);
                }
                i++;
                b++;
            }

        }
    }

    public String transform(String output) {
        byte     b;
        int      i;
        String[] arrayOfString;
        for (i = (arrayOfString = getObjects()).length, b = 0; b < i; ) {
            String variable = arrayOfString[b];
            if (output.contains(variable) && this.currentValues.containsKey(variable))
                output = output.replace(variable, this.currentValues.get(variable));
            b++;
        }

        return output;
    }
}
