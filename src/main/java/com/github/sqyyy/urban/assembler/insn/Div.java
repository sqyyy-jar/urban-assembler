package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record Div(int reg0, int reg1, int reg2) implements Instruction {
    @Override
    public int write() {
        int opc = 0xf9200000;
        opc |= (reg0 & 0x1f) << 10;
        opc |= (reg1 & 0x1f) << 5;
        opc |= reg2 & 0x1f;
        return opc;
    }
}
