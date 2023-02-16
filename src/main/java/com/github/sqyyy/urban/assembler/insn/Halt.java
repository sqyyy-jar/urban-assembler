package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public record Halt() implements Instruction {
    @Override
    public int write() {
        return 0xf9800000;
    }
}
