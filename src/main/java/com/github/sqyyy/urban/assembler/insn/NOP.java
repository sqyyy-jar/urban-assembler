package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public class NOP implements Instruction {

    public NOP() {
    }

    @Override
    public int write() {
        int opc = 0b100001_00000000000000000000000000;
        return opc;
    }
}
