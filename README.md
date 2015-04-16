# Additional MyBatis type handlers for Java 8 runtime without extra dependencies

[![Release](https://jitpack.io/v/jneat/mybatis-types.svg)](https://jitpack.io/#jneat/mybatis-types)  
[API javadoc](https://jitpack.io/com/github/jneat/mybatis-types/-SNAPSHOT/javadoc/)

## Project features

### Java 8 time (JSR 310) support for Mybatis
This packages provide mybatis type handlers for core java.time.\* types:
- java.time.Instant (via java.sql.Timestamp)
- java.time.LocalDate (via java.sql.Date)
- java.time.LocalDateTime (via java.sql.Timestamp)
- java.time.LocalTime (via java.sql.Time)
- java.time.OffsetDateTime (via java.sql.Timestamp)
- java.time.ZonedDateTime (via java.sql.Timestamp)

### Arrays support
Generally JDBC driver has built-in support for array types.
Thus any DB with array support can be supported.

I'm provide support only for PostgreSQL array types.
Theoretically adding new DB will require only several lines of code, but I do not have time for this. 
Fell free to contribute if you require new DB support for arrays.

### Java properties and key-value map support
Underlying concept is simple - any text field can have content in format of properties file. 

This package provide a way to read/write properties into text fields.
In your application you can use ```Map<String,String>``` or ```java.util.Properties``` objects to store/read your fields.

Also it does not return null for null DB columns, but empty Map or Properties.

## Add to your project

You can add this artifact to your project using [JitPack](https://jitpack.io/#jneat/mybatis-types).  
All versions list, instructions for gradle, maven, ivy etc. can be found by link above.

To get latest commit use -SNAPSHOT instead version number.

## Configure

### Mybatis config
```
<!-- mybatis-config.xml -->
<typeHandlers>
  <package name="com.github.jneat.mybatis"/>
</typeHandlers>
```

Or you can specify each type handler class one by one.
In a case if you are need particular handlers only.

```
<!-- mybatis-config.xml -->
<typeHandlers>
  <typeHandler handler="com.github.jneat.mybatis.InstantTypeHandler"/>
  <typeHandler handler="com.github.jneat.mybatis.LocalDateTypeHandler"/>
  <typeHandler handler="com.github.jneat.mybatis.LocalDateTimeTypeHandler"/>
  <typeHandler handler="com.github.jneat.mybatis.LocalTimeTypeHandler"/>
  <typeHandler handler="com.github.jneat.mybatis.OffsetDateTimeTypeHandler"/>
  <typeHandler handler="com.github.jneat.mybatis.ZonedDateTimeTypeHandler"/>
  <!-- and other handlers -->
</typeHandlers>
```

### Mybatis via Spring
```
<bean id="SomeId" class="org.mybatis.spring.SqlSessionFactoryBean">
    <!-- your configuration -->
    <property name="typeHandlersPackage" value="com.github.jneat.mybatis" />
</bean>
```
