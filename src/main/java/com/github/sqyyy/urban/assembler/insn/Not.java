package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record Not(int reg0, int reg1) implements Instruction {
    @Override
    public int write() {
        int opc = 0x88000000;
        opc |= (this.reg0 & 0x1F) << 5;
        opc |= this.reg1 & 0x1F;
        return opc;
    }
}
