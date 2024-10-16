package com.textquest.servlets;

import com.textquest.utils.PagePaths;
import com.textquest.models.Questions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "InitServlet", value = "/textquest/start")
public class InitServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(InitServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(PagePaths.START_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);

        LOGGER.info("initialize questions");
        try {
            Questions questions = new Questions();
            questions.questionsInitializer();
            session.setAttribute("questions", questions);
            session.setAttribute("username", req.getParameter("username"));
        } catch (Exception e) {
            if (e.getCause() instanceof IOException) {
                LOGGER.error("Error reading questions file",e);
                resp.sendRedirect(PagePaths.START_PAGE);
                return;
            }
            LOGGER.error("Error while initializing questions",e);
            resp.sendRedirect(PagePaths.START_PAGE);
            return;
        }

        resp.sendRedirect(PagePaths.QUEST_PAGE);
    }
}
