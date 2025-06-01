package studio.magemonkey.genesis.addon.trickyshops;

import studio.magemonkey.genesis.misc.MathTools;

public class VariableCalculation {
    private final String variable;
    private final String calculation;

    public VariableCalculation(String variable, String calculation) {
        this.variable = variable;
        this.calculation = calculation;
    }

    public String transform(String s, ShopItemLayout layout) {
        return s.replace(this.variable, String.valueOf(calculate(0.0D, layout)));
    }

    public double calculate(double def, ShopItemLayout layout) {
        try {
            String s = layout.transform(this.calculation, false);
            return MathTools.calculate(s);
        } catch (Exception exception) {

            return def;
        }
    }
}
