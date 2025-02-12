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
public class Survey {

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
    @Column(name = "startDate")
    private Timestamp startDate;
    @Basic
    @Column(name = "endDate")
    private Timestamp endDate;
    @Basic
    @Column(name = "minResponses")
    private Integer minResponses;
    @Basic
    @Column(name = "maxResponses")
    private Integer maxResponses;
    @Basic
    @Column(name = "createdAt")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    @Basic
    @Column(name = "surveyStatusId", insertable = false, updatable = false)
    private Integer surveyStatusId;
    @OneToMany(mappedBy = "surveyBySurveyId")
    private Collection<Question> questionsById;
    @OneToMany(mappedBy = "surveyBySurveyId")
    private Collection<Response> responsesById;
    @ManyToOne
    @JoinColumn(name = "surveyStatusId", referencedColumnName = "id", nullable = false)
    private SurveyStatus surveyStatusBySurveyStatusId;

    public Survey() {
    }

    public Survey(Integer id, String name, String description, Timestamp startDate, Timestamp endDate, Integer minResponses, Integer maxResponses, Timestamp createdAt, Timestamp updatedAt, Integer surveyStatusId, Collection<Question> questionsById, Collection<Response> responsesById, SurveyStatus surveyStatusBySurveyStatusId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minResponses = minResponses;
        this.maxResponses = maxResponses;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.surveyStatusId = surveyStatusId;
        this.questionsById = questionsById;
        this.responsesById = responsesById;
        this.surveyStatusBySurveyStatusId = surveyStatusBySurveyStatusId;
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

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Integer getMinResponses() {
        return minResponses;
    }

    public void setMinResponses(Integer minResponses) {
        this.minResponses = minResponses;
    }

    public Integer getMaxResponses() {
        return maxResponses;
    }

    public void setMaxResponses(Integer maxResponses) {
        this.maxResponses = maxResponses;
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

    public Integer getSurveyStatusId() {
        return surveyStatusId;
    }

    public void setSurveyStatusId(Integer surveyStatusId) {
        this.surveyStatusId = surveyStatusId;
    }

    public Collection<Question> getQuestionsById() {
        return questionsById;
    }

    public void setQuestionsById(Collection<Question> questionsById) {
        this.questionsById = questionsById;
    }

    public Collection<Response> getResponsesById() {
        return responsesById;
    }

    public void setResponsesById(Collection<Response> responsesById) {
        this.responsesById = responsesById;
    }

    public SurveyStatus getSurveyStatusBySurveyStatusId() {
        return surveyStatusBySurveyStatusId;
    }

    public void setSurveyStatusBySurveyStatusId(SurveyStatus surveyStatusBySurveyStatusId) {
        this.surveyStatusBySurveyStatusId = surveyStatusBySurveyStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Survey survey = (Survey) o;

        if (id != null ? !id.equals(survey.id) : survey.id != null) {
            return false;
        }
        if (name != null ? !name.equals(survey.name) : survey.name != null) {
            return false;
        }
        if (description != null ? !description.equals(survey.description) : survey.description != null) {
            return false;
        }
        if (startDate != null ? !startDate.equals(survey.startDate) : survey.startDate != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(survey.endDate) : survey.endDate != null) {
            return false;
        }
        if (minResponses != null ? !minResponses.equals(survey.minResponses) : survey.minResponses != null) {
            return false;
        }
        if (maxResponses != null ? !maxResponses.equals(survey.maxResponses) : survey.maxResponses != null) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(survey.createdAt) : survey.createdAt != null) {
            return false;
        }
        if (updatedAt != null ? !updatedAt.equals(survey.updatedAt) : survey.updatedAt != null) {
            return false;
        }
        if (surveyStatusId != null ? !surveyStatusId.equals(survey.surveyStatusId) : survey.surveyStatusId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (minResponses != null ? minResponses.hashCode() : 0);
        result = 31 * result + (maxResponses != null ? maxResponses.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (surveyStatusId != null ? surveyStatusId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Survey{" + "id=" + id + ", name=" + name + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", minResponses=" + minResponses + ", maxResponses=" + maxResponses + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", surveyStatusId=" + surveyStatusId + ", questionsById=" + questionsById + ", responsesById=" + responsesById + ", surveyStatusBySurveyStatusId=" + surveyStatusBySurveyStatusId + '}';
    }
}
