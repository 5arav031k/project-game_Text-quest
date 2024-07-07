package com.textquest.servlets;

import com.textquest.utils.PagePaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RestartServlet", value = "/textquest/restart")
public class RestartServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(RestartServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info("Quest restarted");
        req.getSession().invalidate();
        resp.sendRedirect(PagePaths.START_PAGE);
    }
}
