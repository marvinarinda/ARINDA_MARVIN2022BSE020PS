<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <div class="container error-container">
        <h2>Operation Failed</h2>
        <p><%= request.getParameter("msg") %></p>
        <a href="login.jsp">Go Back</a>
    </div>
</body>
</html>