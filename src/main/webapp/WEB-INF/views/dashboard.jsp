<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AltTestLens</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: #fefefe; /* Light background */
            color: #333;
            text-align: center;
        }

        h1 {
            margin-top: 20px;
            font-size: 3em;
            color: #4a90e2; /* Light blue */
            text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
        }

        h2 {
            margin-top: 30px;
            font-size: 1.8em;
            color: #7f8c8d; /* Soft gray */
        }

        .tagline {
            font-size: 1.2em;
            margin-top: 10px;
            color: #95a5a6; /* Light gray */
            font-style: italic;
        }

        form {
            margin: 30px auto;
            padding: 20px;
            width: 50%;
            background: #f9f9f9; /* Light gray background */
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        label {
            font-size: 1.2em;
            display: block;
            margin-bottom: 10px;
            color: #34495e; /* Dark gray */
        }

        input[type="file"] {
            padding: 10px;
            border: 1px solid #dcdcdc;
            border-radius: 5px;
            background: #ffffff; /* White */
            color: #333;
            font-size: 1em;
            cursor: pointer;
            transition: border-color 0.3s ease;
        }

        input[type="file"]:hover {
            border-color: #4a90e2; /* Light blue */
        }

        input[type="file"]:focus {
            outline: none;
            box-shadow: 0 0 5px #4a90e2;
        }

        button {
            margin-top: 20px;
            padding: 10px 20px;
            font-size: 1.2em;
            color: #fff;
            background: #4a90e2; /* Light blue */
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background 0.3s ease, transform 0.2s ease;
        }

        button:hover {
            background: #357ab8; /* Slightly darker blue */
            transform: scale(1.05);
        }

        button:active {
            transform: scale(0.95);
        }
    </style>
</head>
<body>
    <h1>AltTestLens</h1>
    <p class="tagline">Upload your API details and run API health check</p>
    <div>
        <h2>Enter Details</h2>
        <form method="post" enctype="multipart/form-data">
            <label for="fileUpload">Upload File:</label>
            <input type="file" id="fileUpload" name="fileUpload" accept=".xls,.xlsx" required /><br><br>
            <button type="submit" formaction="/run-tests">Run Tests</button>
        </form>
    </div>
</body>
</html>