package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record BranchGreaterImmediate(int reg0, long immediate) implements Instruction {
    @Override
    public int write() {
        int opc = 0x24000000;
        opc |= this.reg0 & 0x1F;
        opc |= ((int) this.immediate & 0x1fffff) << 5;
        return opc;
    }
}
