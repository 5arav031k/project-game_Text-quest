package com.textquest.servlets;

import static org.mockito.Mockito.*;

import com.textquest.models.Questions;
import com.textquest.utils.PagePaths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class QuestServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private Questions questions;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ServletContext servletContext;
    @InjectMocks
    private QuestServlet questServlet;
    private static AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() throws ServletException {
        autoCloseable = MockitoAnnotations.openMocks(this);
        when(request.getSession(true)).thenReturn(session);
        when(request.getRequestDispatcher(PagePaths.QUEST_JSP)).thenReturn(requestDispatcher);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher(PagePaths.QUEST_JSP)).thenReturn(requestDispatcher);
        questServlet.init(servletConfig);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void shouldSendRedirectToStartPageWhenUserNotLoggedIn() throws Exception {
        when(session.getAttribute("username")).thenReturn(null);
        questServlet.doGet(request, response);
        verify(response).sendRedirect(PagePaths.START_PAGE);
    }

    @Test
    public void shouldAddNextQuestion() throws Exception {
        when(session.getAttribute("username")).thenReturn("testUser");
        when(session.getAttribute("questions")).thenReturn(questions);
        when(session.getAttribute("question")).thenReturn("currentQuestion");
        when(request.getParameter("answer")).thenReturn("currentAnswer");
        when(questions.getNextQuestion("currentQuestion", "currentAnswer")).thenReturn("nextQuestion");
        when(questions.getFirstAnswer("nextQuestion")).thenReturn("firstAnswer");
        when(questions.getSecondAnswer("nextQuestion")).thenReturn("secondAnswer");

        questServlet.doGet(request, response);

        verify(session).setAttribute("question", "nextQuestion");
        verify(session).setAttribute("first_answer", "firstAnswer");
        verify(session).setAttribute("second_answer", "secondAnswer");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldRedirectToResultsPageWhenNextQuestionIsFinal() throws Exception {
        when(session.getAttribute("username")).thenReturn("testUser");
        when(session.getAttribute("questions")).thenReturn(questions);
        when(session.getAttribute("question")).thenReturn("currentQuestion");
        when(request.getParameter("answer")).thenReturn("currentAnswer");
        when(questions.getNextQuestion("currentQuestion", "currentAnswer")).thenReturn("Перемога");

        questServlet.doGet(request, response);

        verify(session).setAttribute("result", "Перемога");
        verify(response).sendRedirect(PagePaths.RESULTS_PAGE);
    }
}