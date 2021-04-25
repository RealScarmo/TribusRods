package se.scarmo.tribusrods.enchant;

public enum RodEnchant {

    SEVEN_SEAS(10, "SS", "§b§lSEVEN SEAS", 10000, "§7Lockar fisk snabbare"),
    TOKEN_BOOST(5, "TF", "§e§lTOKEN CATCHER", 10000, "§7Ha större chans att fånga tokens när du fiskar"),
    SPAWNER_FINDER(5, "SF", "§d§lSPAWNER CATCHER", 10000, "§7Ha större chans att fånga spawners när du fiskar");

    int maxLevel;
    String tag;
    String loreString;
    String desc;
    int costPerUpgrade;

    RodEnchant(int maxLevel, String tag, String loreString, int costPerUpgrade, String desc) {

        this.maxLevel = maxLevel;
        this.tag = tag;
        this.loreString = loreString;
        this.costPerUpgrade = costPerUpgrade;
        this.desc = desc;

    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getTag() {
        return tag;
    }

    public String getLoreString() {
        return loreString;
    }

    public int getCostPerUpgrade() {
        return costPerUpgrade;
    }

    public String getDesc() {
        return desc;
    }

}
