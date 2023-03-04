package com.github.sqyyy.urban.assembler.model;

import com.github.sqyyy.urban.assembler.model.assembled.AssembledFunction;
import com.github.sqyyy.urban.assembler.model.assembled.AssembledModule;
import com.github.sqyyy.urban.assembler.util.Utils;

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
        offsetTable.put(function.getName(), new Offset.ModuleFunctionOffset(functions.size()));
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
            len += Utils.alignment(len1, 4);
        }
        return len;
    }

    public long includedFunctionsLen() {
        var len = 0L;
        for (var includedFunction : includedFunctions) {
            var len1 = includedFunction.len();
            len += len1;
            len += Utils.alignment(len1, 4);
        }
        return len;
    }

    public long constantsLen() {
        var len = 0L;
        for (var constant : constants) {
            var len1 = constant.len();
            len += len1;
            len += Utils.alignment(len1, 4);
        }
        return len;
    }

    public long functionsLen() {
        var len = 0L;
        for (var function : functions) {
            var len1 = function.len();
            len += len1;
            len += Utils.alignment(len1, 4);
        }
        return len;
    }

    public long constantOffset(int index) {
        var len = 0;
        for (int i = 0; i < index; i++) {
            var len1 = constants.get(i)
                .len();
            len += len1;
            len += Utils.alignment(len1, 4);
        }
        return len;
    }

    public long functionOffset(int index) {
        var len = 0;
        for (int i = 0; i < index; i++) {
            long len1 = functions.get(i)
                .len();
            len += len1;
            len += Utils.alignment(len1, 4);
        }
        return len + functions.get(index)
            .constantsLen();
    }

    public long functionOffset(Function function) {
        var len = 0;
        for (var value : functions) {
            if (value == function) {
                break;
            }
            long len1 = value.len();
            len += len1;
            len += Utils.alignment(len1, 4);
        }
        return len + function.constantsLen();
    }

    public Offset<Module> getOffset(String label) {
        return offsetTable.get(label);
    }

    public long assemble(OutputStream out) throws IOException {
        var hasMain = false;
        var mainOffset = 0;
        for (var includedModule : includedModules) {
            includedModule.write(out);
            var len = includedModule.len();
            mainOffset += len;
            for (var i = 0; i < Utils.alignment(len, 4); i++) {
                out.write(0);
                mainOffset++;
            }
        }
        for (var includedFunction : includedFunctions) {
            includedFunction.write(out);
            var len = includedFunction.len();
            mainOffset += len;
            for (var i = 0; i < Utils.alignment(len, 4); i++) {
                out.write(0);
                mainOffset++;
            }
        }
        for (var constant : constants) {
            constant.write(this, out);
            var len = constant.len();
            mainOffset += len;
            for (var i = 0; i < Utils.alignment(len, 4); i++) {
                out.write(0);
                mainOffset++;
            }
        }
        for (var function : functions) {
            function.assemble(out);
            if (function.getName()
                .equals("main")) {
                if (hasMain) {
                    throw new RuntimeException("Multiple main functions");
                }
                mainOffset += function.constantsLen();
                hasMain = true;
            }
            var len = function.len();
            if (!hasMain) {
                mainOffset += len;
            }
            for (int i = 0; i < Utils.alignment(len, 4); i++) {
                out.write(0);
                if (!hasMain) {
                    mainOffset++;
                }
            }
        }
        return hasMain ? mainOffset : -1;
    }

    public Module constant(String label, long value) {
        offsetTable.put(label, new Offset.ModuleConstOffset(constants.size()));
        constants.add(new Constant.Integer<>(value));
        return this;
    }

    public Module constant(String label, double value) {
        offsetTable.put(label, new Offset.ModuleConstOffset(constants.size()));
        constants.add(new Constant.Float<>(value));
        return this;
    }

    public Module constant(String label, String value) {
        offsetTable.put(label, new Offset.ModuleConstOffset(constants.size()));
        constants.add(new Constant.CString<>(value));
        return this;
    }

    public Module staticAlloc(String label, long capacity) {
        offsetTable.put(label, new Offset.ModuleConstOffset(constants.size()));
        constants.add(new Constant.Buffer<>(capacity));
        return this;
    }
}
