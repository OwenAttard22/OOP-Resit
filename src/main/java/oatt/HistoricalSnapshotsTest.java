package oatt;

import java.util.Date;

public class HistoricalSnapshotsTest {
    public static void main(String[] args) {
        // Test StockSnapshot
        StockSnapshot stockSnapshot = new StockSnapshot.Builder()
                .setassetName("Apple Inc.")
                .setassetValue(150.75f)
                .setticker("AAPL")
                .setquantity(10.0f)
                .setyield(1.5f)
                .setintermediaryName("Brokerage XYZ")
                .setcommission(10.0f)
                .build();

        // Display the initial stock snapshot
        System.out.println(stockSnapshot.displaySnapshot());

        // Record and display a new snapshot
        StockSnapshot newStockSnapshot = (StockSnapshot) stockSnapshot.recordSnapshot();
        System.out.println("Recorded new stock snapshot at: " + new Date());
        System.out.println(newStockSnapshot.displaySnapshot());

        // Test BondSnapshot
        BondSnapshot bondSnapshot = new BondSnapshot.Builder()
                .setassetName("US Treasury Bond")
                .setassetValue(1000.0f)
                .setinterestRate(2.0f)
                .setdaysToMaturity(365)
                .setintermediaryName("Bank ABC")
                .setintermediaryInterestRate(0.5f)
                .build();

        // Display the initial bond snapshot
        System.out.println(bondSnapshot.displaySnapshot());

        // Record and display a new snapshot
        BondSnapshot newBondSnapshot = (BondSnapshot) bondSnapshot.recordSnapshot();
        System.out.println("Recorded new bond snapshot at: " + new Date());
        System.out.println(newBondSnapshot.displaySnapshot());

        // Test MutualFundSnapshot
        MutualFundSnapshot mutualFundSnapshot = new MutualFundSnapshot.Builder()
                .setassetName("Vanguard S&P 500 ETF")
                .setassetValue(300.5f)
                .setexpenseRatio(0.04f)
                .setemployeeNumber("EMP123456")
                .setmanagementFee(0.5f)
                .setintermediaryName("Investment Firm 123")
                .build();

        // Display the initial mutual fund snapshot
        System.out.println(mutualFundSnapshot.displaySnapshot());

        // Record and display a new snapshot
        MutualFundSnapshot newMutualFundSnapshot = (MutualFundSnapshot) mutualFundSnapshot.recordSnapshot();
        System.out.println("Recorded new mutual fund snapshot at: " + new Date());
        System.out.println(newMutualFundSnapshot.displaySnapshot());
    }
}
