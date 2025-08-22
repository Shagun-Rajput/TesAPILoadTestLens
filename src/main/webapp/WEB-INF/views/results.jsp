<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>AltTestLens - Results</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background: #f9f9f9;
            color: #333;
        }

        h1 {
            text-align: center;
            color: #4a90e2;
            position: relative;
        }

        .progress-circle {
            position: absolute;
            top: 50%;
            right: 350px;
            transform: translateY(-50%);
            width: 100px;
            height: 100px;
            background: conic-gradient(
                green ${percentagePassed}%,
                red ${percentagePassed}% 100%
            );
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.2em;
            color: #fff;
            font-weight: bold;
            margin-top: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #4a90e2;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #ddd;
        }

        .summary {
            margin: 20px 0;
            text-align: center;
        }

        .summary span {
            font-size: 1.2em;
            margin: 0 10px;
        }
    </style>
</head>
<body>
    <h1>
        API Test Results
        <div class="progress-circle"></div>
    </h1>
    <div class="summary">
        <span>Total Records: ${fn:length(processedRecords)}</span>
        <span>Passed: ${passed}</span>
        <span>Failed: ${failed}</span>
        <span>Pass Percentage: ${percentagePassed}%</span>
    </div>
    <table>
        <thead>
            <tr>
                <th>API Type</th>
                <th>Method</th>
                <th>API URL</th>
                <th>Response Code</th>
                <th>Response Time (ms)</th>
                <th>Response Message</th>
                <th>Details</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty processedRecords}">
                    <c:forEach var="record" items="${processedRecords}">
                        <tr>
                            <td>${record.apitype}</td>
                            <td>${record.method}</td>
                            <td>${record.apiurl}</td>
                            <td>${record.responseCode}</td>
                            <td>${record.responseTime}</td>
                            <td>${record.responseMessage}</td>
                            <td>
                                <button onclick="showDetails('${record.payload}', '${record.headers}', '${record.params}')">👁️</button>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="7" style="text-align: center;">No records to display</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <script>
        function showDetails(payload, headers, params) {
            document.getElementById('modalPayload').textContent = payload || 'N/A';
            document.getElementById('modalHeaders').textContent = headers || 'N/A';
            document.getElementById('modalParams').textContent = params || 'N/A';
            document.getElementById('detailsModal').style.display = 'block';
            document.getElementById('modalOverlay').style.display = 'block';
        }

        function closeModal() {
            document.getElementById('detailsModal').style.display = 'none';
            document.getElementById('modalOverlay').style.display = 'none';
        }
    </script>
</body>
</html>