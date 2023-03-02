package com.github.sqyyy.urban.assembler.util;

public class Utils {
    private Utils() {
    }

    public static long align(long value, long alignment) {
        if (value % alignment == 0L) {
            return value;
        } else {
            return value - (value % alignment) + alignment;
        }
    }
}
