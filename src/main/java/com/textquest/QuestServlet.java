package com.textquest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "QuestServlet", value = "/textquest/quest")
public class QuestServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(QuestServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        if (session.getAttribute("username") == null) {
            LOGGER.debug("User not logged in");
            resp.sendRedirect(PagePaths.START_PAGE);
            return;
        }
        try {
            Questions questions = (Questions) session.getAttribute("questions");
            String question = (String) session.getAttribute("question");
            String answer = req.getParameter("answer");
            String nextQuestion = questions.getNextQuestion(question, answer);

            if (nextQuestion.contains("Перемога") || nextQuestion.contains("Поразка")) {
                session.setAttribute("result", nextQuestion);
                LOGGER.debug("opening results page");
                resp.sendRedirect(PagePaths.RESULTS_PAGE);
                return;
            }

            session.setAttribute("question", nextQuestion);
            session.setAttribute("first_answer", questions.getFirstAnswer(nextQuestion));
            session.setAttribute("second_answer", questions.getSecondAnswer(nextQuestion));
        } catch (Exception e) {
            LOGGER.error("Bad session",e);
            resp.sendRedirect(PagePaths.START_PAGE);
            return;
        }
        getServletContext().getRequestDispatcher(PagePaths.QUEST_JSP).forward(req, resp);
    }
}
