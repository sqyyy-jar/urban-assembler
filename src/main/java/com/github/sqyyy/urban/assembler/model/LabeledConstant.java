package com.github.sqyyy.urban.assembler.model;

public record LabeledConstant<T>(Offset<T> offset, Constant constant) {
}
