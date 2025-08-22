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

        #loader {
            display: none;
            text-align: center;
            margin-top: 20px;
            font-size: 1.2em;
            color: #4a90e2;
        }

        .info-section {
            text-align: center;
            margin: 20px;
            font-size: 1.1em;
        }

        .info-section a {
            color: #4a90e2;
            text-decoration: none;
            font-weight: bold;
        }

        .info-section a:hover {
            text-decoration: underline;
        }
    </style>
    <script>
        function showLoader() {
            document.getElementById('loader').style.display = 'block';
        }
    </script>
</head>
<body>
    <h1>AltLens - Dashboard</h1>
    <form method="post" enctype="multipart/form-data" action="/run-tests" onsubmit="showLoader()">
        <label for="fileUpload">Upload Test File:</label>
        <input type="file" id="fileUpload" name="fileUpload" accept=".xls,.xlsx" required />
        <button type="submit">Run Tests</button>
    </form>
    <div id="loader">API runner is working, please wait for the results...</div>
    <div class="info-section">
        <p>Not sure about the input file format? <a href="/sampleInput">Click here</a> to view the sample input file format.</p>
    </div>
</body>
</html>
<%@ include file="footer.jsp" %>