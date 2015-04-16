package com.github.jneat.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface TypesMapper {

    @Select("SELECT * FROM mybatis_types where id = #{id}")
    TypesRow get(long id);

    @Insert("INSERT INTO mybatis_types (id, keyvalue, properties) VALUES (#{id}, #{keyvalue}, #{properties}) "
        + " ON CONFLICT (id) DO UPDATE "
        + "SET properties = EXCLUDED.properties, keyvalue = EXCLUDED.keyvalue")
    int set(TypesRow types);

}
