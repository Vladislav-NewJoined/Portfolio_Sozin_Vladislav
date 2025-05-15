# Laravel Application Module

Это Laravel-приложение, интегрированное в Maven-структуру проекта.

## Запуск приложения

1. Перейдите в директорию `laravel`:
   ```
   cd laravel
   ```

2. Установите зависимости Composer:
   ```
   composer install
   ```

3. Скопируйте файл `.env.example` в `.env`:
   ```
   copy .env.example .env
   ```

4. Сгенерируйте ключ приложения:
   ```
   php artisan key:generate
   ```

5. Запустите миграции (если необходимо):
   ```
   php artisan migrate
   ```

6. Запустите сервер разработки:
   ```
   php artisan serve
   ```

7. Откройте приложение в браузере: [http://localhost:8000](http://localhost:8000)

## Примечание

Это приложение использует PHP и Laravel, поэтому убедитесь, что у вас установлены:
- PHP 8.x
- Composer
- Необходимые расширения PHP для Laravel
```