package com.github.sqyyy.urban.assembler;

import com.github.sqyyy.urban.assembler.model.Function;
import com.github.sqyyy.urban.assembler.model.Module;
import com.github.sqyyy.urban.assembler.util.BinaryExporter;

import java.io.IOException;
import java.nio.file.Path;

import static com.github.sqyyy.urban.assembler.model.Register.R0;
import static com.github.sqyyy.urban.assembler.model.Register.R1;

public class Bootstrap {
    public static void main(String[] args) throws IOException {
        var mod = new Module();
        mod.func(Function.of(mod, "main")
                .constant("x", 42.0)
                .ldr(R0, -2)
                .fti(R1, R0)
                .branchL("dump")
                .halt())
            .func(Function.of(mod, "dump")
                .raw(-1)
                .halt());
        new BinaryExporter().exportExecutable(Path.of("test.bin"), mod, 0);
        //        var asm = new ModuleAssembler();
        //        asm.addModule(Modules.MATH);
        //        asm.labelConst("num");
        //        asm.constFloat(Math.PI);
        //        asm.labelConst("buf");
        //        for (int i = 0; i < 32; i++) {
        //            asm.constInt(0L);
        //        }
        //
        //        asm.ldr(R0.num(), "num");
        //        asm.branchLinked("math:sqrt(f64)f64");
        //
        //        asm.movAbs(R1.num(), "buf");
        //        asm.mov(R2.num(), 256L);
        //        asm.vcall(VC_FLOAT_TO_STRING);
        //
        //        asm.mov(R2.num(), R0.num());
        //        asm.mov(R0.num(), 1L);
        //        asm.movAbs(R1.num(), "buf");
        //        asm.interrupt(INT_WRITE);
        //
        //        asm.mov(R0.num(), 0xaL);
        //        asm.movAbs(R1.num(), "buf");
        //        asm.strByte(R1.num(), R0.num(), 0L);
        //
        //        asm.mov(R0.num(), 1L);
        //        asm.movAbs(R1.num(), "buf");
        //        asm.mov(R2.num(), 1L);
        //        asm.interrupt(INT_WRITE);
        //
        //        asm.halt();
        //
        //        var fio = Files.newOutputStream(Path.of("test.bin"));
        //        asm.write(fio);
    }
}