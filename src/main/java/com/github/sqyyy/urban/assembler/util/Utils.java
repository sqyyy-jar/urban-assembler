package com.github.sqyyy.urban.assembler.util;

public class Utils {
    private Utils() {
    }

    public static long align(long value, long alignment) {
        if (value % alignment == 0L) {
            return value;
        } else {
            return value - value % alignment + alignment;
        }
    }

    public static long alignment(long value, long alignment) {
        if (value % alignment == 0L) {
            return 0L;
        } else {
            return alignment - value % alignment;
        }
    }
}
