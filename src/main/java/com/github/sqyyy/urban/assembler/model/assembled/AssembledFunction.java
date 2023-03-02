package com.github.sqyyy.urban.assembler.model.assembled;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class AssembledFunction {
    private final Map<String, AssembledOffset> offsetTable;
    private final byte[] bytes;

    public AssembledFunction() {
        offsetTable = new HashMap<>();
        bytes = new byte[0];
        // TODO
    }

    public AssembledOffset getOffset(String label) {
        return offsetTable.get(label);
    }

    public int len() {
        return bytes.length;
    }

    public void write(OutputStream out) throws IOException {
        out.write(bytes);
    }
}
