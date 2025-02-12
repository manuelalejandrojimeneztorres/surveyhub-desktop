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
public class Question {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "`order`")
    private Integer order;
    @Basic
    @Column(name = "text")
    private String text;
    @Basic
    @Column(name = "isMandatory")
    private String isMandatory;
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
    @Column(name = "questionTypeId", insertable = false, updatable = false)
    private Integer questionTypeId;
    @OneToMany(mappedBy = "questionByQuestionId")
    private Collection<Answer> answersById;
    @ManyToOne
    @JoinColumn(name = "surveyId", referencedColumnName = "id", nullable = false)
    private Survey surveyBySurveyId;
    @ManyToOne
    @JoinColumn(name = "questionTypeId", referencedColumnName = "id", nullable = false)
    private QuestionType questionTypeByQuestionTypeId;
    @OneToMany(mappedBy = "questionByQuestionId")
    private Collection<QuestionOption> questionOptionsById;

    public Question() {
    }

    public Question(Integer id, Integer order, String text, String isMandatory, Timestamp createdAt, Timestamp updatedAt, Integer surveyId, Integer questionTypeId, Collection<Answer> answersById, Survey surveyBySurveyId, QuestionType questionTypeByQuestionTypeId, Collection<QuestionOption> questionOptionsById) {
        this.id = id;
        this.order = order;
        this.text = text;
        this.isMandatory = isMandatory;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.surveyId = surveyId;
        this.questionTypeId = questionTypeId;
        this.answersById = answersById;
        this.surveyBySurveyId = surveyBySurveyId;
        this.questionTypeByQuestionTypeId = questionTypeByQuestionTypeId;
        this.questionOptionsById = questionOptionsById;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(String isMandatory) {
        this.isMandatory = isMandatory;
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

    public Integer getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(Integer questionTypeId) {
        this.questionTypeId = questionTypeId;
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

    public QuestionType getQuestionTypeByQuestionTypeId() {
        return questionTypeByQuestionTypeId;
    }

    public void setQuestionTypeByQuestionTypeId(QuestionType questionTypeByQuestionTypeId) {
        this.questionTypeByQuestionTypeId = questionTypeByQuestionTypeId;
    }

    public Collection<QuestionOption> getQuestionOptionsById() {
        return questionOptionsById;
    }

    public void setQuestionOptionsById(Collection<QuestionOption> questionOptionsById) {
        this.questionOptionsById = questionOptionsById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Question question = (Question) o;

        if (id != null ? !id.equals(question.id) : question.id != null) {
            return false;
        }
        if (order != null ? !order.equals(question.order) : question.order != null) {
            return false;
        }
        if (text != null ? !text.equals(question.text) : question.text != null) {
            return false;
        }
        if (isMandatory != null ? !isMandatory.equals(question.isMandatory) : question.isMandatory != null) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(question.createdAt) : question.createdAt != null) {
            return false;
        }
        if (updatedAt != null ? !updatedAt.equals(question.updatedAt) : question.updatedAt != null) {
            return false;
        }
        if (surveyId != null ? !surveyId.equals(question.surveyId) : question.surveyId != null) {
            return false;
        }
        if (questionTypeId != null ? !questionTypeId.equals(question.questionTypeId) : question.questionTypeId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (isMandatory != null ? isMandatory.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (surveyId != null ? surveyId.hashCode() : 0);
        result = 31 * result + (questionTypeId != null ? questionTypeId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", order=" + order + ", text=" + text + ", isMandatory=" + isMandatory + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", surveyId=" + surveyId + ", questionTypeId=" + questionTypeId + ", answersById=" + answersById + ", surveyBySurveyId=" + surveyBySurveyId + ", questionTypeByQuestionTypeId=" + questionTypeByQuestionTypeId + ", questionOptionsById=" + questionOptionsById + '}';
    }
}
