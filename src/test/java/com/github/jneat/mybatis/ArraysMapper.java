package com.github.jneat.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface ArraysMapper {

    @Select("SELECT * FROM mybatis_arrays where id = #{id}")
    ArraysRow get(long id);

    @Insert("INSERT INTO mybatis_arrays (id, booleans, doubles, integers, longs, strings, strings2d) "
        + "VALUES (#{id}, #{booleans}, #{doubles}, #{integers}, #{longs}, #{strings}, #{strings2d})")
    int set(ArraysRow row);
}
