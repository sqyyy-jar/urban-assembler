package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record MulImmediate(int reg0, int reg1, long immediate) implements Instruction {
    @Override
    public int write() {
        int opc = 0x78000000;
        opc |= (reg0 & 0x1f) << 5;
        opc |= reg1 & 0x1f;
        opc |= ((int) immediate & 0x1ffff) << 10;
        return opc;
    }
}
