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
public class Survey {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "SurveyID")
    private int surveyId;
    @Basic
    @Column(name = "SurveyName")
    private String surveyName;
    @Basic
    @Column(name = "SurveyDescription")
    private String surveyDescription;
    @Basic
    @Column(name = "StartDate")
    private String startDate;
    @Basic
    @Column(name = "EndDate")
    private String endDate;
    @Basic
    @Column(name = "MinResponses")
    private Integer minResponses;
    @Basic
    @Column(name = "MaxResponses")
    private Integer maxResponses;
    @Basic
    @Column(name = "SurveyStatusID", insertable = false, updatable = false)
    private int surveyStatusId;
    @OneToMany(mappedBy = "surveyBySurveyId")
    private Collection<Question> questionsBySurveyId;
    @OneToMany(mappedBy = "surveyBySurveyId")
    private Collection<Response> responsesBySurveyId;
    @ManyToOne
    @JoinColumn(name = "SurveyStatusID", referencedColumnName = "SurveyStatusID", nullable = false)
    private SurveyStatus surveyStatusBySurveyStatusId;

    public Survey() {
    }

    public Survey(int surveyId, String surveyName, String surveyDescription, String startDate, String endDate, Integer minResponses, Integer maxResponses, int surveyStatusId, Collection<Question> questionsBySurveyId, Collection<Response> responsesBySurveyId, SurveyStatus surveyStatusBySurveyStatusId) {
        this.surveyId = surveyId;
        this.surveyName = surveyName;
        this.surveyDescription = surveyDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minResponses = minResponses;
        this.maxResponses = maxResponses;
        this.surveyStatusId = surveyStatusId;
        this.questionsBySurveyId = questionsBySurveyId;
        this.responsesBySurveyId = responsesBySurveyId;
        this.surveyStatusBySurveyStatusId = surveyStatusBySurveyStatusId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getSurveyDescription() {
        return surveyDescription;
    }

    public void setSurveyDescription(String surveyDescription) {
        this.surveyDescription = surveyDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

    public int getSurveyStatusId() {
        return surveyStatusId;
    }

    public void setSurveyStatusId(int surveyStatusId) {
        this.surveyStatusId = surveyStatusId;
    }

    public Collection<Question> getQuestionsBySurveyId() {
        return questionsBySurveyId;
    }

    public void setQuestionsBySurveyId(Collection<Question> questionsBySurveyId) {
        this.questionsBySurveyId = questionsBySurveyId;
    }

    public Collection<Response> getResponsesBySurveyId() {
        return responsesBySurveyId;
    }

    public void setResponsesBySurveyId(Collection<Response> responsesBySurveyId) {
        this.responsesBySurveyId = responsesBySurveyId;
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

        if (surveyId != survey.surveyId) {
            return false;
        }
        if (surveyStatusId != survey.surveyStatusId) {
            return false;
        }
        if (surveyName != null ? !surveyName.equals(survey.surveyName) : survey.surveyName != null) {
            return false;
        }
        if (surveyDescription != null ? !surveyDescription.equals(survey.surveyDescription) : survey.surveyDescription != null) {
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

        return true;
    }

    @Override
    public int hashCode() {
        int result = surveyId;
        result = 31 * result + (surveyName != null ? surveyName.hashCode() : 0);
        result = 31 * result + (surveyDescription != null ? surveyDescription.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (minResponses != null ? minResponses.hashCode() : 0);
        result = 31 * result + (maxResponses != null ? maxResponses.hashCode() : 0);
        result = 31 * result + surveyStatusId;
        return result;
    }

    @Override
    public String toString() {
        return "Survey{" + "surveyId=" + surveyId + ", surveyName=" + surveyName + ", surveyDescription=" + surveyDescription + ", startDate=" + startDate + ", endDate=" + endDate + ", minResponses=" + minResponses + ", maxResponses=" + maxResponses + ", surveyStatusId=" + surveyStatusId + ", questionsBySurveyId=" + questionsBySurveyId + ", responsesBySurveyId=" + responsesBySurveyId + ", surveyStatusBySurveyStatusId=" + surveyStatusBySurveyStatusId + '}';
    }
}
