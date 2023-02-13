package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;

public class Nop implements Instruction {
    @Override
    public int write() {
        return 0x84000000;
    }
}
