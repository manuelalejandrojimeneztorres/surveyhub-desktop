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
public class QuestionType {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "QuestionTypeID")
    private int questionTypeId;
    @Basic
    @Column(name = "QuestionType")
    private String questionType;
    @OneToMany(mappedBy = "questionTypeByQuestionTypeId")
    private Collection<Question> questionsByQuestionTypeId;

    public QuestionType() {
    }

    public QuestionType(int questionTypeId, String questionType, Collection<Question> questionsByQuestionTypeId) {
        this.questionTypeId = questionTypeId;
        this.questionType = questionType;
        this.questionsByQuestionTypeId = questionsByQuestionTypeId;
    }

    public int getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Collection<Question> getQuestionsByQuestionTypeId() {
        return questionsByQuestionTypeId;
    }

    public void setQuestionsByQuestionTypeId(Collection<Question> questionsByQuestionTypeId) {
        this.questionsByQuestionTypeId = questionsByQuestionTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionType that = (QuestionType) o;

        if (questionTypeId != that.questionTypeId) {
            return false;
        }
        if (questionType != null ? !questionType.equals(that.questionType) : that.questionType != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = questionTypeId;
        result = 31 * result + (questionType != null ? questionType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QuestionType{" + "questionTypeId=" + questionTypeId + ", questionType=" + questionType + ", questionsByQuestionTypeId=" + questionsByQuestionTypeId + '}';
    }
}
