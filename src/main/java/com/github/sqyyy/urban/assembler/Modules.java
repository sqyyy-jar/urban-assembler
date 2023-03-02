package com.github.sqyyy.urban.assembler;

public final class Modules {
    //    public static final AssembledModule MATH;
    //
    //    static {
    //        try {
    //            var math = new ModuleAssembler();
    //            {
    //                // Constants
    //                math.labelConst("-math:0.0");
    //                math.constFloat(0.0d);
    //                math.labelConst("-math:2.0");
    //                math.constFloat(2.0d);
    //            }
    //            {
    //                // sqrt(f64): f64
    //                math.labelCode("math:sqrt(f64)f64");
    //                math.ldr(3, "-math:2.0");
    //                math.divf(2, 0, 3);
    //                for (int i = 0; i < 16; i++) {
    //                    math.divf(1, 0, 2);
    //                    math.addf(1, 2, 1);
    //                    math.divf(2, 1, 3);
    //                }
    //                math.mov(0, 2);
    //                math.ret();
    //                math.panic();
    //            }
    //            {
    //                // abs(i64): i64
    //                math.labelCode("math:abs(i64)i64");
    //                math.mov(1, 0L);
    //                math.cmps(1, 0, 1);
    //                math.branchLess(1, "-math:abs(i64)i64#0");
    //                math.ret();
    //                math.labelCode("-math:abs(i64)i64#0");
    //                math.movs(1, 0L);
    //                math.subs(0, 1, 0);
    //                math.ret();
    //                math.panic();
    //            }
    //            {
    //                // abs(f64): f64
    //                math.labelCode("math:abs(f64)f64");
    //                math.ldr(1, "-math:0.0");
    //                math.cmpf(1, 0, 1);
    //                math.branchLess(1, "-math:abs(f64)f64#0");
    //                math.ret();
    //                math.labelCode("-math:abs(f64)f64#0");
    //                math.ldr(1, "-math:0.0");
    //                math.subf(0, 1, 0);
    //                math.ret();
    //                math.panic();
    //            }
    //            MATH = AssembledModule.compile(math);
    //        } catch (IOException e) {
    //            throw new UncheckedIOException(e);
    //        }
    //    }

    private Modules() {
    }
}
