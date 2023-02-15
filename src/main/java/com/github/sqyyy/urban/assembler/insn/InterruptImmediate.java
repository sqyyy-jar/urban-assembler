package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record InterruptImmediate(long immediate) implements Instruction {
    @Override
    public int write() {
        int opc = 0xf9400000;
        opc |= (int) immediate & 0xffff;
        return opc;
    }
}
