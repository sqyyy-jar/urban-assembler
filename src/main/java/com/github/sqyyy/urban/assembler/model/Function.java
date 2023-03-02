package com.github.sqyyy.urban.assembler.model;

import com.github.sqyyy.urban.assembler.util.Bits;

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
            len += len1 % 4;
        }
        return len;
    }

    public long constantOffset(int index) {
        var len = 0;
        for (var i = 0; i < index; i++) {
            var len1 = constants.get(i)
                .len();
            len += len1;
            len += len1 % 4;
        }
        return len;
    }

    public Offset<Function> getOffset(String label) {
        return offsetTable.get(label);
    }

    public void assemble(OutputStream out) throws IOException {
        for (var constant : constants) {
            constant.write(this, out);
            for (var i = 0; i < constant.len() % 4; i++) {
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
}
