package com.github.sqyyy.urban.assembler.insn;

import com.github.sqyyy.urban.assembler.Instruction;
import com.github.sqyyy.urban.assembler.ModuleAssembler;

import java.util.Objects;

public record BranchLabel(String label) implements Instruction {
    @Override
    public int write(ModuleAssembler module, int instructionIndex) {
        var target = Objects.requireNonNull(module.getLabels()
            .get(label));
        int opc = 0x10000000;
        var a = target.absoluteByteAddress(module) / 4;
        opc |= (a - instructionIndex & 0x3ffffff);
        return opc;
    }
}
