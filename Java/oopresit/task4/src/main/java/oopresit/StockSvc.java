package oopresit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import oopresit.stockservice.StockServiceGrpc;
import oopresit.stockservice.StockService_Proto;
import oopresit.stockservice.StockService_Return_Proto;

public class StockSvc {
    private final StockServiceGrpc.StockServiceBlockingStub blockingStub;

    public StockSvc(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = StockServiceGrpc.newBlockingStub(channel);
    }

    public double calculateStockReturn(String name, float value, float quantity, float yield, float commission) {
        StockService_Proto request = StockService_Proto.newBuilder()
                .setStockserviceName(name)
                .setStocksericeValue(value)
                .setStockserviceQuantity(quantity)
                .setStockserviceYield(yield)
                .setStockserviceIntermediaryCommission(commission)
                .build();

        StockService_Return_Proto response = blockingStub.calculateStockReturn(request);
        return response.getStockserviceReturn();
    }
}
