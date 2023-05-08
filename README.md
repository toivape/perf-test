Compare Spring Boot, Ktor and Reactive Spring Boot performance.

| Framework      | Concurrency | Requests per second | Time per request (ms) | Time per request across all concurrent requests (ms) |
|----------------|-------------|---------------------|-----------------------|----------------------------------------------------|
| Reactive Spring Boot | 2500        | 88.41              | 28276.080             | 11.310                                             |
| Reactive Spring Boot | 2000        | 88.50              | 22598.685             | 11.299                                             |
| Reactive Spring Boot | 1500        | 38.01              | 39464.489             | 26.310                                             |
| Reactive Spring Boot | 1000        | 37.99              | 26322.909             | 26.323                                             |
| Reactive Spring Boot | 500         | 39.95              | 12515.613             | 25.031                                             |
| Reactive Spring Boot | 100         | 19.47              | 5135.591              | 51.356                                             |
| Spring Boot    | 2500        | 37.96              | 65851.834             | 26.341                                             |
| Spring Boot    | 2000        | 37.94              | 52718.500             | 26.359                                             |
| Spring Boot    | 1500        | 37.97              | 39508.068             | 26.339                                             |
| Spring Boot    | 1000        | 38.00              | 26314.950             | 26.315                                             |
| Spring Boot    | 500         | 37.77              | 13238.478             | 26.477                                             |
| Spring Boot    | 100         | 19.47              | 5135.599              | 51.356                                             |
| Ktor           | 500         | 19.41              | 25762.710             | 51.525                                             |
| Ktor           | 100         | 18.88              | 5297.431              | 52.974                                             |


Test were run using Apache AB tool like this:

```ab -n 4000 -c 100 http://localhost:8082/reactive/data/1```

```ab -n 4000 -c 100 http://localhost:8081/boot/data/1```

```ab -n 4000 -c 100 http://localhost:8084/ktor/data/1```

