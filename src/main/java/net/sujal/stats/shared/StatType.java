package net.sujal.stats.shared;

public enum StatType {
    // COMBAT / SURVIVAL
    HEALTH(true, 100.0),
    DEFENSE(false, 0.0),
    STRENGTH(false, 0.0),
    CRIT_CHANCE(false, 30.0),
    CRIT_DAMAGE(false, 50.0),
    BONUS_ATTACK_SPEED(false, 0.0),
    INTELLIGENCE(false, 0.0),
    ABILITY_DAMAGE(false, 0.0),
    TRUE_DEFENSE(false, 0.0),
    FEROCITY(false, 0.0),
    HEALTH_REGEN(false, 100.0),
    VITALITY(false, 100.0),
    MENDING(false, 100.0),
    DAMAGE(false, 0.0),
    ABSORPTION(true, 0.0),
    MANA(true, 100.0),
    MANA_REGEN(false, 100.0),
    OVERFLOW(true, 0.0),
    HEARTS(false, 0.0),

    // UTILITY
    SPEED(false, 100.0),
    MAGIC_FIND(false, 0.0),
    PET_LUCK(false, 0.0),
    SEA_CREATURE_CHANCE(false, 0.0),
    SWING_RANGE(false, 3.0),
    TREASURE_CHANCE(false, 0.0),
    DOUBLE_HOOK_CHANCE(false, 0.0),
    PULL(false, 0.0),
    RESPIRATION(false, 0.0),

    // GATHERING / MINING / FARMING / FORAGING / FISHING
    MINING_SPEED(false, 0.0),
    MINING_FORTUNE(false, 0.0),
    MINING_SPREAD(false, 0.0),
    GEMSTONE_SPREAD(false, 0.0),
    PRISTINE(false, 0.0),
    BREAKING_POWER(false, 0.0),
    FARMING_FORTUNE(false, 0.0),
    FORAGING_FORTUNE(false, 0.0),
    FISHING_SPEED(false, 0.0),
    
    // WISDOM
    FISHING_WISDOM(false, 0.0),
    FARMING_WISDOM(false, 0.0),
    FORAGING_WISDOM(false, 0.0),
    MINING_WISDOM(false, 0.0),
    RUNECRAFTING_WISDOM(false, 0.0),
    ALCHEMY_WISDOM(false, 0.0),
    COMBAT_WISDOM(false, 0.0),
    ENCHANTING_WISDOM(false, 0.0),
    CARPENTRY_WISDOM(false, 0.0),
    TAMING_WISDOM(false, 0.0),
    SOCIAL_WISDOM(false, 0.0),
    HUNTING_WISDOM(false, 0.0),
    
    // FORTUNE EXTRA
    HUNTER_FORTUNE(false, 0.0),
    FIG_FORTUNE(false, 0.0),
    MANGROVE_FORTUNE(false, 0.0),

    // ENVIRONMENT / STATUS
    COLD(false, 0.0),
    COLD_RESISTANCE(false, 0.0),
    HEAT(false, 0.0),
    HEAT_RESISTANCE(false, 0.0),
    PRESSURE_RESISTANCE(false, 0.0),
    FEAR(false, 0.0),
    FUEL(false, 0.0),
    RIFT_DAMAGE(false, 0.0),
    RIFT_TIME(false, 0.0),

    // SPECIAL / EXTRA
    TRUE_DAMAGE(false, 0.0),
    SWEEP(false, 0.0),
    BONUS_PEST_CHANCE(false, 0.0);

    private final boolean isPool;
    private final double defaultValue;

    StatType(boolean isPool, double defaultValue) {
        this.isPool = isPool;
        this.defaultValue = defaultValue;
    }

    public boolean isPool() {
        return isPool;
    }

    public double getDefaultValue() {
        return defaultValue;
    }
}
