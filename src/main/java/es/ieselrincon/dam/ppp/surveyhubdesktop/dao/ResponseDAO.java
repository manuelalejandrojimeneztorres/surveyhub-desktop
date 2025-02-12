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
package es.ieselrincon.dam.ppp.surveyhubdesktop.dao;

import es.ieselrincon.dam.ppp.surveyhubdesktop.models.Response;
import es.ieselrincon.dam.ppp.surveyhubdesktop.utils.HibernateUtils;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
public class ResponseDAO {

    static {
        // Inicializar la clase anotada
        HibernateUtils.initializeAnnotatedClass("es.ieselrincon.dam.ppp.surveyhubdesktop.models.Response");
    }

    // Create
    public void save(Response response) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSession()) {
            transaction = session.beginTransaction();
            if (response.getId() == null) {
                response.setCreatedAt(new Timestamp(new Date().getTime()));
            }
            session.save(response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Read
    public Response findById(int id) {
        try (Session session = HibernateUtils.getSession()) {
            return session.get(Response.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update
    public void update(Response response) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSession()) {
            transaction = session.beginTransaction();
            response.setUpdatedAt(new Timestamp(new Date().getTime()));
            session.update(response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete
    public void delete(Response response) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSession()) {
            transaction = session.beginTransaction();
            session.delete(response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Get All
    public List<Response> findAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("from Response", Response.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Count Rows
    public long count() {
        try (Session session = HibernateUtils.getSession()) {
            Query<Long> query = session.createQuery("select count(r.id) from Response r", Long.class);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
