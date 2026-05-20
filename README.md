# Bank Card Management System

Система управления банковскими картами на Java 17 + Spring Boot + MySQL.

## Что реализовано

- Spring Security + JWT.
- Роли `ADMIN` и `USER`.
- CRUD для банковских карт.
- Управление пользователями администратором.
- Просмотр своих карт пользователем с поиском и пагинацией.
- Запрос блокировки карты пользователем.
- Переводы между своими картами.
- Просмотр баланса карты.
- Шифрование номера карты через AES/GCM.
- Маскирование номера карты в ответах: `**** **** **** 1234`.
- Liquibase миграции в `src/main/resources/db/migration`.
- Docker Compose для MySQL и приложения.
- Swagger UI и OpenAPI.
- Unit-тесты ключевой бизнес-логики.

## Тестовые пользователи

При первом запуске создаются:

| Логин | Пароль | Роли |
|---|---|---|
| `admin` | `admin123` | `ADMIN`, `USER` |
| `user` | `user123` | `USER` |

## Как запустить через Docker Compose

```bash
mvn clean package -DskipTests
docker compose up --build
```

Приложение будет доступно на:

```text
http://localhost:8080
```

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

## Как запустить локально

1. Запусти MySQL.
2. Создай базу `bank_cards` и пользователя `bank_user` / `bank_password`.
3. Запусти приложение:

```bash
mvn spring-boot:run
```

## Если IntelliJ пишет Non-resolvable parent POM

В этом архиве `pom.xml` уже переделан без `<parent>`, чтобы избежать ошибки `Non-resolvable parent POM`.

Также добавлены файлы:

```text
.mvn/jvm.config
.mvn/maven.config
maven-settings.xml
```

Если Maven всё равно ругается на `Remote host terminated the handshake`:

1. `Settings → Appearance & Behavior → System Settings → HTTP Proxy` — выбери рабочий вариант прокси или `No proxy`.
2. `Settings → Build Tools → Maven` — можно включить `Override` для `User settings file` и выбрать файл `maven-settings.xml` из корня проекта.
3. Удали битый кэш:

```bat
rmdir /s /q "%USERPROFILE%\.m2\repository\org\springframework\boot"
del /s /q "%USERPROFILE%\.m2\repository\*.lastUpdated"
```

4. Нажми `Reload All Maven Projects`.

## Основные эндпоинты

### Auth

```http
POST /api/auth/login
```

### ADMIN

```http
GET    /api/admin/cards
POST   /api/admin/cards
GET    /api/admin/cards/{id}
PUT    /api/admin/cards/{id}
PATCH  /api/admin/cards/{id}/block
PATCH  /api/admin/cards/{id}/activate
DELETE /api/admin/cards/{id}

GET    /api/admin/users
POST   /api/admin/users
GET    /api/admin/users/{id}
PUT    /api/admin/users/{id}
DELETE /api/admin/users/{id}
```

### USER

```http
GET   /api/cards/my
GET   /api/cards/{id}/balance
PATCH /api/cards/{id}/block-request
POST  /api/transfers
```

## Формат сдачи

По заданию проект нужно выложить в отдельный публичный GitHub/GitLab-репозиторий. Архив использовать только для переноса файлов, не как финальный формат сдачи.
