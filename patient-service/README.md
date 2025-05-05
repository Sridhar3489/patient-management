- `/v3/api-docs` - Swagger docs

### Docker
- Config/Dependencies (application.properties)
- Runtime (JDK)
- Source Code (Maven)
- Docker image is a blueprint to create containers
- Docker image can be stored local development registry, AWS ECR, Dockerhub
- Container will have the config, runtime, source code packaged in it

### Docker App and Docker Db Communication
#### Docker PG DB (name - patient_db)
- Create a docker image for pg db
- Expose ports 5432:5432
- Bind mount on docker container - `/var/lib/postgres/data`
- Env variables
  - POSTGRES_USER: demo_user
  - POSTGRES_PASSWORD: demo_pwd
  - POSTGRES_DB: demo_db
- Run options should be `--network internal`, so that containers running on internal network can access this db image
#### Docker App
- Write Dockerfile
- Env variables(imp only)
  - SPRING_DATASOURCE_URL: `jdbc:postgresql://patient_db:5432/demo_db`
  - SPRING_DATASOURCE_USERNAME: demo_user
  - SPRING_DATASOURCE_PASSWORD: demo_pwd
- URL to connect local DB is - `jdbc:postgresql://localhost:5432/db`
- URL to connect Docker db is - `jdbc:postgresql://container-name:5432/db`
- Address will be container-name, during communication of microservices

### SSH into Docker PG Db
- `docker ps`
- `docker exec -it CONTAINER-ID bash`
- `psql -U USERNAME -d DB-NAME`
- `docker-compose down -v`
- `docker-compose up --build`

### gRPC
- Synchronous
- Microservice communication
- Low latency communication between services
- `Protobuf (proto)` format
- To work with gRPC, we need proto file
- proto file will generate gRPC client and gRPC server
- proto file will be placed in each microservice

### Kafka
- Asynchronous
- bitnami/kafka - docker image(https://hub.docker.com/r/bitnami/kafka)

### gRPC vs Kafka
- gRPC can be used when we need one-one communication and require immediate response
- Kafka can be used when we need one-many communication and we don't need an immediate response