package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;
import com.github.sqyyy.urban.assembler.Label;

import java.util.Map;
import java.util.Objects;

public record B_LABEL(String label) implements Instruction {
    @Override
    public int write() {
        return 0;
    }

    @Override
    public int write(Map<String, Label> labels, int instructionCount, int instructionIndex) {
        var target = Objects.requireNonNull(labels.get(label));
        int opc = 0b000110_00000000000000000000000000;
        if (target.constant()) {
            opc |= (((instructionCount + target.index()) - instructionIndex) & 0x3ffffff);
        } else {
            opc |= ((target.index() - instructionIndex) & 0x3ffffff);
        }
        return opc;
    }
}
