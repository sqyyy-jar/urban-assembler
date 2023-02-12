package com.github.sqyyy.urban.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.sqyyy.urban.assembler.Builder.*;

public class Bootstrap {
    public static void main(String[] args) throws IOException {
        ConstLabel("msg");
        CStringConstant("Hello world!\n");

        CodeLabel("loop");
        Mov(0, 1L);
        MovAbs(1, "msg");
        Mov(2, 13L);
        Interrupt(1L);
        B("loop");
        Halt();

        var fio = Files.newOutputStream(Path.of("test.bin"));
        write(fio);
    }
}