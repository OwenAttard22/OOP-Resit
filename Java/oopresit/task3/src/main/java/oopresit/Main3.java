package oopresit;


public class Main3 {
    public static void main(String[] args) {
        System.out.println("TASK 3!!!!!!!!!!");
        CLI3.InitMenu();


        // ArrayList<Intermediaries> intermediariesList = new ArrayList<>();

        // // Adding some intermediaries
        // Broker broker1 = new Broker("ABC Brokerage", 0.02f);
        // Bank bank1 = new Bank("XYZ Bank", 0.05f);
        // MutualFundManager fundManager1 = new MutualFundManager("DEF Fund Manager", "EMP123", 0.015f);

        // intermediariesList.add(broker1);
        // intermediariesList.add(bank1);
        // intermediariesList.add(fundManager1);

        // // Create the ArrayList for Assets
        // ArrayList<Assets> assetsList = new ArrayList<>();

        // // Adding some assets associated with intermediaries
        // Stock stock1 = new Stock("Apple Stock", 150.00f, "AAPL", 10.0f, 0.02f, broker1);
        // Bond bond1 = new Bond("Government Bond", 1000.00f, 0.03f, 200, bank1);
        // MutualFund mutualFund1 = new MutualFund("Growth Fund", 5000.00f, 0.01f, fundManager1);

        // assetsList.add(stock1);
        // assetsList.add(bond1);
        // assetsList.add(mutualFund1);

        // // Create HistoricalSnapshots for each asset
        // ArrayList<HistoricalSnapshots> snapshotsList = new ArrayList<>();
        
        // // Add a snapshot for each asset type
        // snapshotsList.add(new StockSnapshot.Builder()
        //         .setassetName(stock1.get_name())
        //         .setassetValue(stock1.get_value())
        //         .setticker(stock1.get_ticker())
        //         .setquantity(stock1.get_quantity())
        //         .setyield(stock1.get_yield())
        //         .setintermediaryName(stock1.get_intermediary().get_name())
        //         .setcommission(((Broker) stock1.get_intermediary()).get_commission())
        //         .setsnapshotDate(new Date())
        //         .build()
        //         .recordSnapshot(new Date()));

        // snapshotsList.add(new BondSnapshot.Builder()
        //         .setassetName(bond1.get_name())
        //         .setassetValue(bond1.get_value())
        //         .setinterestRate(bond1.get_interestRate())
        //         .setdaysToMaturity(bond1.get_daysToMaturity())
        //         .setintermediaryName(bond1.get_intermediary().get_name())
        //         .setintermediaryInterestRate(((Bank) bond1.get_intermediary()).get_interestRate())
        //         .setsnapshotDate(new Date())
        //         .build()
        //         .recordSnapshot(new Date()));

        // snapshotsList.add(new MutualFundSnapshot.Builder()
        //         .setassetName(mutualFund1.get_name())
        //         .setassetValue(mutualFund1.get_value())
        //         .setexpenseRatio(mutualFund1.get_expenseRatio())
        //         .setemployeeNumber(((MutualFundManager) mutualFund1.get_intermediary()).get_employeeNumber())
        //         .setmanagementFee(((MutualFundManager) mutualFund1.get_intermediary()).get_managementFee())
        //         .setintermediaryName(mutualFund1.get_intermediary().get_name())
        //         .setsnapshotDate(new Date())
        //         .build()
        //         .recordSnapshot(new Date()));

        // // Display all intermediaries
        // System.out.println("Intermediaries List:");
        // for (Intermediaries intermediary : intermediariesList) {
        //     System.out.println(intermediary.displayIntermediary());
        // }

        // // Display all assets along with their intermediaries
        // System.out.println("\nAssets List:");
        // for (Assets asset : assetsList) {
        //     System.out.println(asset.displayAsset() + " (Intermediary: " + asset.get_intermediary().displayIntermediary() + ")");
        // }

        // // Create a Facade object
        // Facade facade = new Facade();

        // // Save the state, which will include processing and saving the historical snapshots
        // facade.save(intermediariesList, assetsList, snapshotsList);

        

        // ArrayList<Object> loadedData = facade.load();

        // // Extract the loaded assetsList and intermediariesList and snapshotsList from the result
        // ArrayList<Assets> assetsList2 = (ArrayList<Assets>) loadedData.get(0);
        // ArrayList<Intermediaries> intermediariesList2 = (ArrayList<Intermediaries>) loadedData.get(1);
        // ArrayList<HistoricalSnapshots> snapshotsList2 = (ArrayList<HistoricalSnapshots>) loadedData.get(2);

        // // You can now work with the loaded lists, for example:
        // System.out.println("Loaded Assets:");
        // for (Assets asset : assetsList2) {
        //     System.out.println(asset.displayAsset() + " (Intermediary: " + asset.get_intermediary().displayIntermediary() + ")");
        // }

        // System.out.println("Loaded Intermediaries:");
        // for (Intermediaries intermediary : intermediariesList2) {
        //     System.out.println(intermediary.displayIntermediary());
        // }

        // System.out.println("Loaded Snapshots:");
        // for (HistoricalSnapshots snapshot : snapshotsList2) {
        //     System.out.println(snapshot.displaySnapshot());
        // }

    }
}

// java -cp task3\target\classes oopresit.Main3