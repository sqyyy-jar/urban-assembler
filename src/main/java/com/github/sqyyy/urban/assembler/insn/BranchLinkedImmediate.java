package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record BranchLinkedImmediate(long immediate) implements Instruction {
    @Override
    public int write() {
        int opc = 0x48000000;
        opc |= (int) immediate & 0x1f;

        return opc;
    }
}