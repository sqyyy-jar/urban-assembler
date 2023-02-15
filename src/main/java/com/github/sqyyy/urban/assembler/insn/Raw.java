package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record Raw(int value) implements Instruction {
    @Override
    public int write() {
        return value;
    }
}
