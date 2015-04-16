package com.github.jneat.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface TimeMapper {

    @Select("SELECT * FROM mybatis_time WHERE id = #{id}")
    TimeRow get(long id);

    @Insert("INSERT INTO mybatis_time (id, instant, locald, localt, localdt, offsetdt, zoneddt) "
        + "VALUES (#{id}, #{instant}, #{locald}, #{localt}, #{localdt}, #{offsetdt}, #{zoneddt})")
    int set(TimeRow row);
}
