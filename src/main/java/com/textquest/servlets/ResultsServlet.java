package com.textquest.servlets;

import com.textquest.utils.PagePaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ResultsServlet", value = "/textquest/results")
public class ResultsServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(ResultsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        String result = (String) session.getAttribute("result");
        if (session.getAttribute("username") == null || result == null) {
            LOGGER.error("Bad session");
            resp.sendRedirect(PagePaths.START_PAGE);
            return;
        }
        getServletContext().getRequestDispatcher(PagePaths.RESULTS_JSP).forward(req, resp);
    }
}
