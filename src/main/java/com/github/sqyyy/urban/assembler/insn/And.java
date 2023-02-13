package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record And(int reg0, int reg1, int reg2) implements Instruction {
    @Override
    public int write() {
        int opc = 0x14000000;
        opc |= (this.reg0 & 0x1F) << 10;
        opc |= (this.reg1 & 0x1F) << 5;
        opc |= this.reg2 & 0x1F;
        return opc;
    }
}
