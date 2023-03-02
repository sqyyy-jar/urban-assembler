package com.github.sqyyy.urban.assembler.model;

import com.github.sqyyy.urban.assembler.model.assembled.AssembledFunction;
import com.github.sqyyy.urban.assembler.model.assembled.AssembledModule;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Module {
    private final Map<String, Offset<Module>> offsetTable = new HashMap<>();
    private final List<AssembledModule> includedModules = new ArrayList<>();
    private final List<AssembledFunction> includedFunctions = new ArrayList<>();
    private final List<Constant<Module>> constants = new ArrayList<>();
    private final List<Function> functions = new ArrayList<>();

    public Module use(AssembledModule module) {
        Objects.requireNonNull(module);
        includedModules.add(module);
        return this;
    }

    public Module use(AssembledFunction function) {
        Objects.requireNonNull(function);
        includedFunctions.add(function);
        return this;
    }

    public Module func(Function function) {
        Objects.requireNonNull(function);
        functions.add(function);
        return this;
    }

    public long len() {
        return includedModulesLen() + includedFunctionsLen() + constantsLen() + functionsLen();
    }

    public long includedModulesLen() {
        var len = 0L;
        for (var includedModule : includedModules) {
            var len1 = includedModule.len();
            len += len1;
            len += len1 % 4;
        }
        return len;
    }

    public long includedFunctionsLen() {
        var len = 0L;
        for (var includedFunction : includedFunctions) {
            var len1 = includedFunction.len();
            len += len1;
            len += len1 % 4;
        }
        return len;
    }

    public long constantsLen() {
        var len = 0L;
        for (var constant : constants) {
            var len1 = constant.len();
            len += len1;
            len += len1 % 4;
        }
        return len;
    }

    public long functionsLen() {
        var len = 0L;
        for (var function : functions) {
            var len1 = function.len();
            len += len1;
            len += len1 % 4;
        }
        return len;
    }

    public long constantOffset(int index) {
        var len = 0;
        for (int i = 0; i < index; i++) {
            var len1 = constants.get(i)
                .len();
            len += len1;
            len += len1 % 4;
        }
        return len;
    }

    public long functionOffset(int index) {
        var len = 0;
        for (int i = 0; i < index; i++) {
            long len1 = functions.get(i)
                .len();
            len += len1;
            len += len1 % 4;
        }
        return len;
    }

    public Offset<Module> getOffset(String label) {
        return offsetTable.get(label);
    }

    public void assemble(OutputStream out) throws IOException {
        for (var includedModule : includedModules) {
            includedModule.write(out);
            for (int i = 0; i < includedModule.len() % 4; i++) {
                out.write(0);
            }
        }
        for (var includedFunction : includedFunctions) {
            includedFunction.write(out);
            for (int i = 0; i < includedFunction.len() % 4; i++) {
                out.write(0);
            }
        }
        for (var constant : constants) {
            constant.write(this, out);
            for (int i = 0; i < constant.len() % 4; i++) {
                out.write(0);
            }
        }
        for (var function : functions) {
            function.assemble(out);
            for (int i = 0; i < function.len() % 4; i++) {
                out.write(0);
            }
        }
    }
}
