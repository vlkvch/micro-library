# micro-library

Набор микросервисов для имитации библиотеки.

## Как запустить?

При помощи Docker Compose:

```
$ docker compose up -d --build
```

## Документация

Swagger документация доступна на `/api-docs`. Swagger UI — на `/swagger-ui/index.html`.

Микросервисы — `book-service` и `library-service` — доступны через API Gateway, который доступен по `localhost:8080`:

- `localhost:8080/book-service/api-docs`
- `localhost:8080/library-service/api-docs`

## Тестирование

Postman коллекция доступна в папке `postman`.

## Лицензия

MIT
