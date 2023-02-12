package com.github.sqyyy.urban.assembler;

import java.util.Map;

public interface Instruction {
    int write();

    default int write(Map<String, Label> labels, int instructionCount, int instructionIndex) {
        return write();
    }
}
