package com.github.sqyyy.urban.assembler.model;

import com.github.sqyyy.urban.assembler.util.Bits;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public sealed interface Constant<T>
    permits Constant.AbsoluteLabelAddress, Constant.AbsoluteModuleLabelAddress, Constant.Buffer, Constant.CString, Constant.Float,
    Constant.Integer {
    void write(T t, OutputStream out) throws IOException;

    long len();

    record Integer<T>(long value) implements Constant<T> {
        @Override
        public void write(T t, OutputStream out) throws IOException {
            Bits.writeLong(out, value);
        }

        @Override
        public long len() {
            return 8;
        }
    }

    record Float<T>(double value) implements Constant<T> {
        @Override
        public void write(T t, OutputStream out) throws IOException {
            Bits.writeLong(out, Double.doubleToRawLongBits(value));
        }

        @Override
        public long len() {
            return 8;
        }
    }

    final class CString<T> implements Constant<T> {
        private final byte[] bytes;

        public CString(String value) {
            this.bytes = value.getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public void write(T t, OutputStream out) throws IOException {
            out.write(bytes);
            out.write(0);
        }

        @Override
        public long len() {
            return bytes.length + 1;
        }
    }

    record Buffer<T>(long capacity) implements Constant<T> {
        @Override
        public void write(T t, OutputStream out) throws IOException {
            for (long i = 0; i < capacity; i++) {
                out.write(0);
            }
        }

        @Override
        public long len() {
            return capacity;
        }
    }

    record AbsoluteModuleLabelAddress(String label) implements Constant<Module> {
        @Override
        public void write(Module module, OutputStream out) throws IOException {
            Bits.writeLong(out, module.getOffset(label)
                .address(module));
        }

        @Override
        public long len() {
            return 8;
        }
    }

    record AbsoluteLabelAddress(String label) implements Constant<Function> {
        @Override
        public void write(Function function, OutputStream out) throws IOException {
            Bits.writeLong(out, function.getOffset(label)
                .address(function));
        }

        @Override
        public long len() {
            return 8;
        }
    }
}
