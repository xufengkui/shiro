package cn.temptation.dao;

import cn.temptation.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceDao extends JpaRepository<Resource, Integer> {
    @Query(value = "SELECT rs.resourceurl FROM sys_role_resource AS rr " +
            "INNER JOIN sys_resource AS rs ON rr.resourceid = rs.resourceid " +
            "INNER JOIN sys_role AS r ON rr.roleid = r.roleid " +
            "INNER JOIN sys_user AS u ON u.roleid = r.roleid WHERE u.userid = :userid ", nativeQuery = true)
    List<String> findByUserid(@Param("userid") Integer userid);
}