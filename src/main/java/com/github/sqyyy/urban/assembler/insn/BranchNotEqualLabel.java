package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;
import com.github.sqyyy.urban.assembler.Label;

import java.util.Map;
import java.util.Objects;

public record BranchNotEqualLabel(int reg0, String label) implements Instruction {
    @Override
    public int write(Map<String, Label> labels, int instructionCount, int instructionIndex) {
        var target = Objects.requireNonNull(labels.get(label));
        int opc = 0x30000000;
        if (target.constant()) {
            opc |= ((instructionCount + target.index() - instructionIndex) & 0x1fffff) << 5;
        } else {
            opc |= (target.index() - instructionIndex & 0x1fffff) << 5;
        }
        opc |= reg0 & 0x1F;
        return opc;
    }
}
