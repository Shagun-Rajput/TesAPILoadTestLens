<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>AI Test Results</title>
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

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        th {
            background: #4a90e2;
            color: white;
        }

        tr:nth-child(even) {
            background: #f2f2f2;
        }

        tr:hover {
            background: #ddd;
        }

        .action-buttons {
            margin: 20px 0;
            text-align: center;
        }

        .action-buttons button {
            padding: 10px 20px;
            font-size: 1em;
            color: #fff;
            background: #4a90e2;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin: 0 10px;
        }

        .action-buttons button:hover {
            background: #357ab8;
        }
    </style>
</head>
<body>
    <h1>AI Test Results</h1>
    <div class="action-buttons">
        <button onclick="location.href='/ai-dashboard'">Back to Dashboard</button>
        <button onclick="location.href='/download-report'">Download Report</button>
    </div>
    <table>
        <thead>
            <tr>
                <th>API Type</th>
                <th>Method</th>
                <th>API URL</th>
                <th>Payload</th>
                <th>Headers</th>
                <th>Params</th>
                <th>Response Code</th>
                <th>Response Time</th>
                <th>Response Message</th>
                <th>Other Details</th>
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
                            <td>${record.payload}</td>
                            <td>${record.headers}</td>
                            <td>${record.params}</td>
                            <td>${record.responseCode}</td>
                            <td>${record.responseTime}</td>
                            <td>${record.responseMessage}</td>
                            <td>${record.otherDetails}</td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="10" style="text-align: center;">No records found</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>