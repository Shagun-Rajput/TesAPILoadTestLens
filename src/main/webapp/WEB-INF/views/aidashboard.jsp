<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AI Test Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background: #f9f9f9;
            color: #333;
        }

        .container {
            margin: 50px auto;
            width: 50%;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        label {
            font-size: 1.2em;
            display: block;
            margin-bottom: 10px;
        }

        input[type="text"] {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            width: 100%;
            margin-bottom: 15px;
        }

        button {
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

        h1 {
            text-align: center;
            color: #4a90e2;
        }

        #loader {
            display: none;
            text-align: center;
            margin-top: 20px;
            font-size: 1.2em;
            color: #4a90e2;
        }
    </style>
    <script>
        function triggerTest(event) {
            event.preventDefault();
            const swaggerUrl = document.getElementById('swaggerUrl').value;
            if (!swaggerUrl) {
                alert('Please enter a Swagger URL.');
                return;
            }

            document.getElementById('loader').style.display = 'block';

            fetch('/ai-test', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ swaggerUrl })
            })
            .then(response => {
                document.getElementById('loader').style.display = 'none';
                if (response.ok) {
                    return response.text(); // Get the HTML content of the response
                } else {
                    alert('Failed to trigger AI test. Please try again.');
                    throw new Error('Failed to trigger AI test');
                }
            })
            .then(html => {
                document.open();
                document.write(html); // Render the returned HTML directly
                document.close();
            })
            .catch(error => {
                document.getElementById('loader').style.display = 'none';
                alert(`An unexpected error occurred: ${error.message}`);
            });
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>AI Test Dashboard</h1>
        <form onsubmit="triggerTest(event)">
            <label for="swaggerUrl">Enter Swagger URL:</label>
            <input type="text" id="swaggerUrl" name="swaggerUrl" placeholder="https://example.com/swagger.json" required />
            <button type="submit">Trigger Test</button>
        </form>
        <div id="loader">Processing... Please wait.</div>
    </div>
</body>
</html>
<%@ include file="footer.jsp" %>