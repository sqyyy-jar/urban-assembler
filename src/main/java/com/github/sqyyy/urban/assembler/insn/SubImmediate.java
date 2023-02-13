package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record SubImmediate(int reg0, int reg1, long immediate) implements Instruction {
    @Override
    public int write() {
        int opc = 0xa8000000;
        opc |= (this.reg0 & 0x1F) << 5;
        opc |= this.reg1 & 0x1F;
        opc |= ((int) this.immediate & 0xffff) << 10;
        return opc;
    }
}
