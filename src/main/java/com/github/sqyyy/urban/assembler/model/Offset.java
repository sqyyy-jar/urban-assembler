package com.github.sqyyy.urban.assembler.model;

public interface Offset<T> {
    long address(T t);

    record ModuleConstOffset(int constIndex) implements Offset<Module> {
        @Override
        public long address(Module module) {
            return module.includedModulesLen() + module.includedFunctionsLen() + module.constantOffset(constIndex);
        }
    }

    record ModuleFunctionOffset(int functionIndex) implements Offset<Module> {
        @Override
        public long address(Module module) {
            return module.includedModulesLen() + module.includedFunctionsLen() + module.constantsLen() +
                module.functionOffset(functionIndex);
        }
    }

    record FunctionConstOffset(int constIndex) implements Offset<Function> {
        @Override
        public long address(Function function) {
            return function.constantOffset(constIndex);
        }
    }

    record FunctionCodeOffset(int instructionIndex) implements Offset<Function> {
        @Override
        public long address(Function function) {
            return function.constantsLen() + instructionIndex * 4L;
        }
    }
}
