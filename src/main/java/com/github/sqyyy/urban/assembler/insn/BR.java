package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public class BR implements Instruction {
    private final int reg0;

    public BR(int reg0) {
        this.reg0 = reg0;
    }

    @Override
    public int write() {
        int opc = 0b001110_00000000000000000000000000;
        opc |= (this.reg0 & 0x1F) << 0;
        return opc;
    }
}
