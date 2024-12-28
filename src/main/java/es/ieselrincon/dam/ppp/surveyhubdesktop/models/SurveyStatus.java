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
public class SurveyStatus {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "SurveyStatusID")
    private int surveyStatusId;
    @Basic
    @Column(name = "SurveyStatus")
    private String surveyStatus;
    @OneToMany(mappedBy = "surveyStatusBySurveyStatusId")
    private Collection<Survey> surveysBySurveyStatusId;

    public SurveyStatus() {
    }

    public SurveyStatus(int surveyStatusId, String surveyStatus, Collection<Survey> surveysBySurveyStatusId) {
        this.surveyStatusId = surveyStatusId;
        this.surveyStatus = surveyStatus;
        this.surveysBySurveyStatusId = surveysBySurveyStatusId;
    }

    public int getSurveyStatusId() {
        return surveyStatusId;
    }

    public void setSurveyStatusId(int surveyStatusId) {
        this.surveyStatusId = surveyStatusId;
    }

    public String getSurveyStatus() {
        return surveyStatus;
    }

    public void setSurveyStatus(String surveyStatus) {
        this.surveyStatus = surveyStatus;
    }

    public Collection<Survey> getSurveysBySurveyStatusId() {
        return surveysBySurveyStatusId;
    }

    public void setSurveysBySurveyStatusId(Collection<Survey> surveysBySurveyStatusId) {
        this.surveysBySurveyStatusId = surveysBySurveyStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SurveyStatus that = (SurveyStatus) o;

        if (surveyStatusId != that.surveyStatusId) {
            return false;
        }
        if (surveyStatus != null ? !surveyStatus.equals(that.surveyStatus) : that.surveyStatus != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = surveyStatusId;
        result = 31 * result + (surveyStatus != null ? surveyStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SurveyStatus{" + "surveyStatusId=" + surveyStatusId + ", surveyStatus=" + surveyStatus + ", surveysBySurveyStatusId=" + surveysBySurveyStatusId + '}';
    }
}
