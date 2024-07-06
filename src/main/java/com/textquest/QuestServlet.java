package com.textquest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "QuestServlet", value = "/textquest/quest")
public class QuestServlet extends HttpServlet {
    private static final String QUEST_JSP = "/quest.jsp";
    private static final String RESULTS_PAGE = "/textquest/results";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        Questions questions = (Questions) session.getAttribute("questions");
        String question = (String) session.getAttribute("question");
        String answer = req.getParameter("answer");
        String nextQuestion = questions.getNextQuestion(question, answer);

        if (nextQuestion.contains("Перемога") || nextQuestion.contains("Поразка")) {
            session.setAttribute("result", nextQuestion);
            resp.sendRedirect(RESULTS_PAGE);
            return;
        }

        session.setAttribute("question", nextQuestion);
        session.setAttribute("first_answer", questions.getFirstAnswer(nextQuestion));
        session.setAttribute("second_answer", questions.getSecondAnswer(nextQuestion));

        getServletContext().getRequestDispatcher(QUEST_JSP).forward(req, resp);
    }
}
