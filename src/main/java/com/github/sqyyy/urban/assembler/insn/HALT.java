package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public class HALT implements Instruction {

    public HALT() {
    }

    @Override
    public int write() {
        int opc = 0b010101_00000000000000000000000000;
        return opc;
    }
}
