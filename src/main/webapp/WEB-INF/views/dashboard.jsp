<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AltLens - Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background: #f9f9f9;
            color: #333;
        }

        h1 {
            text-align: center;
            color: #4a90e2;
        }

        form {
            margin: 20px auto;
            padding: 20px;
            width: 50%;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        label {
            font-size: 1.2em;
            display: block;
            margin-bottom: 10px;
        }

        input[type="file"] {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            width: 100%;
        }

        button {
            margin-top: 20px;
            padding: 10px 20px;
            font-size: 1.2em;
            color: #fff;
            background: #4a90e2;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        button:hover {
            background: #357ab8;
        }
    </style>
</head>
<body>
    <h1>AltLens - Dashboard</h1>
    <form method="post" enctype="multipart/form-data" action="/run-tests">
        <label for="fileUpload">Upload Test File:</label>
        <input type="file" id="fileUpload" name="fileUpload" accept=".xls,.xlsx" required />
        <button type="submit">Run Tests</button>
    </form>
</body>
</html>
<%@ include file="footer.jsp" %>