package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public class BRL implements Instruction {
    private final int reg0;

    public BRL(int reg0) {
        this.reg0 = reg0;
    }

    @Override
    public int write() {
        int opc = 0b001111_00000000000000000000000000;
        opc |= (this.reg0 & 0x1F) << 0;
        return opc;
    }
}
