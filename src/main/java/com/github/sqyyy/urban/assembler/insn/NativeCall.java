package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record NativeCall(long immediate) implements Instruction {
    @Override
    public int write() {
        int opc = 0xfac00000;
        opc |= (int) immediate & 0x1fffff;
        return opc;
    }
}
