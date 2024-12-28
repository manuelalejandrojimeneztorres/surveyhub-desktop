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

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
@Entity
public class AnswerOption {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "AnswerOptionID")
    private int answerOptionId;
    @Basic
    @Column(name = "AnswerID", insertable = false, updatable = false)
    private int answerId;
    @Basic
    @Column(name = "QuestionOptionID", insertable = false, updatable = false)
    private int questionOptionId;
    @ManyToOne
    @JoinColumn(name = "AnswerID", referencedColumnName = "AnswerID", nullable = false)
    private Answer answerByAnswerId;
    @ManyToOne
    @JoinColumn(name = "QuestionOptionID", referencedColumnName = "QuestionOptionID", nullable = false)
    private QuestionOption questionOptionByQuestionOptionId;

    public AnswerOption() {
    }

    public AnswerOption(int answerOptionId, int answerId, int questionOptionId, Answer answerByAnswerId, QuestionOption questionOptionByQuestionOptionId) {
        this.answerOptionId = answerOptionId;
        this.answerId = answerId;
        this.questionOptionId = questionOptionId;
        this.answerByAnswerId = answerByAnswerId;
        this.questionOptionByQuestionOptionId = questionOptionByQuestionOptionId;
    }

    public int getAnswerOptionId() {
        return answerOptionId;
    }

    public void setAnswerOptionId(int answerOptionId) {
        this.answerOptionId = answerOptionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(int questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public Answer getAnswerByAnswerId() {
        return answerByAnswerId;
    }

    public void setAnswerByAnswerId(Answer answerByAnswerId) {
        this.answerByAnswerId = answerByAnswerId;
    }

    public QuestionOption getQuestionOptionByQuestionOptionId() {
        return questionOptionByQuestionOptionId;
    }

    public void setQuestionOptionByQuestionOptionId(QuestionOption questionOptionByQuestionOptionId) {
        this.questionOptionByQuestionOptionId = questionOptionByQuestionOptionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnswerOption that = (AnswerOption) o;

        if (answerOptionId != that.answerOptionId) {
            return false;
        }
        if (answerId != that.answerId) {
            return false;
        }
        if (questionOptionId != that.questionOptionId) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = answerOptionId;
        result = 31 * result + answerId;
        result = 31 * result + questionOptionId;
        return result;
    }

    @Override
    public String toString() {
        return "AnswerOption{" + "answerOptionId=" + answerOptionId + ", answerId=" + answerId + ", questionOptionId=" + questionOptionId + ", answerByAnswerId=" + answerByAnswerId + ", questionOptionByQuestionOptionId=" + questionOptionByQuestionOptionId + '}';
    }
}
