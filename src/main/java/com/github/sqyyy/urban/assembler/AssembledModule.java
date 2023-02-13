package com.github.sqyyy.urban.assembler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AssembledModule {
    private final byte[] bytes;
    private final Map<String, Integer> offsetTable;

    private AssembledModule(byte[] bytes, Map<String, Integer> offsetTable) {
        this.bytes = bytes;
        this.offsetTable = offsetTable;
    }

    public static AssembledModule compile(ModuleAssembler assembler) throws IOException {
        var buffer = new ByteArrayOutputStream();
        assembler.write(buffer);
        var labels = assembler.getLabels();
        var offsetTable = new HashMap<String, Integer>();
        for (var label : labels.entrySet()) {
            offsetTable.put(label.getKey(), label.getValue()
                .absoluteByteAddress(assembler) / 4);
        }
        return new AssembledModule(buffer.toByteArray(), Collections.unmodifiableMap(offsetTable));
    }

    public void write(OutputStream out) throws IOException {
        out.write(bytes);
    }

    public Map<String, Integer> getOffsetTable() {
        return offsetTable;
    }

    public int length() {
        return bytes.length;
    }
}
