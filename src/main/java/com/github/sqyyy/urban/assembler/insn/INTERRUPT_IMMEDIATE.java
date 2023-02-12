package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public class INTERRUPT_IMMEDIATE implements Instruction {
    private final long immediate;

    public INTERRUPT_IMMEDIATE(long immediate) {
        this.immediate = immediate;
    }

    @Override
    public int write() {
        int opc = 0b010110_00000000000000000000000000;
        opc |= (((int) this.immediate) & 0x3ffffff) << 0;
        return opc;
    }
}
