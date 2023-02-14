package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record Halt() implements Instruction {
    @Override
    public int write() {
        int opc = 0xf9200000;

        return opc;
    }
}
