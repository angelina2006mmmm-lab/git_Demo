# Student Platform — enhanced backend project

Учебный проект для практики Java backend-стека, близкого к требованиям вакансий Software Engineer.

## Стек
Java 17, Spring Boot 3, Spring Data JPA/Hibernate, PostgreSQL, Spring Security, JWT, Kafka, CompletableFuture, Docker Compose, Kubernetes basics.

## Архитектура
`student-service` хранит студентов в PostgreSQL и публикует `StudentCreatedEvent` в Kafka. `notification-service` — отдельный микросервис, который потребляет событие.

## JWT
Демо-пользователь: `admin / admin123`.

`POST /api/auth/login`
```json
{"username":"admin","password":"admin123"}
```
Полученный токен передавать как `Authorization: Bearer <token>`.

## REST
- GET `/api/students`
- GET `/api/students/{id}`
- POST `/api/students`
- PUT `/api/students/{id}`
- DELETE `/api/students/{id}`
- GET `/api/students/report` — асинхронный endpoint через `@Async` + `CompletableFuture`

## Docker
```bash
docker compose up --build
```

## Kubernetes basics
В `k8s/student-service.yaml` есть примеры `Deployment`, `Service`, `ConfigMap`, `Ingress`.

## Что изучить перед добавлением в резюме
1. Security filter chain, JWT structure/expiration, stateless auth.
2. Kafka topic, producer, consumer group, offset, at-least-once delivery.
3. Docker image/container/network/volume, Compose.
4. Зачем разделять сервисы и какие появляются trade-offs.
5. Thread pool, `@Async`, `CompletableFuture`.
6. Kubernetes: pod/deployment/service/configmap/ingress.
