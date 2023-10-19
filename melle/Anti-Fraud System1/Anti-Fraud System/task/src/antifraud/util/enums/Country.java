package antifraud.util.enums;

public enum Country {
    EAP("East Asia and Pacific"),
    ECA("Europe and Central Asia"),
    HIC("High-Income countries"),
    LAC("Latin America and the Caribbean"),
    MENA("The Middle East and North Africa"),
    SA("South Asia"),
    SSA("Sub-Saharan Africa");

    private final String description;

    Country(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

