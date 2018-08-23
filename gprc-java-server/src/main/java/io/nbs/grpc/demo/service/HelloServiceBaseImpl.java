package io.nbs.grpc.demo.service;

import io.grpc.stub.StreamObserver;
import io.nbs.gprc.demoserver.HelloReply;
import io.nbs.gprc.demoserver.HelloRequest;
import io.nbs.gprc.demoserver.HelloServiceGrpc;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project gRPC-demo
 * <p>
 * Author   : lanbery
 * Created  : 2018/8/23
 */
public class HelloServiceBaseImpl extends HelloServiceGrpc.HelloServiceImplBase {

    private  Logger logger = LoggerFactory.getLogger(HelloServiceBaseImpl.class);

    private Map<String,String> map = new HashMap<>();

    public HelloServiceBaseImpl() {
        map.put("nbs","你好，");
        map.put("nbschain","你好，");
        map.put("NBS","你好，");
        map.put("NBSChain","你好，");
    }



    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        logger.info("Server sayHello param:{} ,returns {}",request.getName(),responseObserver.toString());
        String remsg = request.getName();
        remsg = covert(remsg);
        HelloReply reply = HelloReply.newBuilder().setMessage(remsg).build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }


    /**
     * @param : a
     * @return : a
     * @Description :
     * @author : lanbery
     * @Datetime : 2018/8/3
     */
    private String covert(String key){
        if(StringUtils.isNotBlank(key)&&map.containsKey(key)){
            return map.get(key)+key;
        }else {
            return key==null? "none" :key;
        }
    }
}
