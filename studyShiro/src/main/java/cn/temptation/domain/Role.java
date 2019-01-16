package cn.temptation.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sys_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleid")
    private Integer roleid;

    @Column(name = "rolename")
    private String rolename;

    @ManyToMany
    @JoinTable(name = "sys_role_resource",
            joinColumns = {@JoinColumn(name = "roleid", referencedColumnName = "roleid", foreignKey = @ForeignKey(name = "none"))},
            inverseJoinColumns = {@JoinColumn(name = "resourceid", referencedColumnName = "resourceid", foreignKey = @ForeignKey(name = "none"))})
    private Set<Resource> resources;

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}