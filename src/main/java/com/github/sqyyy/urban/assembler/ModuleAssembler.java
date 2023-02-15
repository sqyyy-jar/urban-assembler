package com.github.sqyyy.urban.assembler;

import com.github.sqyyy.urban.assembler.insn.Add;
import com.github.sqyyy.urban.assembler.insn.AddFloat;
import com.github.sqyyy.urban.assembler.insn.AddImmediate;
import com.github.sqyyy.urban.assembler.insn.AddSigned;
import com.github.sqyyy.urban.assembler.insn.AddSignedImmediate;
import com.github.sqyyy.urban.assembler.insn.And;
import com.github.sqyyy.urban.assembler.insn.BranchEqualImmediate;
import com.github.sqyyy.urban.assembler.insn.BranchEqualLabel;
import com.github.sqyyy.urban.assembler.insn.BranchGreaterEqualImmediate;
import com.github.sqyyy.urban.assembler.insn.BranchGreaterEqualLabel;
import com.github.sqyyy.urban.assembler.insn.BranchGreaterImmediate;
import com.github.sqyyy.urban.assembler.insn.BranchGreaterLabel;
import com.github.sqyyy.urban.assembler.insn.BranchImmediate;
import com.github.sqyyy.urban.assembler.insn.BranchLabel;
import com.github.sqyyy.urban.assembler.insn.BranchLabelLinked;
import com.github.sqyyy.urban.assembler.insn.BranchLessEqualImmediate;
import com.github.sqyyy.urban.assembler.insn.BranchLessEqualLabel;
import com.github.sqyyy.urban.assembler.insn.BranchLessImmediate;
import com.github.sqyyy.urban.assembler.insn.BranchLessLabel;
import com.github.sqyyy.urban.assembler.insn.BranchLinkedImmediate;
import com.github.sqyyy.urban.assembler.insn.BranchNotEqualImmediate;
import com.github.sqyyy.urban.assembler.insn.BranchNotEqualLabel;
import com.github.sqyyy.urban.assembler.insn.BranchRegister;
import com.github.sqyyy.urban.assembler.insn.BranchRegisterLinked;
import com.github.sqyyy.urban.assembler.insn.Div;
import com.github.sqyyy.urban.assembler.insn.DivFloat;
import com.github.sqyyy.urban.assembler.insn.DivImmediate;
import com.github.sqyyy.urban.assembler.insn.DivSigned;
import com.github.sqyyy.urban.assembler.insn.DivSignedImmediate;
import com.github.sqyyy.urban.assembler.insn.Halt;
import com.github.sqyyy.urban.assembler.insn.InterruptImmediate;
import com.github.sqyyy.urban.assembler.insn.Load;
import com.github.sqyyy.urban.assembler.insn.LoadImmediate;
import com.github.sqyyy.urban.assembler.insn.LoadLabel;
import com.github.sqyyy.urban.assembler.insn.MovAbsLabel;
import com.github.sqyyy.urban.assembler.insn.Move;
import com.github.sqyyy.urban.assembler.insn.MoveImmediate;
import com.github.sqyyy.urban.assembler.insn.MoveSignedImmediate;
import com.github.sqyyy.urban.assembler.insn.Mul;
import com.github.sqyyy.urban.assembler.insn.MulFloat;
import com.github.sqyyy.urban.assembler.insn.MulImmediate;
import com.github.sqyyy.urban.assembler.insn.MulSigned;
import com.github.sqyyy.urban.assembler.insn.MulSignedImmediate;
import com.github.sqyyy.urban.assembler.insn.Nop;
import com.github.sqyyy.urban.assembler.insn.Not;
import com.github.sqyyy.urban.assembler.insn.Or;
import com.github.sqyyy.urban.assembler.insn.ShlImmediate;
import com.github.sqyyy.urban.assembler.insn.ShrImmediate;
import com.github.sqyyy.urban.assembler.insn.ShrsImmediate;
import com.github.sqyyy.urban.assembler.insn.Store;
import com.github.sqyyy.urban.assembler.insn.StoreImmediate;
import com.github.sqyyy.urban.assembler.insn.StoreLabel;
import com.github.sqyyy.urban.assembler.insn.Sub;
import com.github.sqyyy.urban.assembler.insn.SubFloat;
import com.github.sqyyy.urban.assembler.insn.SubImmediate;
import com.github.sqyyy.urban.assembler.insn.SubSigned;
import com.github.sqyyy.urban.assembler.insn.SubSignedImmediate;
import com.github.sqyyy.urban.assembler.insn.Xor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ModuleAssembler {
    private final Map<String, Label> labels = new HashMap<>();
    private final List<Instruction> instructions = new ArrayList<>();
    private final List<Constant> constants = new ArrayList<>();
    private final List<AssembledModule> modules = new ArrayList<>();

    public void write(OutputStream out) throws IOException {
        var i = 0;
        for (var instruction : instructions) {
            Bits.writeInt(out, instruction.write(this, i));
            i++;
        }
        for (var constant : constants) {
            constant.write(out, this);
        }
        for (var module : modules) {
            module.write(out);
        }
    }

    public void labelCode(String label) {
        labels.putIfAbsent(label, new Label.Assembler(instructions.size(), false));
    }

    public void labelConst(String label) {
        var offset = 0;
        for (var constant : constants) {
            offset += constant.len();
        }
        labels.putIfAbsent(label, new Label.Assembler(offset, true));
    }

    public void constInt(long value) {
        constants.add(new Constant.Integer(value));
    }

    public void constFloat(double value) {
        constants.add(new Constant.Float(value));
    }

    public void constCStr(String value) {
        constants.add(new Constant.CString(value));
    }

    public void constAbsLabel(String label) {
        constants.add(new Constant.AbsoluteLabelAddress(label));
    }

    public void add(int reg0, int reg1, int reg2) {
        instructions.add(new Add(reg0, reg1, reg2));
    }

    public void add(int reg0, int reg1, long immediate) {
        instructions.add(new AddImmediate(reg0, reg1, immediate));
    }

    public void addf(int reg0, int reg1, int reg2) {
        instructions.add(new AddFloat(reg0, reg1, reg2));
    }

    public void adds(int reg0, int reg1, int reg2) {
        instructions.add(new AddSigned(reg0, reg1, reg2));
    }

    public void adds(int reg0, int reg1, long immediate) {
        instructions.add(new AddSignedImmediate(reg0, reg1, immediate));
    }

    public void and(int reg0, int reg1, int reg2) {
        instructions.add(new And(reg0, reg1, reg2));
    }

    public void branch(long immediate) {
        instructions.add(new BranchImmediate(immediate));
    }

    public void branch(String label) {
        instructions.add(new BranchLabel(label));
    }

    public void branchEqual(int reg0, long immediate) {
        instructions.add(new BranchEqualImmediate(reg0, immediate));
    }

    public void branchEqual(int reg0, String label) {
        instructions.add(new BranchEqualLabel(reg0, label));
    }

    public void branchGreaterEqual(int reg0, long immediate) {
        instructions.add(new BranchGreaterEqualImmediate(reg0, immediate));
    }

    public void branchGreaterEqual(int reg0, String label) {
        instructions.add(new BranchGreaterEqualLabel(reg0, label));
    }

    public void branchGreater(int reg0, long immediate) {
        instructions.add(new BranchGreaterImmediate(reg0, immediate));
    }

    public void branchGreater(int reg0, String label) {
        instructions.add(new BranchGreaterLabel(reg0, label));
    }

    public void branchLessEqual(int reg0, long immediate) {
        instructions.add(new BranchLessEqualImmediate(reg0, immediate));
    }

    public void branchLessEqual(int reg0, String label) {
        instructions.add(new BranchLessEqualLabel(reg0, label));
    }

    public void branchLess(int reg0, long immediate) {
        instructions.add(new BranchLessImmediate(reg0, immediate));
    }

    public void branchLess(int reg0, String label) {
        instructions.add(new BranchLessLabel(reg0, label));
    }

    public void branchNotEqual(int reg0, long immediate) {
        instructions.add(new BranchNotEqualImmediate(reg0, immediate));
    }

    public void branchNotEqual(int reg0, String label) {
        instructions.add(new BranchNotEqualLabel(reg0, label));
    }

    public void branchLinked(long immediate) {
        instructions.add(new BranchLinkedImmediate(immediate));
    }

    public void branchLinked(String label) {
        instructions.add(new BranchLabelLinked(label));
    }

    public void branch(int reg0) {
        instructions.add(new BranchRegister(reg0));
    }

    public void branchLinked(int reg0) {
        instructions.add(new BranchRegisterLinked(reg0));
    }

    public void div(int reg0, int reg1, int reg2) {
        instructions.add(new Div(reg0, reg1, reg2));
    }

    public void div(int reg0, int reg1, long immediate) {
        instructions.add(new DivImmediate(reg0, reg1, immediate));
    }

    public void divf(int reg0, int reg1, int reg2) {
        instructions.add(new DivFloat(reg0, reg1, reg2));
    }

    public void divs(int reg0, int reg1, int reg2) {
        instructions.add(new DivSigned(reg0, reg1, reg2));
    }

    public void divs(int reg0, int reg1, long immediate) {
        instructions.add(new DivSignedImmediate(reg0, reg1, immediate));
    }

    public void halt() {
        instructions.add(new Halt());
    }

    public void interrupt(long immediate) {
        instructions.add(new InterruptImmediate(immediate));
    }

    public void ldr(int reg0, int reg1, long offset) {
        instructions.add(new Load(reg0, reg1, offset));
    }

    public void ldr(int reg0, long immediate) {
        instructions.add(new LoadImmediate(reg0, immediate));
    }

    public void ldr(int reg0, String label) {
        instructions.add(new LoadLabel(reg0, label));
    }

    public void mov(int reg0, int reg1) {
        instructions.add(new Move(reg0, reg1));
    }

    public void mov(int reg0, long immediate) {
        instructions.add(new MoveImmediate(reg0, immediate));
    }

    public void movAbs(int reg0, String label) {
        instructions.add(new MovAbsLabel(reg0, label));
    }

    public void movs(int reg0, long immediate) {
        instructions.add(new MoveSignedImmediate(reg0, immediate));
    }

    public void mul(int reg0, int reg1, int reg2) {
        instructions.add(new Mul(reg0, reg1, reg2));
    }

    public void mul(int reg0, int reg1, long immediate) {
        instructions.add(new MulImmediate(reg0, reg1, immediate));
    }

    public void mulf(int reg0, int reg1, int reg2) {
        instructions.add(new MulFloat(reg0, reg1, reg2));
    }

    public void muls(int reg0, int reg1, int reg2) {
        instructions.add(new MulSigned(reg0, reg1, reg2));
    }

    public void muls(int reg0, int reg1, long immediate) {
        instructions.add(new MulSignedImmediate(reg0, reg1, immediate));
    }

    public void nop() {
        instructions.add(new Nop());
    }

    public void not(int reg0, int reg1) {
        instructions.add(new Not(reg0, reg1));
    }

    public void or(int reg0, int reg1, int reg2) {
        instructions.add(new Or(reg0, reg1, reg2));
    }

    public void shl(int reg0, int reg1, long immediate) {
        instructions.add(new ShlImmediate(reg0, reg1, immediate));
    }

    public void shr(int reg0, int reg1, long immediate) {
        instructions.add(new ShrImmediate(reg0, reg1, immediate));
    }

    public void shrs(int reg0, int reg1, long immediate) {
        instructions.add(new ShrsImmediate(reg0, reg1, immediate));
    }

    public void str(int reg0, int reg1, long offset) {
        instructions.add(new Store(reg0, reg1, offset));
    }

    public void str(int reg0, long immediate) {
        instructions.add(new StoreImmediate(reg0, immediate));
    }

    public void str(int reg0, String label) {
        instructions.add(new StoreLabel(reg0, label));
    }

    public void sub(int reg0, int reg1, int reg2) {
        instructions.add(new Sub(reg0, reg1, reg2));
    }

    public void sub(int reg0, int reg1, long immediate) {
        instructions.add(new SubImmediate(reg0, reg1, immediate));
    }

    public void subf(int reg0, int reg1, int reg2) {
        instructions.add(new SubFloat(reg0, reg1, reg2));
    }

    public void subs(int reg0, int reg1, int reg2) {
        instructions.add(new SubSigned(reg0, reg1, reg2));
    }

    public void subs(int reg0, int reg1, long immediate) {
        instructions.add(new SubSignedImmediate(reg0, reg1, immediate));
    }

    public void xor(int reg0, int reg1, int reg2) {
        instructions.add(new Xor(reg0, reg1, reg2));
    }

    public void addModule(AssembledModule module) {
        for (var entry : module.getOffsetTable()
            .entrySet()) {
            labels.putIfAbsent(entry.getKey(), new Label.Compiled(modules.size(), entry.getValue()));
        }
        modules.add(module);
    }

    public int countInstructions() {
        return instructions.size();
    }

    public int countConstantBytes() {
        var sum = 0;
        for (var constant : constants) {
            sum += constant.len() * 4;
        }
        return sum;
    }

    public int moduleOffsetBytes(int module) {
        var sum = 0;
        for (int i = 0; i < module - 1; i++) {
            var entry = modules.get(i);
            sum += entry.length();
        }
        return sum;
    }

    public Map<String, Label> getLabels() {
        return Collections.unmodifiableMap(labels);
    }
}
