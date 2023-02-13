package com.github.sqyyy.urban.assembler;

import java.util.Map;

public interface Instruction {
    default int write() {
        throw new RuntimeException("Unimplemented");
    }

    default int write(Map<String, Label> labels, int instructionCount, int instructionIndex) {
        return write();
    }
}
