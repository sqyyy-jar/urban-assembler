package com.github.sqyyy.urban.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Bootstrap {
    public static void main(String[] args) throws IOException {
        var math = new ModuleAssembler();
        math.labelConst("-math:2.0");
        math.constFloat(2.);
        math.labelCode("math:sqrt");
        // X0 = S
        // X1 = tmp
        // X2 = xn
        // X3 = 2
        math.ldr(3, "-math:2.0");
        math.divf(2, 0, 3);
        for (int i = 0; i < 16; i++) {
            math.divf(1, 0, 2);
            math.addf(1, 2, 1);
            math.divf(2, 1, 3);
        }
        math.mov(0, 2);
        math.branch(30);
        var cMath = AssembledModule.compile(math);

        var asm = new ModuleAssembler();
        asm.addModule(cMath);
        asm.labelConst("num");
        asm.constFloat(2.0);
        asm.labelConst("buf");
        for (int i = 0; i < 32; i++) {
            asm.constInt(0x4141414141414141L);
        }

        asm.ldr(0, "num");
        asm.branchLinked("math:sqrt");

        asm.movAbs(1, "buf");
        asm.mov(2, 256L);
        asm.vcall(0x4202L); // float.toString

        asm.mov(2, 0);
        asm.mov(0, 1L);
        asm.movAbs(1, "buf");
        asm.interrupt(1L); // write
        asm.halt();

        var fio = Files.newOutputStream(Path.of("test.bin"));
        asm.write(fio);
    }
}