package at.hitm.hitmplugin.api;

public enum EnumRequestType {

    HELLOWORLD, USERINFO(true), CUSTOM(true), STATS, ONLINE_PLAYERS, WHITELIST(true), CHAT(true);

    private EnumRequestType() {
        this.id = this.name().toLowerCase();
        this.requiresData = false;
    }

    private EnumRequestType(boolean requiresData) {
        this.id = this.name().toLowerCase();
        this.requiresData = requiresData;
    }

    private final String id;
    private final boolean requiresData;

    public String getId() {
        return id;
    }

    public boolean requiresData() {
        return requiresData;
    }

    public static EnumRequestType parse(String input) {
        if (input == null) return null;
        try {
            return valueOf(input.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}