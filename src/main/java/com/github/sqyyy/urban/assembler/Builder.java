package com.github.sqyyy.urban.assembler;

import com.github.sqyyy.urban.assembler.insn.ADD;
import com.github.sqyyy.urban.assembler.insn.ADDF;
import com.github.sqyyy.urban.assembler.insn.ADDS;
import com.github.sqyyy.urban.assembler.insn.ADDS_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.ADD_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.AND;
import com.github.sqyyy.urban.assembler.insn.BL_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.BL_LABEL;
import com.github.sqyyy.urban.assembler.insn.BR;
import com.github.sqyyy.urban.assembler.insn.BRL;
import com.github.sqyyy.urban.assembler.insn.B_EQ_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.B_EQ_LABEL;
import com.github.sqyyy.urban.assembler.insn.B_GE_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.B_GE_LABEL;
import com.github.sqyyy.urban.assembler.insn.B_GT_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.B_GT_LABEL;
import com.github.sqyyy.urban.assembler.insn.B_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.B_LABEL;
import com.github.sqyyy.urban.assembler.insn.B_LE_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.B_LE_LABEL;
import com.github.sqyyy.urban.assembler.insn.B_LT_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.B_LT_LABEL;
import com.github.sqyyy.urban.assembler.insn.B_NE_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.B_NE_LABEL;
import com.github.sqyyy.urban.assembler.insn.DIV;
import com.github.sqyyy.urban.assembler.insn.DIVF;
import com.github.sqyyy.urban.assembler.insn.DIVS;
import com.github.sqyyy.urban.assembler.insn.DIVS_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.DIV_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.HALT;
import com.github.sqyyy.urban.assembler.insn.INTERRUPT_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.LDR;
import com.github.sqyyy.urban.assembler.insn.LDR_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.LDR_LABEL;
import com.github.sqyyy.urban.assembler.insn.MOV;
import com.github.sqyyy.urban.assembler.insn.MOVABS_LABEL;
import com.github.sqyyy.urban.assembler.insn.MOVS_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.MOV_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.MUL;
import com.github.sqyyy.urban.assembler.insn.MULF;
import com.github.sqyyy.urban.assembler.insn.MULS;
import com.github.sqyyy.urban.assembler.insn.MULS_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.MUL_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.NOP;
import com.github.sqyyy.urban.assembler.insn.NOT;
import com.github.sqyyy.urban.assembler.insn.OR;
import com.github.sqyyy.urban.assembler.insn.SHL_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.SHRS_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.SHR_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.STR;
import com.github.sqyyy.urban.assembler.insn.STR_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.STR_LABEL;
import com.github.sqyyy.urban.assembler.insn.SUB;
import com.github.sqyyy.urban.assembler.insn.SUBF;
import com.github.sqyyy.urban.assembler.insn.SUBS;
import com.github.sqyyy.urban.assembler.insn.SUBS_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.SUB_IMMEDIATE;
import com.github.sqyyy.urban.assembler.insn.XOR;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Builder {
    private static Map<String, Label> labels = new HashMap<>();
    private static List<Instruction> instructions = new ArrayList<>();
    private static List<Constant> constants = new ArrayList<>();

    public static void write(OutputStream out) throws IOException {
        var i = 0;
        var labelsImmutable = Collections.unmodifiableMap(labels);
        for (var instruction : instructions) {
            Bits.writeInt(out, instruction.write(labelsImmutable, instructions.size(), i));
            i++;
        }
        for (var constant : constants) {
            constant.write(out);
        }
        labels = new HashMap<>();
        instructions = new ArrayList<>();
        constants = new ArrayList<>();
    }

    public static void CodeLabel(String label) {
        labels.putIfAbsent(label, new Label(instructions.size(), false));
    }

    public static void ConstLabel(String label) {
        var offset = 0;
        for (var constant : constants) {
            offset += constant.len();
        }
        labels.putIfAbsent(label, new Label(offset, true));
    }

    public static void IntConstant(long value) {
        constants.add(new Constant.Integer(value));
    }

    public static void FloatConstant(double value) {
        constants.add(new Constant.Float(value));
    }

    public static void CStringConstant(String value) {
        constants.add(new Constant.CString(value));
    }

    public static void Add(int reg0, int reg1, int reg2) {
        instructions.add(new ADD(reg0, reg1, reg2));
    }

    public static void Add(int reg0, int reg1, long immediate) {
        instructions.add(new ADD_IMMEDIATE(reg0, reg1, immediate));
    }

    public static void Addf(int reg0, int reg1, int reg2) {
        instructions.add(new ADDF(reg0, reg1, reg2));
    }

    public static void Adds(int reg0, int reg1, int reg2) {
        instructions.add(new ADDS(reg0, reg1, reg2));
    }

    public static void Adds(int reg0, int reg1, long immediate) {
        instructions.add(new ADDS_IMMEDIATE(reg0, reg1, immediate));
    }

    public static void And(int reg0, int reg1, int reg2) {
        instructions.add(new AND(reg0, reg1, reg2));
    }

    public static void B(long immediate) {
        instructions.add(new B_IMMEDIATE(immediate));
    }

    public static void B(String label) {
        instructions.add(new B_LABEL(label));
    }

    public static void BEq(int reg0, long immediate) {
        instructions.add(new B_EQ_IMMEDIATE(reg0, immediate));
    }

    public static void BEq(int reg0, String label) {
        instructions.add(new B_EQ_LABEL(reg0, label));
    }

    public static void BGe(int reg0, long immediate) {
        instructions.add(new B_GE_IMMEDIATE(reg0, immediate));
    }

    public static void BGe(int reg0, String label) {
        instructions.add(new B_GE_LABEL(reg0, label));
    }

    public static void BGt(int reg0, long immediate) {
        instructions.add(new B_GT_IMMEDIATE(reg0, immediate));
    }

    public static void BGt(int reg0, String label) {
        instructions.add(new B_GT_LABEL(reg0, label));
    }

    public static void BLe(int reg0, long immediate) {
        instructions.add(new B_LE_IMMEDIATE(reg0, immediate));
    }

    public static void BLe(int reg0, String label) {
        instructions.add(new B_LE_LABEL(reg0, label));
    }

    public static void BLt(int reg0, long immediate) {
        instructions.add(new B_LT_IMMEDIATE(reg0, immediate));
    }

    public static void BLt(int reg0, String label) {
        instructions.add(new B_LT_LABEL(reg0, label));
    }

    public static void BNe(int reg0, long immediate) {
        instructions.add(new B_NE_IMMEDIATE(reg0, immediate));
    }

    public static void BNe(int reg0, String label) {
        instructions.add(new B_NE_LABEL(reg0, label));
    }

    public static void Bl(long immediate) {
        instructions.add(new BL_IMMEDIATE(immediate));
    }

    public static void Bl(String label) {
        instructions.add(new BL_LABEL(label));
    }

    public static void Br(int reg0) {
        instructions.add(new BR(reg0));
    }

    public static void Brl(int reg0) {
        instructions.add(new BRL(reg0));
    }

    public static void Div(int reg0, int reg1, int reg2) {
        instructions.add(new DIV(reg0, reg1, reg2));
    }

    public static void Div(int reg0, int reg1, long immediate) {
        instructions.add(new DIV_IMMEDIATE(reg0, reg1, immediate));
    }

    public static void Divf(int reg0, int reg1, int reg2) {
        instructions.add(new DIVF(reg0, reg1, reg2));
    }

    public static void Divs(int reg0, int reg1, int reg2) {
        instructions.add(new DIVS(reg0, reg1, reg2));
    }

    public static void Divs(int reg0, int reg1, long immediate) {
        instructions.add(new DIVS_IMMEDIATE(reg0, reg1, immediate));
    }

    public static void Halt() {
        instructions.add(new HALT());
    }

    public static void Interrupt(long immediate) {
        instructions.add(new INTERRUPT_IMMEDIATE(immediate));
    }

    public static void Ldr(int reg0, int reg1) {
        instructions.add(new LDR(reg0, reg1));
    }

    public static void Ldr(int reg0, long immediate) {
        instructions.add(new LDR_IMMEDIATE(reg0, immediate));
    }

    public static void Ldr(int reg0, String label) {
        instructions.add(new LDR_LABEL(reg0, label));
    }

    public static void Mov(int reg0, int reg1) {
        instructions.add(new MOV(reg0, reg1));
    }

    public static void Mov(int reg0, long immediate) {
        instructions.add(new MOV_IMMEDIATE(reg0, immediate));
    }

    public static void MovAbs(int reg0, String label) {
        instructions.add(new MOVABS_LABEL(reg0, label));
    }

    public static void Movs(int reg0, long immediate) {
        instructions.add(new MOVS_IMMEDIATE(reg0, immediate));
    }

    public static void Mul(int reg0, int reg1, int reg2) {
        instructions.add(new MUL(reg0, reg1, reg2));
    }

    public static void Mul(int reg0, int reg1, long immediate) {
        instructions.add(new MUL_IMMEDIATE(reg0, reg1, immediate));
    }

    public static void Mulf(int reg0, int reg1, int reg2) {
        instructions.add(new MULF(reg0, reg1, reg2));
    }

    public static void Muls(int reg0, int reg1, int reg2) {
        instructions.add(new MULS(reg0, reg1, reg2));
    }

    public static void Muls(int reg0, int reg1, long immediate) {
        instructions.add(new MULS_IMMEDIATE(reg0, reg1, immediate));
    }

    public static void Nop() {
        instructions.add(new NOP());
    }

    public static void Not(int reg0, int reg1) {
        instructions.add(new NOT(reg0, reg1));
    }

    public static void Or(int reg0, int reg1, int reg2) {
        instructions.add(new OR(reg0, reg1, reg2));
    }

    public static void Shl(int reg0, int reg1, long immediate) {
        instructions.add(new SHL_IMMEDIATE(reg0, reg1, immediate));
    }

    public static void Shr(int reg0, int reg1, long immediate) {
        instructions.add(new SHR_IMMEDIATE(reg0, reg1, immediate));
    }

    public static void Shrs(int reg0, int reg1, long immediate) {
        instructions.add(new SHRS_IMMEDIATE(reg0, reg1, immediate));
    }

    public static void Str(int reg0, int reg1) {
        instructions.add(new STR(reg0, reg1));
    }

    public static void Str(int reg0, long immediate) {
        instructions.add(new STR_IMMEDIATE(reg0, immediate));
    }

    public static void Str(int reg0, String label) {
        instructions.add(new STR_LABEL(reg0, label));
    }

    public static void Sub(int reg0, int reg1, int reg2) {
        instructions.add(new SUB(reg0, reg1, reg2));
    }

    public static void Sub(int reg0, int reg1, long immediate) {
        instructions.add(new SUB_IMMEDIATE(reg0, reg1, immediate));
    }

    public static void Subf(int reg0, int reg1, int reg2) {
        instructions.add(new SUBF(reg0, reg1, reg2));
    }

    public static void Subs(int reg0, int reg1, int reg2) {
        instructions.add(new SUBS(reg0, reg1, reg2));
    }

    public static void Subs(int reg0, int reg1, long immediate) {
        instructions.add(new SUBS_IMMEDIATE(reg0, reg1, immediate));
    }

    public static void Xor(int reg0, int reg1, int reg2) {
        instructions.add(new XOR(reg0, reg1, reg2));
    }
}
