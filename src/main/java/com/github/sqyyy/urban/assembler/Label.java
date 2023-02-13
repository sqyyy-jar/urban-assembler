package com.github.sqyyy.urban.assembler;

public sealed interface Label permits Label.Assembler, Label.Compiled {
    int absoluteByteAddress(ModuleAssembler module);

    record Assembler(int dwordIndex, boolean constant) implements Label {
        @Override
        public int absoluteByteAddress(ModuleAssembler module) {
            return constant ? module.countInstructions() * 4 + dwordIndex * 4 : dwordIndex * 4;
        }
    }

    record Compiled(int module, int dwordOffset) implements Label {
        @Override
        public int absoluteByteAddress(ModuleAssembler assembler) {
            return assembler.countInstructions() * 4 + assembler.countConstantBytes() + assembler.moduleOffsetBytes(module) +
                dwordOffset * 4;
        }
    }
}
