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
public class SystemUser {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "loginName")
    private String loginName;
    @Basic
    @Column(name = "firstName")
    private String firstName;
    @Basic
    @Column(name = "lastName")
    private String lastName;
    @Basic
    @Column(name = "emailAddress")
    private String emailAddress;
    @Basic
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Basic
    @Column(name = "passwordHash")
    private String passwordHash;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "tokenVersion")
    private Integer tokenVersion;
    @Basic
    @Column(name = "profilePicture")
    private String profilePicture;
    @Basic
    @Column(name = "lastLoginAt")
    private Timestamp lastLoginAt;
    @Basic
    @Column(name = "lastPasswordChangeAt")
    private Timestamp lastPasswordChangeAt;
    @Basic
    @Column(name = "createdAt")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    @OneToMany(mappedBy = "systemUserBySystemUserId")
    private Collection<Response> responsesById;
    @OneToMany(mappedBy = "systemUserBySystemUserId")
    private Collection<SystemUserRole> systemUserRolesById;

    public SystemUser() {
    }

    public SystemUser(Integer id, String loginName, String firstName, String lastName, String emailAddress, String phoneNumber, String passwordHash, String status, Integer tokenVersion, String profilePicture, Timestamp lastLoginAt, Timestamp lastPasswordChangeAt, Timestamp createdAt, Timestamp updatedAt, Collection<Response> responsesById, Collection<SystemUserRole> systemUserRolesById) {
        this.id = id;
        this.loginName = loginName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.status = status;
        this.tokenVersion = tokenVersion;
        this.profilePicture = profilePicture;
        this.lastLoginAt = lastLoginAt;
        this.lastPasswordChangeAt = lastPasswordChangeAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.responsesById = responsesById;
        this.systemUserRolesById = systemUserRolesById;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTokenVersion() {
        return tokenVersion;
    }

    public void setTokenVersion(Integer tokenVersion) {
        this.tokenVersion = tokenVersion;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Timestamp getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Timestamp lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Timestamp getLastPasswordChangeAt() {
        return lastPasswordChangeAt;
    }

    public void setLastPasswordChangeAt(Timestamp lastPasswordChangeAt) {
        this.lastPasswordChangeAt = lastPasswordChangeAt;
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

    public Collection<Response> getResponsesById() {
        return responsesById;
    }

    public void setResponsesById(Collection<Response> responsesById) {
        this.responsesById = responsesById;
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

        SystemUser that = (SystemUser) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (loginName != null ? !loginName.equals(that.loginName) : that.loginName != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) {
            return false;
        }
        if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) {
            return false;
        }
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) {
            return false;
        }
        if (passwordHash != null ? !passwordHash.equals(that.passwordHash) : that.passwordHash != null) {
            return false;
        }
        if (status != null ? !status.equals(that.status) : that.status != null) {
            return false;
        }
        if (tokenVersion != null ? !tokenVersion.equals(that.tokenVersion) : that.tokenVersion != null) {
            return false;
        }
        if (profilePicture != null ? !profilePicture.equals(that.profilePicture) : that.profilePicture != null) {
            return false;
        }
        if (lastLoginAt != null ? !lastLoginAt.equals(that.lastLoginAt) : that.lastLoginAt != null) {
            return false;
        }
        if (lastPasswordChangeAt != null ? !lastPasswordChangeAt.equals(that.lastPasswordChangeAt) : that.lastPasswordChangeAt != null) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) {
            return false;
        }
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (tokenVersion != null ? tokenVersion.hashCode() : 0);
        result = 31 * result + (profilePicture != null ? profilePicture.hashCode() : 0);
        result = 31 * result + (lastLoginAt != null ? lastLoginAt.hashCode() : 0);
        result = 31 * result + (lastPasswordChangeAt != null ? lastPasswordChangeAt.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SystemUser{" + "id=" + id + ", loginName=" + loginName + ", firstName=" + firstName + ", lastName=" + lastName + ", emailAddress=" + emailAddress + ", phoneNumber=" + phoneNumber + ", passwordHash=" + passwordHash + ", status=" + status + ", tokenVersion=" + tokenVersion + ", profilePicture=" + profilePicture + ", lastLoginAt=" + lastLoginAt + ", lastPasswordChangeAt=" + lastPasswordChangeAt + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", responsesById=" + responsesById + ", systemUserRolesById=" + systemUserRolesById + '}';
    }
}
