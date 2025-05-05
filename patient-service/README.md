- `/v3/api-docs` - Swagger docs

### Docker
- Config/Dependencies (application.properties)
- Runtime (JDK)
- Source Code (Maven)
- Docker image is a blueprint to create containers
- Docker image can be stored local development registry, AWS ECR, Dockerhub
- Container will have the config, runtime, source code packaged in it

### SSH into Docker PG Db
- `docker ps`
- `docker exec -it CONTAINER-ID bash`
- `psql -U USERNAME -d DB-NAME`

### gRPC
- Microservice communication
- Low latency communication between services
- `Protobuf (proto)` format
- To work with gRPC, we need proto file
- proto file will generate gRPC client and gRPC server
- proto file will be placed in each microservice