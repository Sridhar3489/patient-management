package com.patient.billing_service.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import com.google.protobuf.BoolValue;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> billingResponseStreamObserver){
        //StreamObserver class - send multiple responses to client, and accept back & forth communication with clients(chat application)

        log.info("createBillingAccount request received {}", billingRequest.toString());

        //business logic

        BillingResponse billingResponse = BillingResponse.newBuilder().setAccountId("12345").setStatus("ACTIVE").build();

        billingResponseStreamObserver.onNext(billingResponse);
        billingResponseStreamObserver.onCompleted();

    }

    @Override
    public void deleteBillingAccount(StringValue patientId, StreamObserver<BoolValue> booleanStreamObserver){

        log.info("deleteBillingAccount request received {}", patientId);

        boolean boolValue = true;

        BoolValue response = BoolValue.newBuilder().setValue(boolValue).build();

        booleanStreamObserver.onNext(response);

        booleanStreamObserver.onCompleted();

    }

}
