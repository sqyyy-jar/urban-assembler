package com.github.sqyyy.urban.assembler.model;

public record RawInstruction(int num) implements Instruction {
    @Override
    public int write(Function function, int instructionIndex) {
        return num;
    }
}
