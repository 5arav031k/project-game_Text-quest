package com.textquest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "InitServlet", value = "/textquest/start")
public class InitServlet extends HttpServlet {
    private static final String INDEX_JSP = "/index.jsp";
    private static final String QUEST_PAGE = "/textquest/quest";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(INDEX_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);

        Questions questions = new Questions();
        session.setAttribute("questions", questions);
        session.setAttribute("username", req.getParameter("username"));

        resp.sendRedirect(QUEST_PAGE);
    }
}
