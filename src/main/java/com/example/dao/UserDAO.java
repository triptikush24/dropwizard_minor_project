package com.example.dao;

import com.example.api.User;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

@RegisterRowMapper(UserMapper.class)
public interface UserDAO {
    @SqlQuery("SELECT * FROM users")
    List<User> getAllUsers();

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    User getUserById(@Bind("id") Long id);

    @SqlUpdate("INSERT INTO users (name, email) VALUES (:name, :email)")
    @GetGeneratedKeys
    Long createUser(@BindBean User user);

    @SqlUpdate("UPDATE users SET name = :name, email = :email WHERE id = :id")
    void updateUser(@BindBean User user);

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    void deleteUser(@Bind("id") Long id);
} 