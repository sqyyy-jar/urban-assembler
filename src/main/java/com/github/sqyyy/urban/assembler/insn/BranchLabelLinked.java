package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;
import com.github.sqyyy.urban.assembler.ModuleAssembler;

import java.util.Objects;

public record BranchLabelLinked(String label) implements Instruction {
    @Override
    public int write(ModuleAssembler module, int instructionIndex) {
        var target = Objects.requireNonNull(module.getLabels()
            .get(label));
        int opc = 0x48000000;
        opc |= (target.absoluteByteAddress(module) / 4 - instructionIndex & 0x3ffffff);
        return opc;
    }
}
