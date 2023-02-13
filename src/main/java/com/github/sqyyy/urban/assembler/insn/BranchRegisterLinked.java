package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record BranchRegisterLinked(int reg0) implements Instruction {
    @Override
    public int write() {
        int opc = 0x3c000000;
        opc |= this.reg0 & 0x1F;
        return opc;
    }
}
