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
public class QuestionOption {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "`order`")
    private Integer order;
    @Basic
    @Column(name = "value")
    private String value;
    @Basic
    @Column(name = "createdAt")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    @Basic
    @Column(name = "questionId", insertable = false, updatable = false)
    private Integer questionId;
    @OneToMany(mappedBy = "questionOptionByQuestionOptionId")
    private Collection<AnswerOption> answerOptionsById;
    @ManyToOne
    @JoinColumn(name = "questionId", referencedColumnName = "id")
    private Question questionByQuestionId;

    public QuestionOption() {
    }

    public QuestionOption(Integer id, Integer order, String value, Timestamp createdAt, Timestamp updatedAt, Integer questionId, Collection<AnswerOption> answerOptionsById, Question questionByQuestionId) {
        this.id = id;
        this.order = order;
        this.value = value;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.questionId = questionId;
        this.answerOptionsById = answerOptionsById;
        this.questionByQuestionId = questionByQuestionId;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Collection<AnswerOption> getAnswerOptionsById() {
        return answerOptionsById;
    }

    public void setAnswerOptionsById(Collection<AnswerOption> answerOptionsById) {
        this.answerOptionsById = answerOptionsById;
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

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (order != null ? !order.equals(that.order) : that.order != null) {
            return false;
        }
        if (value != null ? !value.equals(that.value) : that.value != null) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) {
            return false;
        }
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) {
            return false;
        }
        if (questionId != null ? !questionId.equals(that.questionId) : that.questionId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (questionId != null ? questionId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QuestionOption{" + "id=" + id + ", order=" + order + ", value=" + value + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", questionId=" + questionId + ", answerOptionsById=" + answerOptionsById + ", questionByQuestionId=" + questionByQuestionId + '}';
    }
}
