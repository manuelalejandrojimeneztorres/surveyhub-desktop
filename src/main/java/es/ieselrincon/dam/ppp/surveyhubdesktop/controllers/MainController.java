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
package es.ieselrincon.dam.ppp.surveyhubdesktop.controllers;

import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.AnswerDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.AnswerOptionDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.QuestionDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.QuestionOptionDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.QuestionTypeDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.RespondentDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.ResponseDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.SurveyDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.dao.SurveyStatusDAO;
import es.ieselrincon.dam.ppp.surveyhubdesktop.utils.ComponentUtils;
import es.ieselrincon.dam.ppp.surveyhubdesktop.views.OnlineSurveySystemView;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

/**
 *
 * @author Manuel Alejandro Jiménez Torres
 */
public class MainController {

    private final OnlineSurveySystemView dashboardView;
    private final MainEventController mainEventController;

    public MainController(OnlineSurveySystemView dashboardView, MainEventController mainEventController) {
        // Inicializar la vista principal
        this.dashboardView = dashboardView;
        // Crear el controlador de eventos y pasar la vista principal
        this.mainEventController = mainEventController;
        customizeComponents();
        initializeControllers();
    }

    private void customizeComponents() {
        // Aquí puedes obtener los iconos que deseas usar
        Icon[] icons = {
            new ImageIcon(getClass().getResource("/images/icons/tab_icons/survey-status-16x16.png")),
            new ImageIcon(getClass().getResource("/images/icons/tab_icons/survey-16x16.png")),
            new ImageIcon(getClass().getResource("/images/icons/tab_icons/question-type-16x16.png")),
            new ImageIcon(getClass().getResource("/images/icons/tab_icons/question-16x16.png")),
            new ImageIcon(getClass().getResource("/images/icons/tab_icons/question-option-16x16.png")),
            new ImageIcon(getClass().getResource("/images/icons/tab_icons/respondent-16x16.png")),
            new ImageIcon(getClass().getResource("/images/icons/tab_icons/response-16x16.png")),
            new ImageIcon(getClass().getResource("/images/icons/tab_icons/answer-16x16.png")),
            new ImageIcon(getClass().getResource("/images/icons/tab_icons/answer-option-16x16.png"))
        };
        // Asegúrate de que este método se ejecute en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Personaliza los componentes aquí
            ComponentUtils.setTabbedPaneIcons(dashboardView.getMainViewJTabbedPane(), icons);
        });
    }

    private void initializeControllers() {
        // Inicializar los controladores de cada pestaña
        SurveyStatusDAO surveyStatusDAO = new SurveyStatusDAO();
        new SurveyStatusEventController(dashboardView, surveyStatusDAO);

        SurveyDAO surveyDAO = new SurveyDAO();
        new SurveyEventController(dashboardView, surveyDAO);

        QuestionTypeDAO questionTypeDAO = new QuestionTypeDAO();
        new QuestionTypeEventController(dashboardView, questionTypeDAO);

        QuestionDAO questionDAO = new QuestionDAO();
        new QuestionEventController(dashboardView, questionDAO, surveyDAO, questionTypeDAO);

        QuestionOptionDAO questionOptionDAO = new QuestionOptionDAO();
        new QuestionOptionEventController(dashboardView, questionOptionDAO, questionDAO);

        RespondentDAO respondentDAO = new RespondentDAO();
        new RespondentEventController(dashboardView, respondentDAO);

        ResponseDAO responseDAO = new ResponseDAO();
        new ResponseEventController(dashboardView, responseDAO, surveyDAO, respondentDAO);

        AnswerDAO answerDAO = new AnswerDAO();
        new AnswerEventController(dashboardView, answerDAO, responseDAO, questionDAO);

        AnswerOptionDAO answerOptionDAO = new AnswerOptionDAO();
        new AnswerOptionEventController(dashboardView, answerOptionDAO, answerDAO, questionOptionDAO);
    }

    public void startApplication() {
        // Mostrar la vista principal
        SwingUtilities.invokeLater(() -> {
            dashboardView.pack();
            dashboardView.setLocationRelativeTo(null);
            dashboardView.setVisible(true);
        });
    }
}
