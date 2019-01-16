package cn.temptation.dao;

import cn.temptation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, Integer> {
    // 根据账号查询用户
    @Query(value = "SELECT * FROM sys_user WHERE username=:username", nativeQuery = true)
    User findByUsername(@Param("username") String username);
}