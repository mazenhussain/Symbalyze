# Symbalyze Backend

This was created using the [Ktor Project Generator](https://start.ktor.io). It will serve all API endpoints used by our frontend.

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- [Routing](https://start.ktor.io/p/routing-default)

This service uses the GloVe (Global Vectors for Word Representation) model. The following is required:

1. Download one of the [pre-trained word vectors](https://github.com/stanfordnlp/GloVe?tab=readme-ov-file#download-pre-trained-word-vectors)
2. Extract the zip file
3. Select the file called glove.6B.50d.txt
4. Move it under src/main/resources/largefiles/

## Building & Running

| Task              | Description                   |
| ----------------- | ----------------------------- | -------------------------------------------------------------------- | --- |
| `./gradlew test`  | Run the tests                 |
| `./gradlew build` | Build everything              |
| <!--              | `buildFatJar`                 | Build an executable JAR of the server with all dependencies included | --> |
| <!--              | `buildImage`                  | Build the docker image to use with the fat JAR                       | --> |
| <!--              | `publishImageToLocalRegistry` | Publish the docker image locally                                     | --> |
| `./gradlew run`   | Run the server                |
| <!--              | `runDocker`                   | Run using the local docker image                                     | --> |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

Click the link (or go to http://localhost:8080) and you should see "Hello World"!
