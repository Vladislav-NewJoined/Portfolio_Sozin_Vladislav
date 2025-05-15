<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Форма</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f5f5f5;
        }
        .container {
            text-align: center;
            padding: 2rem;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
        }
        h1 {
            color: #ff2d20; /* Laravel red */
        }
        form {
            margin-top: 20px;
        }
        input[type="text"] {
            padding: 8px;
            width: 100%;
            max-width: 300px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            padding: 8px 16px;
            background-color: #ff2d20;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #e62c1a;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Форма приветствия</h1>
        <p>Введите ваше имя, и Laravel вас поприветствует!</p>
        
        <form action="/submit" method="POST">
            @csrf
            <div>
                <input type="text" name="name" placeholder="Ваше имя" required>
            </div>
            <button type="submit">Отправить</button>
        </form>
    </div>
</body>
</html>
