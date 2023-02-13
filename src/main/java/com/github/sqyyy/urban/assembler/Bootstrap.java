package com.github.sqyyy.urban.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Bootstrap {
    public static void main(String[] args) throws IOException {
        var haltModule = new ModuleAssembler();
        haltModule.nop();
        haltModule.labelCode("halt:halt");
        haltModule.halt();
        var haltModuleC = AssembledModule.compile(haltModule);

        var asm = new ModuleAssembler();
        asm.addModule(haltModuleC);
        //        asm.labelConst("&msg");
        //        asm.constAbsLabel("msg");
        //        asm.labelConst("msg");
        //        asm.constCStr("Hello world!\n");

        asm.nop();
        asm.nop();
        asm.nop();
        asm.branch("halt:halt");
        asm.nop();
        asm.nop();
        asm.nop();
        asm.constInt(0x8899AABBCCDDEEFFL);
        //        asm.labelCode("loop");
        //        asm.mov(0, 2L);
        //        asm.ldr(1, "&msg");
        ////        asm.movAbs(1, "msg");
        //        asm.mov(2, 13L);
        //        asm.interrupt(1L);
        //        asm.branch("loop");
        //        asm.halt();

        var fio = Files.newOutputStream(Path.of("test.bin"));
        asm.write(fio);
    }
}