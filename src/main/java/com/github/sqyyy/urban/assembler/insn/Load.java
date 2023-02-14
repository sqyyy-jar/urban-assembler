package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record Load(int reg0, int reg1) implements Instruction {
    @Override
    public int write() {
        int opc = 0xf9600000;
        opc |= (reg0 & 0x1f) << 5;
        opc |= reg1 & 0x1f;
        return opc;
    }
}
