package com.textquest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ResultsServlet", value = "/textquest/results")
public class ResultsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        String result = (String) session.getAttribute("result");
        if (session.getAttribute("username") == null || result == null) {
            resp.sendRedirect(PagePaths.START_PAGE);
            return;
        }
        session.setAttribute("isWinner", result.contains("Перемога"));

        getServletContext().getRequestDispatcher(PagePaths.RESULTS_JSP).forward(req, resp);
    }
}
