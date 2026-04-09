package ru.yandex.practicum.sleeptracker;

public enum Chronotype {
    OWL("Сова"),
    LARK("Жаворонок"),
    DOVE("Голубь");

    private final String name;

    Chronotype(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
