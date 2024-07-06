<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Text Quest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/quest.css">
</head>
<body>
    <section class="quest-section">
        <h1>${username}<br>${question}</h1>
        <div class="button-container">
            <button class="answer" type="submit" onclick="window.location='/textquest/quest?answer=${first_answer}'">${first_answer}</button>
            <button class="answer" type="submit" onclick="window.location='/textquest/quest?answer=${second_answer}'">${second_answer}</button>
        </div>
    </section>
</body>
</html>
