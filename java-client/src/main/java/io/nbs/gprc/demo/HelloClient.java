package io.nbs.gprc.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.nbs.gprc.demoserver.HelloReply;
import io.nbs.gprc.demoserver.HelloRequest;
import io.nbs.gprc.demoserver.HelloServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project gRPC-demo
 * <p>
 * Author   : lanbery
 * Created  : 2018/8/23
 */
public class HelloClient {
    private static final String DEF_HOST = "localhost";
    private static final int DEF_PORT = 52011;

    private static Logger logger = LoggerFactory.getLogger(HelloClient.class);

    private ManagedChannel managedChannel;

    private HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub;

    public HelloClient(String host,int port) {
        this(ManagedChannelBuilder.forAddress(host,port).usePlaintext().build());
    }

    public HelloClient(ManagedChannel managedChannel) {
        this.managedChannel = managedChannel;
        this.helloServiceBlockingStub = HelloServiceGrpc.newBlockingStub(managedChannel);
    }

    public void shutdown() throws InterruptedException {
        managedChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String hi(String in){
        HelloRequest request = HelloRequest.newBuilder().setName(in).build();
        HelloReply reply = helloServiceBlockingStub.sayHello(request);
        logger.info("Reply : {}",reply.getMessage());
        return reply.getMessage();
    }

    public static void main(String[] args){
        logger.info("Client Starting...");
        HelloClient client = new HelloClient(DEF_HOST,DEF_PORT);

        for(String arg : args){
            logger.info("args : {}",arg);
        }
        client.hi("NBS TMAM");
    }
}
