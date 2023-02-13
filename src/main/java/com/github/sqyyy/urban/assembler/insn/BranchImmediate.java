package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record BranchImmediate(long immediate) implements Instruction {
    @Override
    public int write() {
        int opc = 0x18000000;
        opc |= ((int) this.immediate & 0x3ffffff);
        return opc;
    }
}
