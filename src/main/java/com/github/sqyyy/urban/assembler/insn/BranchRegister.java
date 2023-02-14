package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record BranchRegister(int reg0) implements Instruction {
    @Override
    public int write() {
        int opc = 0xf8800000;
        opc |= reg0 & 0x1f;
        return opc;
    }
}
