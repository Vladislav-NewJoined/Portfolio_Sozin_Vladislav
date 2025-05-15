<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\HelloController;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

// Маршрут для отображения приветствия
Route::get('/hello', [HelloController::class, 'index']);

// Маршруты для формы
Route::get('/form', [HelloController::class, 'form']);
Route::post('/submit', [HelloController::class, 'submit']);
