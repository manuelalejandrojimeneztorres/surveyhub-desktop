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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
public class EnvironmentConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentConfig.class);

    // Clase de utilidad: Constructor privado para prevenir instanciación
    private EnvironmentConfig() {
        throw new UnsupportedOperationException("Clase de utilidad.");
    }

    /**
     * Configura las propiedades del sistema basándose en las variables de
     * entorno.
     */
    public static void loadEnvironmentVariables() {
        setSystemProperty("DB_URL", "jdbc:mysql://localhost:3306/onlinesurveysystem");
        setSystemProperty("DB_USERNAME", "root");
        setSystemProperty("DB_PASSWORD", "YourStrongDefaultDatabasePassword");
    }

    /**
     * Establece una propiedad del sistema si la variable de entorno está
     * definida. Si no está definida, utiliza un valor predeterminado
     * (opcional).
     */
    private static void setSystemProperty(String propertyName, String defaultValue) {
        String envValue = System.getenv(propertyName);
        if (envValue != null && !envValue.isEmpty()) {
            System.setProperty(propertyName, envValue);
            LOGGER.info("Loaded environment variable: {}", propertyName);
        } else {
            System.setProperty(propertyName, defaultValue);
            LOGGER.warn("Environment variable {} not found. Using default value.", propertyName);
        }
    }
}
