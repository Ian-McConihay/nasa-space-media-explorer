package com.mcconihay.nasamediaexplorer.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Defines a security role used for authorization.
 * Roles are assigned to users through a join relationship.
 */
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, unique = true, length = 40, columnDefinition = "varchar(40)")
    private RoleType roleName;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoleEntity> userRoles = new HashSet<>();

    public RoleEntity() { }

    public RoleEntity(RoleType roleName) {
        this.roleName = roleName;
    }

    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }

    public RoleType getRoleName() { return roleName; }
    public void setRoleName(RoleType roleName) { this.roleName = roleName; }

    public Set<UserRoleEntity> getUserRoles() { return userRoles; }
    public void setUserRoles(Set<UserRoleEntity> userRoles) { this.userRoles = userRoles; }

    /**
     * Roles are compared by type to prevent duplicate assignments.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleEntity)) return false;
        RoleEntity that = (RoleEntity) o;
        return roleName == that.roleName;
    }

    @Override
    public int hashCode() {
        return roleName != null ? roleName.hashCode() : 0;
    }
}