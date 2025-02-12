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
package es.ieselrincon.dam.ppp.surveyhubdesktop.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
public class BCryptUtils {

    private static final int SALT_ROUNDS = 12;

    public static String hashPassword(String plainTextPassword) {
        String salt = BCrypt.gensalt(SALT_ROUNDS);
        return BCrypt.hashpw(plainTextPassword, salt);
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainTextPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
