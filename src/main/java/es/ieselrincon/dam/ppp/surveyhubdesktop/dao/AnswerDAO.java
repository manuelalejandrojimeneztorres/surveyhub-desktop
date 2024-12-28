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
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Question;
import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Response;
import es.ieselrincon.dam.ppp.surveyhubdesktop.utils.HibernateUtils;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
public class AnswerDAO {

    static {
        // Inicializar la clase anotada
        HibernateUtils.initializeAnnotatedClass("es.ieselrincon.dam.ppp.surveyhubdesktop.models.Answer");
    }

    // Create
    public void save(Answer answer) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSession()) {
            transaction = session.beginTransaction();
            session.save(answer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Read
    public Answer findById(int id) {
        try (Session session = HibernateUtils.getSession()) {
            return session.get(Answer.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update
    public void update(Answer answer) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSession()) {
            transaction = session.beginTransaction();
            session.update(answer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete
    public void delete(Answer answer) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSession()) {
            transaction = session.beginTransaction();
            session.delete(answer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Get All
    public List<Answer> findAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("from Answer", Answer.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Count Rows
    public long count() {
        try (Session session = HibernateUtils.getSession()) {
            Query<Long> query = session.createQuery("select count(a.answerId) from Answer a", Long.class);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Find Response by ID
    public Response findResponseById(int id) {
        try (Session session = HibernateUtils.getSession()) {
            return session.get(Response.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Find Question by ID
    public Question findQuestionById(int id) {
        try (Session session = HibernateUtils.getSession()) {
            return session.get(Question.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
