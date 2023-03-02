package com.github.sqyyy.urban.assembler;

import com.github.sqyyy.urban.assembler.model.Function;
import com.github.sqyyy.urban.assembler.model.Module;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Bootstrap {
    public static void main(String[] args) throws IOException {
        var mod = new Module();
        mod.func(new Function(mod, "main").halt())
            .func(new Function(mod, "test").halt());
        try (var binary = new RandomAccessFile("", "")) {
            // Byte magic
            binary.write(new byte[]{0, 'u', 'r', 'b'});
        }
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