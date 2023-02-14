package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record BranchLessEqualImmediate(int reg0, long immediate) implements Instruction {
    @Override
    public int write() {
        int opc = 0x30000000;
        opc |= reg0 & 0x1f;
        opc |= ((int) immediate & 0x3fffff) << 5;
        return opc;
    }
}
