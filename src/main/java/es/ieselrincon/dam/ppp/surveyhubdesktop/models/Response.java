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
public class Response {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ResponseID")
    private int responseId;
    @Basic
    @Column(name = "SurveyID", insertable = false, updatable = false)
    private int surveyId;
    @Basic
    @Column(name = "RespondentID", insertable = false, updatable = false)
    private int respondentId;
    @Basic
    @Column(name = "BeginDate")
    private String beginDate;
    @Basic
    @Column(name = "EndDate")
    private String endDate;
    @OneToMany(mappedBy = "responseByResponseId")
    private Collection<Answer> answersByResponseId;
    @ManyToOne
    @JoinColumn(name = "SurveyID", referencedColumnName = "SurveyID", nullable = false)
    private Survey surveyBySurveyId;
    @ManyToOne
    @JoinColumn(name = "RespondentID", referencedColumnName = "RespondentID", nullable = false)
    private Respondent respondentByRespondentId;

    public Response() {
    }

    public Response(int responseId, int surveyId, int respondentId, String beginDate, String endDate, Collection<Answer> answersByResponseId, Survey surveyBySurveyId, Respondent respondentByRespondentId) {
        this.responseId = responseId;
        this.surveyId = surveyId;
        this.respondentId = respondentId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.answersByResponseId = answersByResponseId;
        this.surveyBySurveyId = surveyBySurveyId;
        this.respondentByRespondentId = respondentByRespondentId;
    }

    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getRespondentId() {
        return respondentId;
    }

    public void setRespondentId(int respondentId) {
        this.respondentId = respondentId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Collection<Answer> getAnswersByResponseId() {
        return answersByResponseId;
    }

    public void setAnswersByResponseId(Collection<Answer> answersByResponseId) {
        this.answersByResponseId = answersByResponseId;
    }

    public Survey getSurveyBySurveyId() {
        return surveyBySurveyId;
    }

    public void setSurveyBySurveyId(Survey surveyBySurveyId) {
        this.surveyBySurveyId = surveyBySurveyId;
    }

    public Respondent getRespondentByRespondentId() {
        return respondentByRespondentId;
    }

    public void setRespondentByRespondentId(Respondent respondentByRespondentId) {
        this.respondentByRespondentId = respondentByRespondentId;
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

        if (responseId != response.responseId) {
            return false;
        }
        if (surveyId != response.surveyId) {
            return false;
        }
        if (respondentId != response.respondentId) {
            return false;
        }
        if (beginDate != null ? !beginDate.equals(response.beginDate) : response.beginDate != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(response.endDate) : response.endDate != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = responseId;
        result = 31 * result + surveyId;
        result = 31 * result + respondentId;
        result = 31 * result + (beginDate != null ? beginDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Response{" + "responseId=" + responseId + ", surveyId=" + surveyId + ", respondentId=" + respondentId + ", beginDate=" + beginDate + ", endDate=" + endDate + ", answersByResponseId=" + answersByResponseId + ", surveyBySurveyId=" + surveyBySurveyId + ", respondentByRespondentId=" + respondentByRespondentId + '}';
    }
}
