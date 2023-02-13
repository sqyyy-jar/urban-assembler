package com.github.sqyyy.urban.assembler;

public interface Instruction {
    default int write() {
        throw new RuntimeException("Unimplemented");
    }

    default int write(ModuleAssembler module, int instructionIndex) {
        return write();
    }
}
