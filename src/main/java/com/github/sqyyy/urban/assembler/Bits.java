package com.github.sqyyy.urban.assembler;

import java.io.IOException;
import java.io.OutputStream;

public class Bits {
    public static void writeInt(OutputStream write, int value) throws IOException {
        write.write(value & 0xFF);
        write.write((value & 0xFF00) >> 8);
        write.write((value & 0xFF00_00) >> 16);
        write.write((value & 0xFF00_0000) >> 24);
    }

    public static void writeLong(OutputStream write, long value) throws IOException {
        write.write((int) (value & 0xFF));
        write.write((int) ((value & 0xFF00) >> 8));
        write.write((int) ((value & 0xFF00_00) >> 16));
        write.write((int) ((value & 0xFF00_0000L) >> 24));
        write.write((int) ((value & 0xFF00_0000_00L) >> 32));
        write.write((int) ((value & 0xFF00_0000_0000L) >> 40));
        write.write((int) ((value & 0xFF00_0000_0000_00L) >> 48));
        write.write((int) ((value & 0xFF00_0000_0000_0000L) >> 56));
    }
}
