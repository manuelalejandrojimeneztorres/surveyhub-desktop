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
package es.ieselrincon.dam.ppp.surveyhubdesktop.dao;

import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Answer;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.AnswerOption;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.QuestionOption;
import es.ieselrincon.dam.ppp.surveyhubdesktop.utils.HibernateUtils;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
public class AnswerOptionDAO {

    static {
        // Inicializar la clase anotada
        HibernateUtils.initializeAnnotatedClass("es.ieselrincon.dam.ppp.surveyhubdesktop.models.AnswerOption");
    }

    // Create
    public void save(AnswerOption answerOption) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSession()) {
            transaction = session.beginTransaction();
            session.save(answerOption);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Read
    public AnswerOption findById(int id) {
        try (Session session = HibernateUtils.getSession()) {
            return session.get(AnswerOption.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update
    public void update(AnswerOption answerOption) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSession()) {
            transaction = session.beginTransaction();
            session.update(answerOption);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete
    public void delete(AnswerOption answerOption) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSession()) {
            transaction = session.beginTransaction();
            session.delete(answerOption);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Get All
    public List<AnswerOption> findAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("from AnswerOption", AnswerOption.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Count Rows
    public long count() {
        try (Session session = HibernateUtils.getSession()) {
            Query<Long> query = session.createQuery("select count(a.answerOptionId) from AnswerOption a", Long.class);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Find Answer by ID
    public Answer findAnswerById(int id) {
        try (Session session = HibernateUtils.getSession()) {
            return session.get(Answer.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Find QuestionOption by ID
    public QuestionOption findQuestionOptionById(int id) {
        try (Session session = HibernateUtils.getSession()) {
            return session.get(QuestionOption.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
