package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public class ADD_IMMEDIATE implements Instruction {
    private final int reg0;
    private final int reg1;
    private final long immediate;

    public ADD_IMMEDIATE(int reg0, int reg1, long immediate) {
        this.reg0 = reg0;
        this.reg1 = reg1;
        this.immediate = immediate;
    }

    @Override
    public int write() {
        int opc = 0b000001_00000000000000000000000000;
        opc |= (this.reg0 & 0x1F) << 5;
        opc |= (this.reg1 & 0x1F) << 0;
        opc |= (((int) this.immediate) & 0xffff) << 10;
        return opc;
    }
}
