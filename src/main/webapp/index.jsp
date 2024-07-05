<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Text Quest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/index.css">
</head>
<body>
    <h1>Welcome to Text Quest</h1>
    <section class="login-form">
        <form method="post" action="${pageContext.request.contextPath}/textquest/start">
            <input class="username-input" name="username" type="text" placeholder="Username" required>
            <br>
            <input class="submit-form-button" type="submit" value="Play">
        </form>
    </section>
</body>
</html>
