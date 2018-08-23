package io.nbs.grpc.demo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.nbs.grpc.demo.service.HelloServiceBaseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project gRPC-demo
 * <p>
 * Author   : lanbery
 * Created  : 2018/8/23
 */
public class HelloServer {
    private Logger logger = LoggerFactory.getLogger(HelloServer.class);

    private static final int DEF_SERVER_PORT = 52011;

    private int port;

    private Server server;

    public HelloServer() {
        this(DEF_SERVER_PORT,ServerBuilder.forPort(DEF_SERVER_PORT));
    }

    public HelloServer(int port) {
        this(port,ServerBuilder.forPort(port));
    }

    public HelloServer(int port, ServerBuilder<?> builder) {
        this.port = port;
        this.server = builder.addService(new HelloServiceBaseImpl()).build();
    }

    private void start() throws IOException {
        server.start();
        logger.info("Server has started,listening on :{}",this.port);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                HelloServer.this.stop();
            }
        });
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/8/23
     * @Description  :
     *
     */
    private void stop(){
        if(server != null)server.shutdown();
    }

    private void blockSafeShutdown() throws InterruptedException {
        if(server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        HelloServer helloServer;

        if (args.length >0){
            helloServer = new HelloServer(Integer.parseInt(args[0]));
        }else {
            helloServer = new HelloServer();
        }

        helloServer.start();

        helloServer.blockSafeShutdown();
    }
}
