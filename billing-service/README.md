### gRPC
- Create `proto` folder inside java folder
- Create a `.proto` file

```protobuf
syntax = "proto3";

option java_multiple_files = true;  //split our service req and response files into separate files, by default code generation will be in single folder
option java_package = "billing"; //code generation code spits into billing package

service BillingService{
    rpc CreateBillingAccount(BillingRequest) returns (BillingResponse);; //rpc is the protocol, CreateBillingAccount-method
}

message BillingRequest { //How billingRequest looks like (Similar to DTO)
    string patientId=1; //Serialization and Deserialization (Not assigning)
    string name=2;
    string email=3;
}

message BillingResponse{
    string accountId=1;
    string status=2;
}
```
- Generate java files from proto file
- Create a package called `grpc` in `com.patient.billing_service`
- Create a Java class called `BillingGrpcService` extending from ImplBase class which got generated on compiling microservice
- Override the method that we have defined in proto file(the method will be present in `ImplBase` class)
- ImplBase class - `target/generated-sources/protobuf/grpc-java/billing/BillingServiceGrpc.java`

### Generating gRPC protobuf code
- Open `billing_service` in maven directories
- Go to lifecycle
- Double-click on compile
- or `./mvnw clean compile`