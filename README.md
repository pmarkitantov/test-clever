## Запуск

   1. Создайте в PostgreSQL базу данных legacy_import и пользователя:
   ```sql
   CREATE USER legacy_user WITH PASSWORD 'legacy_pass';
   CREATE DATABASE legacy_import OWNER legacy_user;
   ```
   
   2. Выполните SQL-скрипты из папки db/:
   •  schema.sql — создаёт таблицы
   •  seed.sql — добавляет тестовых пациентов

   3. Запуск приложения:
   ```bash
   ./mvnw spring-boot:run
   ```

## Что происходит

   При старте приложение берёт данные из `src/main/resources/legacy/clients.json` и `notes.json`.  
   Каждые 10 секунд запускается импорт: новые заметки добавляются, существующие обновляются.  
   В консоли видно результат в виде строк:
  ```
  [SCHEDULED] Import started
  [SCHEDULED] Import finished: ImportResult{inserted=..., updated=..., skipped=...}
  ```

## Требования

   Локально должна быть PostgreSQL с базой `legacy_import` и таблицами `company_user`, `patient_profile`, `patient_note`.  
   Настройки подключения находятся в `src/main/resources/application.yml`.
