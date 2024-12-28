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
public class Question {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "QuestionID")
    private int questionId;
    @Basic
    @Column(name = "SurveyID", insertable = false, updatable = false)
    private int surveyId;
    @Basic
    @Column(name = "QuestionOrder")
    private int questionOrder;
    @Basic
    @Column(name = "QuestionTypeID", insertable = false, updatable = false)
    private int questionTypeId;
    @Basic
    @Column(name = "QuestionText")
    private String questionText;
    @Basic
    @Column(name = "IsMandatory")
    private String isMandatory;
    @OneToMany(mappedBy = "questionByQuestionId")
    private Collection<Answer> answersByQuestionId;
    @ManyToOne
    @JoinColumn(name = "SurveyID", referencedColumnName = "SurveyID", nullable = false)
    private Survey surveyBySurveyId;
    @ManyToOne
    @JoinColumn(name = "QuestionTypeID", referencedColumnName = "QuestionTypeID", nullable = false)
    private QuestionType questionTypeByQuestionTypeId;
    @OneToMany(mappedBy = "questionByQuestionId")
    private Collection<QuestionOption> questionOptionsByQuestionId;

    public Question() {
    }

    public Question(int questionId, int surveyId, int questionOrder, int questionTypeId, String questionText, String isMandatory, Collection<Answer> answersByQuestionId, Survey surveyBySurveyId, QuestionType questionTypeByQuestionTypeId, Collection<QuestionOption> questionOptionsByQuestionId) {
        this.questionId = questionId;
        this.surveyId = surveyId;
        this.questionOrder = questionOrder;
        this.questionTypeId = questionTypeId;
        this.questionText = questionText;
        this.isMandatory = isMandatory;
        this.answersByQuestionId = answersByQuestionId;
        this.surveyBySurveyId = surveyBySurveyId;
        this.questionTypeByQuestionTypeId = questionTypeByQuestionTypeId;
        this.questionOptionsByQuestionId = questionOptionsByQuestionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }

    public int getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(String isMandatory) {
        this.isMandatory = isMandatory;
    }

    public Collection<Answer> getAnswersByQuestionId() {
        return answersByQuestionId;
    }

    public void setAnswersByQuestionId(Collection<Answer> answersByQuestionId) {
        this.answersByQuestionId = answersByQuestionId;
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

    public Collection<QuestionOption> getQuestionOptionsByQuestionId() {
        return questionOptionsByQuestionId;
    }

    public void setQuestionOptionsByQuestionId(Collection<QuestionOption> questionOptionsByQuestionId) {
        this.questionOptionsByQuestionId = questionOptionsByQuestionId;
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

        if (questionId != question.questionId) {
            return false;
        }
        if (surveyId != question.surveyId) {
            return false;
        }
        if (questionOrder != question.questionOrder) {
            return false;
        }
        if (questionTypeId != question.questionTypeId) {
            return false;
        }
        if (questionText != null ? !questionText.equals(question.questionText) : question.questionText != null) {
            return false;
        }
        if (isMandatory != null ? !isMandatory.equals(question.isMandatory) : question.isMandatory != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = questionId;
        result = 31 * result + surveyId;
        result = 31 * result + questionOrder;
        result = 31 * result + questionTypeId;
        result = 31 * result + (questionText != null ? questionText.hashCode() : 0);
        result = 31 * result + (isMandatory != null ? isMandatory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" + "questionId=" + questionId + ", surveyId=" + surveyId + ", questionOrder=" + questionOrder + ", questionTypeId=" + questionTypeId + ", questionText=" + questionText + ", isMandatory=" + isMandatory + ", answersByQuestionId=" + answersByQuestionId + ", surveyBySurveyId=" + surveyBySurveyId + ", questionTypeByQuestionTypeId=" + questionTypeByQuestionTypeId + ", questionOptionsByQuestionId=" + questionOptionsByQuestionId + '}';
    }
}
