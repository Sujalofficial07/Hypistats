package net.sujal.stats.shared;

public class StatModifier {
    private final String id;
    private final double value;
    private final ModifierType type;
    private final StatSource source;

    public enum ModifierType { FLAT, PERCENT }

    public StatModifier(String id, double value, ModifierType type, StatSource source) {
        this.id = id;
        this.value = value;
        this.type = type;
        this.source = source;
    }

    public String getId() { return id; }
    public double getValue() { return value; }
    public ModifierType getType() { return type; }
    public StatSource getSource() { return source; }
}
