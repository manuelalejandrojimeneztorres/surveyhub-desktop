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
public class Response {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "beginDate")
    private Timestamp beginDate;
    @Basic
    @Column(name = "endDate")
    private Timestamp endDate;
    @Basic
    @Column(name = "createdAt")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    @Basic
    @Column(name = "surveyId", insertable = false, updatable = false)
    private Integer surveyId;
    @Basic
    @Column(name = "systemUserId", insertable = false, updatable = false)
    private Integer systemUserId;
    @OneToMany(mappedBy = "responseByResponseId")
    private Collection<Answer> answersById;
    @ManyToOne
    @JoinColumn(name = "surveyId", referencedColumnName = "id", nullable = false)
    private Survey surveyBySurveyId;
    @ManyToOne
    @JoinColumn(name = "systemUserId", referencedColumnName = "id", nullable = false)
    private SystemUser systemUserBySystemUserId;

    public Response() {
    }

    public Response(Integer id, Timestamp beginDate, Timestamp endDate, Timestamp createdAt, Timestamp updatedAt, Integer surveyId, Integer systemUserId, Collection<Answer> answersById, Survey surveyBySurveyId, SystemUser systemUserBySystemUserId) {
        this.id = id;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.surveyId = surveyId;
        this.systemUserId = systemUserId;
        this.answersById = answersById;
        this.surveyBySurveyId = surveyBySurveyId;
        this.systemUserBySystemUserId = systemUserBySystemUserId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
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

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public Integer getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(Integer systemUserId) {
        this.systemUserId = systemUserId;
    }

    public Collection<Answer> getAnswersById() {
        return answersById;
    }

    public void setAnswersById(Collection<Answer> answersById) {
        this.answersById = answersById;
    }

    public Survey getSurveyBySurveyId() {
        return surveyBySurveyId;
    }

    public void setSurveyBySurveyId(Survey surveyBySurveyId) {
        this.surveyBySurveyId = surveyBySurveyId;
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

        Response response = (Response) o;

        if (id != null ? !id.equals(response.id) : response.id != null) {
            return false;
        }
        if (beginDate != null ? !beginDate.equals(response.beginDate) : response.beginDate != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(response.endDate) : response.endDate != null) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(response.createdAt) : response.createdAt != null) {
            return false;
        }
        if (updatedAt != null ? !updatedAt.equals(response.updatedAt) : response.updatedAt != null) {
            return false;
        }
        if (surveyId != null ? !surveyId.equals(response.surveyId) : response.surveyId != null) {
            return false;
        }
        if (systemUserId != null ? !systemUserId.equals(response.systemUserId) : response.systemUserId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (beginDate != null ? beginDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (surveyId != null ? surveyId.hashCode() : 0);
        result = 31 * result + (systemUserId != null ? systemUserId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Response{" + "id=" + id + ", beginDate=" + beginDate + ", endDate=" + endDate + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", surveyId=" + surveyId + ", systemUserId=" + systemUserId + ", answersById=" + answersById + ", surveyBySurveyId=" + surveyBySurveyId + ", systemUserBySystemUserId=" + systemUserBySystemUserId + '}';
    }
}
