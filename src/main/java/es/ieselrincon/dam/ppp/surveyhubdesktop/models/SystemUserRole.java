/*
 * Copyright 2025 Manuel Alejandro Jiménez Torres.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.ieselrincon.dam.ppp.surveyhubdesktop.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
@Entity
public class SystemUserRole {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "createdAt")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    @Basic
    @Column(name = "roleId", insertable = false, updatable = false)
    private Integer roleId;
    @Basic
    @Column(name = "systemUserId", insertable = false, updatable = false)
    private Integer systemUserId;
    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    private Role roleByRoleId;
    @ManyToOne
    @JoinColumn(name = "systemUserId", referencedColumnName = "id")
    private SystemUser systemUserBySystemUserId;

    public SystemUserRole() {
    }

    public SystemUserRole(Integer id, Timestamp createdAt, Timestamp updatedAt, Integer roleId, Integer systemUserId, Role roleByRoleId, SystemUser systemUserBySystemUserId) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roleId = roleId;
        this.systemUserId = systemUserId;
        this.roleByRoleId = roleByRoleId;
        this.systemUserBySystemUserId = systemUserBySystemUserId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(Integer systemUserId) {
        this.systemUserId = systemUserId;
    }

    public Role getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(Role roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }

    public SystemUser getSystemUserBySystemUserId() {
        return systemUserBySystemUserId;
    }

    public void setSystemUserBySystemUserId(SystemUser systemUserBySystemUserId) {
        this.systemUserBySystemUserId = systemUserBySystemUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SystemUserRole that = (SystemUserRole) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) {
            return false;
        }
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) {
            return false;
        }
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) {
            return false;
        }
        if (systemUserId != null ? !systemUserId.equals(that.systemUserId) : that.systemUserId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (systemUserId != null ? systemUserId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SystemUserRole{" + "id=" + id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", roleId=" + roleId + ", systemUserId=" + systemUserId + ", roleByRoleId=" + roleByRoleId + ", systemUserBySystemUserId=" + systemUserBySystemUserId + '}';
    }
}
