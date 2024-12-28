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
public class QuestionOption {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "QuestionOptionID")
    private int questionOptionId;
    @Basic
    @Column(name = "QuestionID", insertable = false, updatable = false)
    private int questionId;
    @Basic
    @Column(name = "QuestionOptionOrder")
    private int questionOptionOrder;
    @Basic
    @Column(name = "QuestionOptionValue")
    private String questionOptionValue;
    @OneToMany(mappedBy = "questionOptionByQuestionOptionId")
    private Collection<AnswerOption> answerOptionsByQuestionOptionId;
    @ManyToOne
    @JoinColumn(name = "QuestionID", referencedColumnName = "QuestionID", nullable = false)
    private Question questionByQuestionId;

    public QuestionOption() {
    }

    public QuestionOption(int questionOptionId, int questionId, int questionOptionOrder, String questionOptionValue, Collection<AnswerOption> answerOptionsByQuestionOptionId, Question questionByQuestionId) {
        this.questionOptionId = questionOptionId;
        this.questionId = questionId;
        this.questionOptionOrder = questionOptionOrder;
        this.questionOptionValue = questionOptionValue;
        this.answerOptionsByQuestionOptionId = answerOptionsByQuestionOptionId;
        this.questionByQuestionId = questionByQuestionId;
    }

    public int getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(int questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionOptionOrder() {
        return questionOptionOrder;
    }

    public void setQuestionOptionOrder(int questionOptionOrder) {
        this.questionOptionOrder = questionOptionOrder;
    }

    public String getQuestionOptionValue() {
        return questionOptionValue;
    }

    public void setQuestionOptionValue(String questionOptionValue) {
        this.questionOptionValue = questionOptionValue;
    }

    public Collection<AnswerOption> getAnswerOptionsByQuestionOptionId() {
        return answerOptionsByQuestionOptionId;
    }

    public void setAnswerOptionsByQuestionOptionId(Collection<AnswerOption> answerOptionsByQuestionOptionId) {
        this.answerOptionsByQuestionOptionId = answerOptionsByQuestionOptionId;
    }

    public Question getQuestionByQuestionId() {
        return questionByQuestionId;
    }

    public void setQuestionByQuestionId(Question questionByQuestionId) {
        this.questionByQuestionId = questionByQuestionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionOption that = (QuestionOption) o;

        if (questionOptionId != that.questionOptionId) {
            return false;
        }
        if (questionId != that.questionId) {
            return false;
        }
        if (questionOptionOrder != that.questionOptionOrder) {
            return false;
        }
        if (questionOptionValue != null ? !questionOptionValue.equals(that.questionOptionValue) : that.questionOptionValue != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = questionOptionId;
        result = 31 * result + questionId;
        result = 31 * result + questionOptionOrder;
        result = 31 * result + (questionOptionValue != null ? questionOptionValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QuestionOption{" + "questionOptionId=" + questionOptionId + ", questionId=" + questionId + ", questionOptionOrder=" + questionOptionOrder + ", questionOptionValue=" + questionOptionValue + ", answerOptionsByQuestionOptionId=" + answerOptionsByQuestionOptionId + ", questionByQuestionId=" + questionByQuestionId + '}';
    }
}
