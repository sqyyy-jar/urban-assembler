package com.github.sqyyy.urban.assembler.model;

import com.github.sqyyy.urban.assembler.util.Bits;
import com.github.sqyyy.urban.assembler.util.Utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Function implements Instructable<Function> {
    private final Module parent;
    private final String name;
    private final Map<String, Offset<Function>> offsetTable = new HashMap<>();
    private final List<Constant<Function>> constants = new ArrayList<>();
    private final List<Instruction> instructions = new ArrayList<>();

    public Function(Module parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public static Function of(Module parent, String name) {
        return new Function(parent, name);
    }

    public Module getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public long len() {
        return instructionsLen() + constantsLen();
    }

    public long instructionsLen() {
        return instructions.size() * 4L;
    }

    public long constantsLen() {
        var len = 0;
        for (var constant : constants) {
            var len1 = constant.len();
            len += len1;
            len += Utils.alignment(len1, 4);
        }
        return len;
    }

    public long constantOffset(int index) {
        var len = 0;
        for (var i = 0; i < index; i++) {
            var len1 = constants.get(i)
                .len();
            len += len1;
            len += Utils.alignment(len1, 4);
        }
        return len;
    }

    public Offset<Function> getOffset(String label) {
        return offsetTable.get(label);
    }

    public void assemble(OutputStream out) throws IOException {
        for (var constant : constants) {
            constant.write(this, out);
            out.flush();
            var len = constant.len();
            for (var i = 0; i < Utils.alignment(len, 4); i++) {
                out.write(0);
            }
        }
        var i = 0;
        for (var instruction : instructions) {
            var opcode = instruction.write(this, i++);
            Bits.writeInt(out, opcode);
        }
    }

    @Override
    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    public Function constant(String label, long value) {
        offsetTable.put(label, new Offset.FunctionConstOffset(constants.size()));
        constants.add(new Constant.Integer<>(value));
        return this;
    }

    public Function constant(String label, double value) {
        offsetTable.put(label, new Offset.FunctionConstOffset(constants.size()));
        constants.add(new Constant.Float<>(value));
        return this;
    }

    public Function constant(String label, String value) {
        offsetTable.put(label, new Offset.FunctionConstOffset(constants.size()));
        constants.add(new Constant.CString<>(value));
        return this;
    }

    public Function staticAlloc(String label, long capacity) {
        offsetTable.put(label, new Offset.FunctionConstOffset(constants.size()));
        constants.add(new Constant.Buffer<>(capacity));
        return this;
    }

    public Function raw(int opcode) {
        instructions.add(new RawInstruction(opcode));
        return this;
    }

    public Function ret() {
        branch(Register.R30);
        return this;
    }

    public Function branch(String label) {
        instructions.add(new BranchLabelInstruction(label, false));
        return this;
    }

    public Function branchL(String label) {
        instructions.add(new BranchLabelInstruction(label, true));
        return this;
    }
}
