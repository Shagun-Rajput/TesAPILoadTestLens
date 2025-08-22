<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test Runner</title>
    <script>
        function triggerApi() {
            // Show loading message
            document.getElementById('result').innerText = 'Processing... Please wait.';

            // Make a POST request to the API
            fetch('/api/run-test', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ key: 'value' }) // Add any required payload here
            })
            .then(response => response.json())
            .then(data => {
                // Display the result
                document.getElementById('result').innerText = 'Result: ' + data.result;
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById('result').innerText = 'Error occurred while running the test.';
            });
        }
    </script>
</head>
<body>
    <h1>Test Runner</h1>
    <button onclick="triggerApi()">Run Tests</button>
    <p id="result"></p>
</body>
</html>
<%@ include file="footer.jsp" %>