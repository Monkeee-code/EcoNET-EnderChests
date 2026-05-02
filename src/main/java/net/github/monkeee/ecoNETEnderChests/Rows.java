package net.github.monkeee.ecoNETEnderChests;

public enum Rows {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    private final Integer num;

    Rows(Integer num) {
        this.num = num;
    }

    public Integer getInt() {
        return this.num;
    }
}
