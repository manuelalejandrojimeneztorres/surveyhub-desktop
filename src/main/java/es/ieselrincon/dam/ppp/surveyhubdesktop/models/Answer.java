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
public class Answer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "AnswerID")
    private int answerId;
    @Basic
    @Column(name = "ResponseID", insertable = false, updatable = false)
    private int responseId;
    @Basic
    @Column(name = "QuestionID", insertable = false, updatable = false)
    private int questionId;
    @Basic
    @Column(name = "Answer")
    private String answer;
    @ManyToOne
    @JoinColumn(name = "ResponseID", referencedColumnName = "ResponseID", nullable = false)
    private Response responseByResponseId;
    @ManyToOne
    @JoinColumn(name = "QuestionID", referencedColumnName = "QuestionID", nullable = false)
    private Question questionByQuestionId;
    @OneToMany(mappedBy = "answerByAnswerId")
    private Collection<AnswerOption> answerOptionsByAnswerId;

    public Answer() {
    }

    public Answer(int answerId, int responseId, int questionId, String answer, Response responseByResponseId, Question questionByQuestionId, Collection<AnswerOption> answerOptionsByAnswerId) {
        this.answerId = answerId;
        this.responseId = responseId;
        this.questionId = questionId;
        this.answer = answer;
        this.responseByResponseId = responseByResponseId;
        this.questionByQuestionId = questionByQuestionId;
        this.answerOptionsByAnswerId = answerOptionsByAnswerId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Response getResponseByResponseId() {
        return responseByResponseId;
    }

    public void setResponseByResponseId(Response responseByResponseId) {
        this.responseByResponseId = responseByResponseId;
    }

    public Question getQuestionByQuestionId() {
        return questionByQuestionId;
    }

    public void setQuestionByQuestionId(Question questionByQuestionId) {
        this.questionByQuestionId = questionByQuestionId;
    }

    public Collection<AnswerOption> getAnswerOptionsByAnswerId() {
        return answerOptionsByAnswerId;
    }

    public void setAnswerOptionsByAnswerId(Collection<AnswerOption> answerOptionsByAnswerId) {
        this.answerOptionsByAnswerId = answerOptionsByAnswerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Answer answer1 = (Answer) o;

        if (answerId != answer1.answerId) {
            return false;
        }
        if (responseId != answer1.responseId) {
            return false;
        }
        if (questionId != answer1.questionId) {
            return false;
        }
        if (answer != null ? !answer.equals(answer1.answer) : answer1.answer != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = answerId;
        result = 31 * result + responseId;
        result = 31 * result + questionId;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Answer{" + "answerId=" + answerId + ", responseId=" + responseId + ", questionId=" + questionId + ", answer=" + answer + ", responseByResponseId=" + responseByResponseId + ", questionByQuestionId=" + questionByQuestionId + ", answerOptionsByAnswerId=" + answerOptionsByAnswerId + '}';
    }
}
