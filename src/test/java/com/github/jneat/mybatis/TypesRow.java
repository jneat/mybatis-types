package com.github.jneat.mybatis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Properties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypesRow {

    private long id;

    private Map<String, String> keyvalue;

    private Properties properties;
}
