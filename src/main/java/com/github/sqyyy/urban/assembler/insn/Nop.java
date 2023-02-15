package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record Nop() implements Instruction {
    @Override
    public int write() {
        return 0xfa800000;
    }
}
