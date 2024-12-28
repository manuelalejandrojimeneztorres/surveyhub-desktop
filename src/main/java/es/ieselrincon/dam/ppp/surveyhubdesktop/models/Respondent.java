/*
 * Copyright 2024 Manuel Alejandro Jiménez Torres.
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

import java.util.Collection;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
@Entity
public class Respondent {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "RespondentID")
    private int respondentId;
    @Basic
    @Column(name = "LoginName")
    private String loginName;
    @Basic
    @Column(name = "RespondentFullName")
    private String respondentFullName;
    @Basic
    @Column(name = "EmailAddress")
    private String emailAddress;
    @OneToMany(mappedBy = "respondentByRespondentId")
    private Collection<Response> responsesByRespondentId;

    public Respondent() {
    }

    public Respondent(int respondentId, String loginName, String respondentFullName, String emailAddress, Collection<Response> responsesByRespondentId) {
        this.respondentId = respondentId;
        this.loginName = loginName;
        this.respondentFullName = respondentFullName;
        this.emailAddress = emailAddress;
        this.responsesByRespondentId = responsesByRespondentId;
    }

    public int getRespondentId() {
        return respondentId;
    }

    public void setRespondentId(int respondentId) {
        this.respondentId = respondentId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRespondentFullName() {
        return respondentFullName;
    }

    public void setRespondentFullName(String respondentFullName) {
        this.respondentFullName = respondentFullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Collection<Response> getResponsesByRespondentId() {
        return responsesByRespondentId;
    }

    public void setResponsesByRespondentId(Collection<Response> responsesByRespondentId) {
        this.responsesByRespondentId = responsesByRespondentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Respondent that = (Respondent) o;

        if (respondentId != that.respondentId) {
            return false;
        }
        if (loginName != null ? !loginName.equals(that.loginName) : that.loginName != null) {
            return false;
        }
        if (respondentFullName != null ? !respondentFullName.equals(that.respondentFullName) : that.respondentFullName != null) {
            return false;
        }
        if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = respondentId;
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (respondentFullName != null ? respondentFullName.hashCode() : 0);
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Respondent{" + "respondentId=" + respondentId + ", loginName=" + loginName + ", respondentFullName=" + respondentFullName + ", emailAddress=" + emailAddress + ", responsesByRespondentId=" + responsesByRespondentId + '}';
    }
}
