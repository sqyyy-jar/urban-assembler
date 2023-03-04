package com.github.sqyyy.urban.assembler.util;

import com.github.sqyyy.urban.assembler.model.Module;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class BinaryExporter {
    public static final int EXECUTABLE = 0x1;
    private static final byte[] BYTE_MAGIC = {0, 'u', 'r', 'b'};

    public void exportExecutable(Path binaryPath, Module module, int flags) throws IOException {
        Files.deleteIfExists(binaryPath);
        Objects.requireNonNull(module);
        try (var file = new RandomAccessFile(binaryPath.toFile(), "rw")) {
            file.write(BYTE_MAGIC);
            Bits.writeInt(file, flags | EXECUTABLE);
            Bits.writeLong(file, 0L);
            var entrypointOffset = module.assemble(Channels.newOutputStream(file.getChannel()));
            if (entrypointOffset == -1) {
                throw new RuntimeException("Module does not have main function");
            }
            file.seek(8);
            Bits.writeLong(file, entrypointOffset);
        }
    }

    public void exportStaticLib(Path binaryPath, Module module, int flags) throws IOException {
        Files.deleteIfExists(binaryPath);
        Objects.requireNonNull(module);
        try (var file = new RandomAccessFile(binaryPath.toFile(), "rw")) {
            file.write(BYTE_MAGIC);
            Bits.writeInt(file, flags & ~EXECUTABLE);
            module.assemble(Channels.newOutputStream(file.getChannel()));
        }
    }
}
