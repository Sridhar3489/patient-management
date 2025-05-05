package com.patient.patient_service.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {

    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);
    private BillingServiceGrpc.BillingServiceBlockingStub blockingStub; //nested class inside BillingServiceGrpc,
    // provides synchronous service class, whenever we make calls to billing-service using blockingStub, it waits
    // till the response comes back from billing service

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
    ){

        log.info("Connecting to gRPC at {}:{}", serverAddress, serverPort);

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();

        blockingStub = BillingServiceGrpc.newBlockingStub(managedChannel);

    }

    public BillingResponse createBillingAccount(String patientId, String name, String email){

        BillingRequest billingRequest = BillingRequest.newBuilder().setPatientId(patientId).setEmail(email).setName(name).build();

        BillingResponse billingResponse = blockingStub.createBillingAccount(billingRequest);

        log.info("Received response from gRPC {}", billingResponse);

        return billingResponse;

    }

}
