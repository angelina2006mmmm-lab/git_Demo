# Student CRUD App

Учебный Java/Spring Boot проект для управления студентами.

## Стек

- Java 17
- Spring Boot
- Spring MVC
- Thymeleaf
- Spring Data JPA / Hibernate
- PostgreSQL
- Bean Validation
- Maven
- Git

## Архитектура

Проект разделен на слои:

`Controller -> Service -> Repository -> PostgreSQL`

Есть два способа работы с приложением:

1. MVC + Thymeleaf: HTML-интерфейс.
2. REST API: JSON endpoints.

## Возможности

- просмотр списка студентов;
- добавление студента;
- редактирование;
- удаление;
- получение студента по id;
- REST CRUD;
- валидация входных данных;
- централизованная обработка ошибок;
- логирование;
- работа с PostgreSQL через JPA/Hibernate.

## Запуск PostgreSQL

Создайте базу данных:

```sql
CREATE DATABASE student_db;
```

По умолчанию `application.properties` ожидает:

- database: `student_db`
- user: `postgres`
- password: `postgres`

Если у вас другой пароль, измените:

```properties
spring.datasource.username=postgres
spring.datasource.password=ВАШ_ПАРОЛЬ
```

## Запуск приложения

Из корня проекта:

```bash
mvn spring-boot:run
```

Или запустите `StudentCrudApplication` в IntelliJ IDEA.

## Веб-интерфейс

Откройте:

```text
http://localhost:8080/students
```

## REST API

### Получить всех студентов

```http
GET /api/students
```

### Получить по id

```http
GET /api/students/1
```

### Создать

```http
POST /api/students
Content-Type: application/json
```

Пример:

```json
{
  "firstName": "Анна",
  "lastName": "Иванова",
  "email": "anna@example.com",
  "age": 20
}
```

### Изменить

```http
PUT /api/students/1
Content-Type: application/json
```

### Удалить

```http
DELETE /api/students/1
```

## Что можно рассказать на собеседовании

Приложение построено на слоистой архитектуре. Контроллеры отвечают за HTTP-запросы и передачу данных, сервисный слой содержит бизнес-логику, а репозиторий отвечает за доступ к данным.

Для persistence используется Spring Data JPA. Hibernate выступает ORM-провайдером и отображает entity `Student` на таблицу `students` в PostgreSQL.

Bean Validation используется для проверки данных через `@Valid`, `@NotBlank`, `@Email`, `@Min` и другие ограничения.

Ошибки REST API обрабатываются централизованно через `@ControllerAdvice`.

Для изменения данных сервисные методы работают в транзакции через `@Transactional`.

Логирование выполняется через SLF4J.
