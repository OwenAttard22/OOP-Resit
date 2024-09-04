package oopresit;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import oopresit.stockservice.StockServiceGrpc;
import oopresit.stockservice.StockService_Proto;
import oopresit.stockservice.StockService_Return_Proto;

public class StockServer {
    private Server server;

    public void start() throws IOException {
        int port = 7000;
        server = ServerBuilder.forPort(port)
                .addService(new StockServiceImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            StockServer.this.stop();
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final StockServer server = new StockServer();
        server.start();
        server.blockUntilShutdown();
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    static class StockServiceImpl extends StockServiceGrpc.StockServiceImplBase {
        @Override
        public void calculateStockReturn(StockService_Proto request, StreamObserver<StockService_Return_Proto> responseObserver) {
            double stockReturn = 365 * (request.getStockserviceYield() + request.getStockserviceIntermediaryCommission()) * (request.getStocksericeValue() * request.getStockserviceQuantity());

            StockService_Return_Proto response = StockService_Return_Proto.newBuilder()
                    .setStockserviceReturn(stockReturn)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
