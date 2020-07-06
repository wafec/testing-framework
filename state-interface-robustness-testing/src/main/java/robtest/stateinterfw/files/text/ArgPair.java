package robtest.stateinterfw.files.text;

public class ArgPair {
    private String name;
    private String value;
    private String modifier;

    public ArgPair() { }

    public ArgPair(String name, String value) {
        this(name, value, null);
    }

    public ArgPair(String name, String value, String modifier) {
        this.name = name;
        this.value = value;
        this.modifier = modifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    private static String decodeValue(String value) {
        return value.replace("&doublecolon;", ":").replace("&eq;", "='")
                .replace("&2colon;", ":");
    }

    public static ArgPair parse(String text) {
        var pair = text.trim().split("=");
        if (pair.length == 2) {
                if (!pair[1].contains(":"))
                    return new ArgPair(pair[0], decodeValue(pair[1]));
                else {
                    var valueModifier = pair[1].split(":");
                    return new ArgPair(pair[0], decodeValue(valueModifier[0]), valueModifier[1]);
                }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return String.format("<ArgPair(name=%s, value=%s, modifier=%s)>", name, value, modifier);
    }
}
