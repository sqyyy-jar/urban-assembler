package com.github.sqyyy.urban.assembler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public sealed interface Constant permits Constant.Integer, Constant.Float, Constant.CString {
    void write(OutputStream out) throws IOException;

    int len();

    record Integer(long value) implements Constant {
        @Override
        public void write(OutputStream out) throws IOException {
            Bits.writeLong(out, value);
        }

        @Override
        public int len() {
            return 2;
        }
    }

    record Float(double value) implements Constant {
        @Override
        public void write(OutputStream out) throws IOException {
            Bits.writeLong(out, Double.doubleToRawLongBits(value));
        }

        @Override
        public int len() {
            return 2;
        }
    }

    final class CString implements Constant {
        private final byte[] bytes;

        public CString(String value) {
            this.bytes = value.getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public void write(OutputStream out) throws IOException {
            out.write(bytes);
            switch (bytes.length % 4) {
                case 0 -> {
                    out.write(0);
                    out.write(0);
                    out.write(0);
                    out.write(0);
                }
                case 1 -> {
                    out.write(0);
                    out.write(0);
                    out.write(0);
                }
                case 2 -> {
                    out.write(0);
                    out.write(0);
                }
                case 3 -> out.write(0);
            }
        }

        @Override
        public int len() {
            return bytes.length / 4 + 1;
        }
    }
}
