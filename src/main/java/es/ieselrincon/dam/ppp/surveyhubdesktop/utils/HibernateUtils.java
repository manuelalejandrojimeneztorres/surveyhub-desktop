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
package es.ieselrincon.dam.ppp.surveyhubdesktop.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
public class HibernateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUtils.class);
    private static SessionFactory sessionFactory;

    static {
        // Cargar las variables de entorno al iniciar
        EnvironmentConfig.loadEnvironmentVariables();
    }

    // Método para inicializar la clase persister
    public static synchronized void initializeAnnotatedClass(String annotatedClassName) {
        if (sessionFactory != null) {
            return;
        }
        try {
            Class<?> annotatedClass = Class.forName(annotatedClassName);
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(annotatedClass);
            sessionFactory = configuration.buildSessionFactory();
        } catch (ClassNotFoundException e) {
            String errorMsg = "Annotated class not found: " + annotatedClassName;
            LOGGER.error(errorMsg, e);
            throw new IllegalArgumentException(errorMsg, e);
        } catch (HibernateException e) {
            String errorMsg = "Error initializing SessionFactory";
            LOGGER.error(errorMsg, e);
            throw new ExceptionInInitializerError(errorMsg);
        }
    }

    // Método para obtener la sesión
    public static Session getSession() {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory is not initialized");
        }
        return sessionFactory.openSession();
    }

    // Método para cerrar la sesión
    public static void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    // Método para cerrar la SessionFactory
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
