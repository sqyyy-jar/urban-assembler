package com.github.sqyyy.urban.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Bootstrap {
    public static void main(String[] args) throws IOException {
        var asm = new ModuleAssembler();
        asm.constLabel("msg");
        asm.constCStr("Hello world!\n");

        asm.codeLabel("loop");
        asm.mov(0, 1L);
        asm.movAbs(1, "msg");
        asm.mov(2, 13L);
        asm.interrupt(1L);
        asm.branch("loop");
        asm.halt();

        var fio = Files.newOutputStream(Path.of("test.bin"));
        asm.write(fio);
    }
}