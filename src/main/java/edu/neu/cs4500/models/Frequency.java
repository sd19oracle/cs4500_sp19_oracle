package edu.neu.cs4500.models;

public enum Frequency {
    ONETIME ("Onetime"),
    HOURLY ("Hourly"),
    DAILY ("Daily"),
    BIWEEKLY ("Biweekly"),
    MONTHLY ("Monthly"),
    YEARLY ("Yearly"),
    WEEKDAY ("Weekday"), 
    WEEKEND ("Weekend"),
    EMERGENCY ("Emergency"),
    HOLIDAY ("Holiday");

    private final String name;

    private Frequency(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
