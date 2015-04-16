package com.github.jneat.mybatis;

import lombok.Data;

@Data
public class ArraysRow {

    private long id;

    private String[] strings;

    private String[][] strings2d;

    private Boolean[] booleans;

    private Double[] doubles;

    private Integer[] integers;

    private Long[] longs;

}
