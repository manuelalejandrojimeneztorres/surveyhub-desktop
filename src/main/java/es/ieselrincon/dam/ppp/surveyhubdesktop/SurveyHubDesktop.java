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
package es.ieselrincon.dam.ppp.surveyhubdesktop;

import com.formdev.flatlaf.FlatLightLaf;
import es.ieselrincon.dam.ppp.surveyhubdesktop.controllers.MainEventController;
import es.ieselrincon.dam.ppp.surveyhubdesktop.controllers.MainController;
import es.ieselrincon.dam.ppp.surveyhubdesktop.views.OnlineSurveySystemView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
public class SurveyHubDesktop {

    private static final Logger LOGGER = Logger.getLogger(SurveyHubDesktop.class.getName());

    public static void main(String[] args) {
        // Enable the 'FlatLaf Light' Look and Feel
        // FlatLightLaf.setup();
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            // ex.printStackTrace();
            LOGGER.log(Level.SEVERE, "Unsupported Look and Feel", ex);
        }

        OnlineSurveySystemView dashboardView = new OnlineSurveySystemView();
        MainEventController mainEventController = new MainEventController(dashboardView);
        MainController mainController = new MainController(dashboardView, mainEventController);
        mainController.startApplication();
    }
}
