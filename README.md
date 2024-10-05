# micro-library

Набор микросервисов для имитации библиотеки.

## Как запустить?

При помощи Docker Compose:

```
$ docker compose up book-db -d
$ docker compose up library-db -d
$ docker compose up book-service -d
$ docker compose up library-service -d
```

## Документация

Swagger документация доступна на `/api-docs`. Swagger UI — на `/swagger-ui/index.html`.

Порт для `book-service` — 8080, для `library-service` — 8081.

## Лицензия

MIT
