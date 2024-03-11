package model;

import java.util.Arrays;

public enum ToyType {
    F(0),
    M(1),
    U(2);

    private final int value;

    ToyType(int value) {
        this.value = value;
    }

    public static ToyType getTypeByValue(int value){
        return Arrays.stream(ToyType.values()).filter(e->e.value==value).findFirst().orElseThrow();
    }
}
