package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public class NOT implements Instruction {
    private final int reg0;
    private final int reg1;

    public NOT(int reg0, int reg1) {
        this.reg0 = reg0;
        this.reg1 = reg1;
    }

    @Override
    public int write() {
        int opc = 0b100010_00000000000000000000000000;
        opc |= (this.reg0 & 0x1F) << 5;
        opc |= (this.reg1 & 0x1F) << 0;
        return opc;
    }
}
