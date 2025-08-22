<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sample Input File Format</title>
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
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
            background: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
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

        .container {
            text-align: center;
            margin: 20px;
        }

        .download-btn {
            margin: 20px;
            padding: 10px 20px;
            font-size: 1em;
            color: #fff;
            background: #6561E8;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .download-btn:hover {
            background: #4a90e2;
        }
    </style>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>
    <script>
        function downloadSample() {
            const table = document.querySelector('table');
            const workbook = XLSX.utils.table_to_book(table, { sheet: "Sample Input" });
            XLSX.writeFile(workbook, 'SampleInput.xlsx');
        }
    </script>
</head>
<body>
    <h1>Sample Input File Format</h1>
    <div class="container">
        <button class="download-btn" onclick="downloadSample()">Download Sample</button>
        <table>
            <thead>
                <tr>
                    <th>apiType</th>
                    <th>method</th>
                    <th>apiUrl</th>
                    <th>payload</th>
                    <th>headers</th>
                    <th>params</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>REST</td>
                    <td>GET</td>
                    <td>https://example.com/api/v1/users</td>
                    <td>–</td>
                    <td>{ "Authorization": "Bearer &lt;token&gt;" }</td>
                    <td>{ "userId": "12345" }</td>
                </tr>
                <tr>
                    <td>REST</td>
                    <td>POST</td>
                    <td>https://example.com/api/v1/orders</td>
                    <td>{ "productId": "P123", "quantity": 2 }</td>
                    <td>{ "Authorization": "Bearer &lt;token&gt;", "Content-Type": "application/json" }</td>
                    <td>–</td>
                </tr>
                <tr>
                    <td>REST</td>
                    <td>PUT</td>
                    <td>https://example.com/api/v1/orders/123</td>
                    <td>{ "status": "shipped" }</td>
                    <td>{ "Authorization": "Bearer &lt;token&gt;" }</td>
                    <td>–</td>
                </tr>
                <tr>
                    <td>REST</td>
                    <td>DELETE</td>
                    <td>https://example.com/api/v1/orders/123</td>
                    <td>–</td>
                    <td>{ "Authorization": "Bearer &lt;token&gt;" }</td>
                    <td>–</td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>
<%@ include file="footer.jsp" %>