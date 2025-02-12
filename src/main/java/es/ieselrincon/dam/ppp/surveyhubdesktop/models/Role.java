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
import java.util.Collection;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
@Entity
public class Role {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "createdAt")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    @OneToMany(mappedBy = "roleByRoleId")
    private Collection<SystemUserRole> systemUserRolesById;

    public Role() {
    }

    public Role(Integer id, String name, String description, Timestamp createdAt, Timestamp updatedAt, Collection<SystemUserRole> systemUserRolesById) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.systemUserRolesById = systemUserRolesById;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Collection<SystemUserRole> getSystemUserRolesById() {
        return systemUserRolesById;
    }

    public void setSystemUserRolesById(Collection<SystemUserRole> systemUserRolesById) {
        this.systemUserRolesById = systemUserRolesById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Role role = (Role) o;

        if (id != null ? !id.equals(role.id) : role.id != null) {
            return false;
        }
        if (name != null ? !name.equals(role.name) : role.name != null) {
            return false;
        }
        if (description != null ? !description.equals(role.description) : role.description != null) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(role.createdAt) : role.createdAt != null) {
            return false;
        }
        if (updatedAt != null ? !updatedAt.equals(role.updatedAt) : role.updatedAt != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", name=" + name + ", description=" + description + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", systemUserRolesById=" + systemUserRolesById + '}';
    }
}
