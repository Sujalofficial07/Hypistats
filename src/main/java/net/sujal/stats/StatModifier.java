package net.sujal.stats;

public record StatModifier(String id, double value, ModifierType type) {
    public enum ModifierType { FLAT, PERCENT }
}
