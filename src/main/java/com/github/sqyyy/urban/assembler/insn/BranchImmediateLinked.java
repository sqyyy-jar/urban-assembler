package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record BranchImmediateLinked(long immediate) implements Instruction {
    @Override
    public int write() {
        int opc = 0x34000000;
        opc |= ((int) this.immediate & 0x3ffffff);
        return opc;
    }
}
