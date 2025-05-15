<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class HelloController extends Controller
{
    public function index()
    {
        $data = [
            'title' => 'Мой первый проект Laravel',
            'message' => 'Добро пожаловать в мир Laravel!',
            'features' => [
                'Маршрутизация',
                'Контроллеры',
                'Представления',
                'Eloquent ORM',
                'Миграции'
            ]
        ];
        
        return view('hello', $data);
    }

    public function form()
    {
        return view('form');
    }

    public function submit(Request $request)
    {
        $name = $request->input('name');
        return view('hello', [
            'title' => 'Форма отправлена',
            'message' => "Привет, {$name}!",
            'features' => [
                'Маршрутизация',
                'Контроллеры',
                'Представления',
                'Обработка форм',
                'Валидация'
            ]
        ]);
    }
}
