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
public class Answer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "answer")
    private String answer;
    @Basic
    @Column(name = "createdAt")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    @Basic
    @Column(name = "questionId", insertable = false, updatable = false)
    private Integer questionId;
    @Basic
    @Column(name = "responseId", insertable = false, updatable = false)
    private Integer responseId;
    @ManyToOne
    @JoinColumn(name = "questionId", referencedColumnName = "id", nullable = false)
    private Question questionByQuestionId;
    @ManyToOne
    @JoinColumn(name = "responseId", referencedColumnName = "id", nullable = false)
    private Response responseByResponseId;
    @OneToMany(mappedBy = "answerByAnswerId")
    private Collection<AnswerOption> answerOptionsById;

    public Answer() {
    }

    public Answer(Integer id, String answer, Timestamp createdAt, Timestamp updatedAt, Integer questionId, Integer responseId, Question questionByQuestionId, Response responseByResponseId, Collection<AnswerOption> answerOptionsById) {
        this.id = id;
        this.answer = answer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.questionId = questionId;
        this.responseId = responseId;
        this.questionByQuestionId = questionByQuestionId;
        this.responseByResponseId = responseByResponseId;
        this.answerOptionsById = answerOptionsById;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getResponseId() {
        return responseId;
    }

    public void setResponseId(Integer responseId) {
        this.responseId = responseId;
    }

    public Question getQuestionByQuestionId() {
        return questionByQuestionId;
    }

    public void setQuestionByQuestionId(Question questionByQuestionId) {
        this.questionByQuestionId = questionByQuestionId;
    }

    public Response getResponseByResponseId() {
        return responseByResponseId;
    }

    public void setResponseByResponseId(Response responseByResponseId) {
        this.responseByResponseId = responseByResponseId;
    }

    public Collection<AnswerOption> getAnswerOptionsById() {
        return answerOptionsById;
    }

    public void setAnswerOptionsById(Collection<AnswerOption> answerOptionsById) {
        this.answerOptionsById = answerOptionsById;
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

        if (id != null ? !id.equals(answer1.id) : answer1.id != null) {
            return false;
        }
        if (answer != null ? !answer.equals(answer1.answer) : answer1.answer != null) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(answer1.createdAt) : answer1.createdAt != null) {
            return false;
        }
        if (updatedAt != null ? !updatedAt.equals(answer1.updatedAt) : answer1.updatedAt != null) {
            return false;
        }
        if (questionId != null ? !questionId.equals(answer1.questionId) : answer1.questionId != null) {
            return false;
        }
        if (responseId != null ? !responseId.equals(answer1.responseId) : answer1.responseId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (questionId != null ? questionId.hashCode() : 0);
        result = 31 * result + (responseId != null ? responseId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Answer{" + "id=" + id + ", answer=" + answer + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", questionId=" + questionId + ", responseId=" + responseId + ", questionByQuestionId=" + questionByQuestionId + ", responseByResponseId=" + responseByResponseId + ", answerOptionsById=" + answerOptionsById + '}';
    }
}
