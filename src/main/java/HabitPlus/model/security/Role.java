package HabitPlus.model.security;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String name;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum values{
        ADMIN(1L),
        BASIC(2L);
        Long roleId;

        values(Long roleId){this.roleId = roleId;}
        public Long getRoleId(){ return roleId;}
    }
}
