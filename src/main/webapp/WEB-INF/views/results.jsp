<%@ include file="header.jsp" %>
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
            background: #f9f9f9;
            color: #333;
        }

        h1 {
            text-align: center;
            color: #6561E8;
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
            background-color: #6561E8;
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

        .action-section {
            text-align: center;
            margin: 20px 0;
        }

        .action-section button {
            margin: 0 10px;
            padding: 10px 20px;
            font-size: 1em;
            color: #fff;
            background: #6561E8;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .action-section button:hover {
            background: #4a90e2;
        }

        #detailsModal {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            z-index: 1000;
            width: 50%;
            max-width: 600px;
        }

        #modalOverlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            z-index: 999;
        }

        .modal-content {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .modal-row {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .modal-row strong {
            font-weight: bold;
            color: #333;
        }

        .modal-row p {
            margin: 0;
            padding: 10px;
            background: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 5px;
            word-wrap: break-word;
        }

        .close-btn {
            align-self: flex-end;
            padding: 10px 20px;
            font-size: 1em;
            color: #fff;
            background: #6561E8;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .close-btn:hover {
            background: #4a90e2;
        }
    </style>
</head>
<body>
    <h1>API Test Results</h1>
    <div class="summary">
        <span>Total Records: ${fn:length(processedRecords)}</span>
        <span>Passed: ${passed}</span>
        <span>Failed: ${failed}</span>
        <span>Pass Percentage: ${percentagePassed}%</span>
    </div>
    <div class="action-section">
        <button onclick="resetPage()">Reset</button>
        <button onclick="downloadResults()">Download Results</button>
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

    <!-- Modal Structure -->
    <div id="detailsModal">
        <div class="modal-content">
            <h3>Record Details</h3>
            <div class="modal-row">
                <strong>Payload:</strong>
                <p id="modalPayload"></p>
            </div>
            <div class="modal-row">
                <strong>Headers:</strong>
                <p id="modalHeaders"></p>
            </div>
            <div class="modal-row">
                <strong>Params:</strong>
                <p id="modalParams"></p>
            </div>
            <button class="close-btn" onclick="closeModal()">Close</button>
        </div>
    </div>
    <div id="modalOverlay" onclick="closeModal()"></div>

    <script>
        function downloadResults() {
            const table = document.querySelector('table');
            let csvContent = '';

            // Extract table headers
            const headers = Array.from(table.querySelectorAll('thead th')).map(th => th.textContent.trim());
            csvContent += headers.join(',') + '\n';

            // Extract table rows
            const rows = table.querySelectorAll('tbody tr');
            rows.forEach(row => {
                const cells = Array.from(row.querySelectorAll('td')).map(td => td.textContent.trim());
                csvContent += cells.join(',') + '\n';
            });

            // Create a Blob and trigger download
            const blob = new Blob([csvContent], { type: 'text/csv' });
            const url = URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'test_results.csv';
            a.click();
            URL.revokeObjectURL(url);
        }

        function resetPage() {
            window.location.href = '/';
        }

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
<%@ include file="footer.jsp" %>