<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script src="<c:url value="/static/jquery-3.6.0.min.js"/>"></script>
    <title>Text Quest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/results.css">
</head>
<body>
    <section class="results-section">
        <h1>${username}<br>${result}</h1>
        <button type="submit" onclick="restart()">Restart quest</button>
    </section>

    <script>
        function restart() {
            $.ajax({
                url: '/textquest/restart',
                method: 'POST',
                contentType: 'application/json;charset=UTF-8',
                async: false,
                success: function () {
                    location.reload();
                }
            })
        }
    </script>
</body>
</html>
