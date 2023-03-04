package com.github.sqyyy.urban.assembler.model;

import com.github.sqyyy.urban.assembler.util.OpCodes;

public record BranchLabelInstruction(String label, boolean linked) implements Instruction {
    @Override
    public int write(Function function, int instructionIndex) {
        var offset = function.getOffset(label);
        long selfAddress;
        long targetAddress;
        if (offset != null) {
            selfAddress = function.constantsLen() + instructionIndex * 4L;
            targetAddress = offset.address(function);
        } else {
            var moduleOffset = function.getParent()
                .getOffset(label);
            selfAddress = function.getParent()
                .functionOffset(function) + instructionIndex * 4L;
            targetAddress = moduleOffset.address(function.getParent());
        }
        long offsetAddress = targetAddress - selfAddress;
        if (offsetAddress % 4 != 0) {
            throw new RuntimeException("Non aligned branch");
        }
        return (linked ? OpCodes.L0_BRANCH_L : OpCodes.L0_BRANCH) | ((int) offsetAddress / 4 & 0x7ffffff);
    }
}
