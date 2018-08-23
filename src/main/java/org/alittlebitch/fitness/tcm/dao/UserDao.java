package org.alittlebitch.fitness.tcm.dao;

import org.alittlebitch.fitness.tcm.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/23 10:44
 */
@Mapper
public interface UserDao {
    @Select("select username,password,name from testing_user where username=#{username} and password = #{password};")
    User selectUserCount(@Param("username") String username, @Param("password") String pwd);

    @Update("update testing_user set pwd = #{password} where username = #{username}")
    int changePwd(@Param("username") String username, @Param("password") String pwd);

    @Insert("insert into testing_user (username,password,name) values (#{username},#{password},#{name})")
    int addUser(@Param("username") String username, @Param("password") String pwd, @Param("name") String name);

    @Select("select count(1) from testing_user where username = #{username}")
    int existsUser(@Param("username") String username);

    @Select("${sql}")
    List<User> list(@Param("sql") String sql);
}
