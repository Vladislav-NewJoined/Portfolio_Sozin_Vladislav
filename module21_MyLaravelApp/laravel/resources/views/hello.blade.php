<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>{{ $title }}</title>
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
        ul {
            text-align: left;
            margin: 20px auto;
            max-width: 300px;
        }
        li {
            margin-bottom: 8px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>{{ $message }}</h1>
        <p>Ваше первое приложение на Laravel успешно запущено!</p>
        <p>Текущее время: {{ date('H:i:s') }}</p>
        
        <h2>Основные возможности Laravel:</h2>
        <ul>
            @foreach($features as $feature)
                <li>{{ $feature }}</li>
            @endforeach
        </ul>
    </div>
</body>
</html>
