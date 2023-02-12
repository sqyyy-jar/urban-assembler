package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public class STR_IMMEDIATE implements Instruction {
    private final int reg0;
    private final long immediate;

    public STR_IMMEDIATE(int reg0, long immediate) {
        this.reg0 = reg0;
        this.immediate = immediate;
    }

    @Override
    public int write() {
        int opc = 0b101000_00000000000000000000000000;
        opc |= (this.reg0 & 0x1F) << 0;
        opc |= (((int) this.immediate) & 0x1fffff) << 5;
        return opc;
    }
}