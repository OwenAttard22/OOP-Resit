package oopresit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import com.google.protobuf.Timestamp;

import oopresit.proto.TestProto.AssetSnapshot_Proto;
import oopresit.proto.TestProto.Asset_Proto;
import oopresit.proto.TestProto.Assets_Proto;
import oopresit.proto.TestProto.Bank_Proto;
import oopresit.proto.TestProto.BondSnapshot_Proto;
import oopresit.proto.TestProto.Bond_Proto;
import oopresit.proto.TestProto.Broker_Proto;
import oopresit.proto.TestProto.HistoricalSnapshot_Proto;
import oopresit.proto.TestProto.HistoricalSnapshots_Proto;
import oopresit.proto.TestProto.Intermediaries_Proto;
import oopresit.proto.TestProto.Intermediary_Proto;
import oopresit.proto.TestProto.MutualFundManager_Proto;
import oopresit.proto.TestProto.MutualFundSnapshot_Proto;
import oopresit.proto.TestProto.MutualFund_Proto;
import oopresit.proto.TestProto.StockSnapshot_Proto;
import oopresit.proto.TestProto.Stock_Proto;

public class Facade {
    private ArrayList<Intermediaries> intermediariesList;
    private ArrayList<Assets> assetsList;
    private ArrayList<HistoricalSnapshots2> snapshotsList;
    
    Assets_Proto.Builder assets = Assets_Proto.newBuilder();
    Intermediaries_Proto.Builder intermediaries = Intermediaries_Proto.newBuilder();
    HistoricalSnapshots_Proto.Builder snapshots = HistoricalSnapshots_Proto.newBuilder();

    private void setLists(ArrayList<Intermediaries> intermediariesList, ArrayList<Assets> assetsList, ArrayList<HistoricalSnapshots2> snapshotsList) {
        this.intermediariesList = intermediariesList;
        this.assetsList = assetsList;
        this.snapshotsList = snapshotsList;
    }

    private void resetLists() {
        this.intermediariesList = new ArrayList<>();
        this.assetsList = new ArrayList<>();
        this.snapshotsList = new ArrayList<>();
    }

    private Intermediary_Proto.Builder BrokerHelper(Broker broker) {
        Broker_Proto.Builder _broker = Broker_Proto.newBuilder();
        _broker.setBrokerCommission(broker.get_commission());
        _broker.build();

        Intermediary_Proto.Builder _intermediary = Intermediary_Proto.newBuilder();
        _intermediary.setIntermediaryName(broker.get_name());
        _intermediary.setBroker(_broker);
        _intermediary.build();

        return _intermediary;
    }

    private Intermediary_Proto.Builder BankHelper(Bank bank) {
        Bank_Proto.Builder _bank = Bank_Proto.newBuilder();
        _bank.setBankInterestRate(bank.get_interestRate());
        _bank.build();

        Intermediary_Proto.Builder _intermediary = Intermediary_Proto.newBuilder();
        _intermediary.setIntermediaryName(bank.get_name());
        _intermediary.setBank(_bank);
        _intermediary.build();

        return _intermediary;
    }

    private Intermediary_Proto.Builder MutualFundManagerHelper(MutualFundManager mutualFundManager) {
        MutualFundManager_Proto.Builder _mutualFundManager = MutualFundManager_Proto.newBuilder();
        _mutualFundManager.setMutualfundmanagerEmployeenumber(mutualFundManager.get_employeeNumber());
        _mutualFundManager.setMututalfundmanagerManagementfee(mutualFundManager.get_managementFee());
        _mutualFundManager.build();

        Intermediary_Proto.Builder _intermediary = Intermediary_Proto.newBuilder();
        _intermediary.setIntermediaryName(mutualFundManager.get_name());
        _intermediary.setMutualFundManager(_mutualFundManager);
        _intermediary.build();

        return _intermediary;
    }

    private void processBroker(Broker broker) {
        intermediaries.addIntermediaries(BrokerHelper(broker));
    }

    private void processBank(Bank bank) {
        intermediaries.addIntermediaries(BankHelper(bank));
    }

    private void processMutualFundManager(MutualFundManager mutualFundManager) {
        intermediaries.addIntermediaries(MutualFundManagerHelper(mutualFundManager));
    }

    private Asset_Proto.Builder StockHelper(Stock stock) {
        Stock_Proto.Builder _stock = Stock_Proto.newBuilder();
        _stock.setStockQuantity(stock.get_quantity());
        _stock.setStockTicker(stock.get_ticker());
        _stock.setStockYield(stock.get_yield());
        _stock.build();

        Asset_Proto.Builder _asset = Asset_Proto.newBuilder();
        _asset.setAssetName(stock.get_name());
        _asset.setAssetValue(stock.get_value());
        _asset.setIntermediary(BrokerHelper((Broker) stock.get_intermediary()));
        _asset.setStock(_stock);
        _asset.build();

        return _asset;
    }

    private Asset_Proto.Builder BondHelper(Bond bond) {
        Bond_Proto.Builder _bond = Bond_Proto.newBuilder();
        _bond.setBondInterestrate(bond.get_interestRate());
        _bond.setBondDaystomaturity(bond.get_daysToMaturity());
        _bond.build();

        Asset_Proto.Builder _asset = Asset_Proto.newBuilder();
        _asset.setAssetName(bond.get_name());
        _asset.setAssetValue(bond.get_value());
        _asset.setIntermediary(BankHelper((Bank) bond.get_intermediary()));
        _asset.setBond(_bond);
        _asset.build();

        return _asset;
    }

    private Asset_Proto.Builder MutualFundHelper(MutualFund mutualFund) {
        MutualFund_Proto.Builder _mutualFund = MutualFund_Proto.newBuilder();
        _mutualFund.setMutualfundExpenseratio(mutualFund.get_expenseRatio());
        _mutualFund.build();

        Asset_Proto.Builder _asset = Asset_Proto.newBuilder();
        _asset.setAssetName(mutualFund.get_name());
        _asset.setAssetValue(mutualFund.get_value());
        _asset.setIntermediary(MutualFundManagerHelper((MutualFundManager) mutualFund.get_intermediary()));
        _asset.setMutualFund(_mutualFund);
        _asset.build();

        return _asset;
    }

    private void processStock(Stock stock) {
        assets.addAssets(StockHelper(stock));
    }

    private void processBond(Bond bond) {
        assets.addAssets(BondHelper(bond));
    }

    private void processMutualFund(MutualFund mutualFund) {
        assets.addAssets(MutualFundHelper(mutualFund));
    }

    private void processIntermediaries() {
        for (Intermediaries intermediary : this.intermediariesList){
            if (intermediary instanceof Broker) {
                processBroker((Broker) intermediary);
            } else if (intermediary instanceof Bank) {
                processBank((Bank) intermediary);
            } else if (intermediary instanceof MutualFundManager) {
                processMutualFundManager((MutualFundManager) intermediary);
            }
        }
    }

    private void processAssets() {
        for (Assets asset : this.assetsList) {
            if (asset instanceof Stock) {
                processStock((Stock) asset);
            } else if (asset instanceof Bond) {
                processBond((Bond) asset);
            } else if (asset instanceof MutualFund) {
                processMutualFund((MutualFund) asset);
            }
        }
    }

    private void saveState() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        try (FileOutputStream intermediariesOutput = new FileOutputStream("intermediaries_" + timestamp + ".bin");
             FileOutputStream assetsOutput = new FileOutputStream("assets_" + timestamp + ".bin");
             FileOutputStream snapshotsOutput = new FileOutputStream("snapshots_" + timestamp + ".bin")) {

            intermediaries.build().writeTo(intermediariesOutput);
            assets.build().writeTo(assetsOutput);
            snapshots.build().writeTo(snapshotsOutput);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File[] getFiles(String prefix) {
        File folder = new File(".");
        return folder.listFiles((dir, name) -> name.startsWith(prefix) && name.endsWith(".bin"));
    }

    private void loadCLI(File[] intermediaryFiles) {
        if (intermediaryFiles.length == 0) {
            System.out.println("No files found");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 1 && choice != 2) {
            try {
                System.out.println("Select sorting method: ");
                System.out.println("1. Ascending order");
                System.out.println("2. Descending order");
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine());

                if (choice != 1 && choice != 2) {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        if (choice == 1) {
            Arrays.sort(intermediaryFiles, Comparator.comparing(File::getName));
        } else if (choice == 2) {
            Arrays.sort(intermediaryFiles, Comparator.comparing(File::getName).reversed());
        }

        System.out.println("Load Save:");
        for (int i = 0; i < intermediaryFiles.length; i++) {
            System.out.println((i + 1) + ". " + intermediaryFiles[i].getName());
        }

        int fileChoice = 0;
        while (fileChoice < 1 || fileChoice > intermediaryFiles.length) {
            try {
                System.out.print("Select save: ");
                fileChoice = Integer.parseInt(scanner.nextLine());

                if (fileChoice < 1 || fileChoice > intermediaryFiles.length) {
                    System.out.println("Invalid choice. Please select a number between 1 and " + intermediaryFiles.length + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        File selectedIntermediaryFile = intermediaryFiles[fileChoice - 1];
        String timestamp = selectedIntermediaryFile.getName().substring("intermediaries_".length(), selectedIntermediaryFile.getName().length() - 4);
        File assetsFile = new File("assets_" + timestamp + ".bin");
        File snapshotsFile = new File("snapshots_" + timestamp + ".bin");

        if (assetsFile.exists() && snapshotsFile.exists()) {
            deserialise(selectedIntermediaryFile, assetsFile, snapshotsFile);
        } else {
            System.out.println("Matching assets or snapshots file not found.");
        }
    }

    private void deserialise(File intermediariesFile, File assetsFile, File snapshotsFile) {
        try (FileInputStream intermediariesInput = new FileInputStream(intermediariesFile);
             FileInputStream assetsInput = new FileInputStream(assetsFile);
             FileInputStream snapshotsInput = new FileInputStream(snapshotsFile)) {
    
            Intermediaries_Proto intermediariesProto = Intermediaries_Proto.parseFrom(intermediariesInput);
            this.intermediariesList = deserialiseIntermediaries(intermediariesProto);
    
            Assets_Proto assetsProto = Assets_Proto.parseFrom(assetsInput);
            this.assetsList = deserialiseAssets(assetsProto);
    
            HistoricalSnapshots_Proto snapshotsProto = HistoricalSnapshots_Proto.parseFrom(snapshotsInput);
            this.snapshotsList = deserialiseHistoricalSnapshots(snapshotsProto);
    
            System.out.println("Successfully loaded from " + intermediariesFile.getName());
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Broker deserialiseBroker(Intermediary_Proto intermediaryProto) {
        return new Broker(intermediaryProto.getIntermediaryName(), intermediaryProto.getBroker().getBrokerCommission());
    }

    private Bank deserialiseBank(Intermediary_Proto intermediaryProto) {
        return new Bank(intermediaryProto.getIntermediaryName(), intermediaryProto.getBank().getBankInterestRate());
    }

    private MutualFundManager deserialiseMutualFundManager(Intermediary_Proto intermediaryProto) {
        return new MutualFundManager(intermediaryProto.getIntermediaryName(), intermediaryProto.getMutualFundManager().getMutualfundmanagerEmployeenumber(), intermediaryProto.getMutualFundManager().getMututalfundmanagerManagementfee());
    }

    private ArrayList<Intermediaries> deserialiseIntermediaries(Intermediaries_Proto intermediariesProto) {
        ArrayList<Intermediaries> intermediariesList = new ArrayList<>();

        for (Intermediary_Proto intermediaryProto : intermediariesProto.getIntermediariesList()) {
            if (intermediaryProto.hasBroker()) {
                intermediariesList.add(deserialiseBroker(intermediaryProto));
            } else if (intermediaryProto.hasBank()) {
                intermediariesList.add(deserialiseBank(intermediaryProto));
            } else if (intermediaryProto.hasMutualFundManager()) {
                intermediariesList.add(deserialiseMutualFundManager(intermediaryProto));
            }
        }

        return intermediariesList;
    }

    private Stock deserialiseStock(Asset_Proto assetProto) {
        Broker broker = (Broker) deserialiseBroker(assetProto.getIntermediary());
        return new Stock(assetProto.getAssetName(), assetProto.getAssetValue(),
                assetProto.getStock().getStockTicker(), assetProto.getStock().getStockQuantity(),
                assetProto.getStock().getStockYield(), broker);
    }

    private Bond deserialiseBond(Asset_Proto assetProto) {
        Bank bank = (Bank) deserialiseBank(assetProto.getIntermediary());
        return new Bond(assetProto.getAssetName(), assetProto.getAssetValue(),
                assetProto.getBond().getBondInterestrate(), assetProto.getBond().getBondDaystomaturity(), bank);
    }

    private MutualFund deserialiseMutualFund(Asset_Proto assetProto) {
        MutualFundManager manager = (MutualFundManager) deserialiseMutualFundManager(assetProto.getIntermediary());
        return new MutualFund(assetProto.getAssetName(), assetProto.getAssetValue(),
                assetProto.getMutualFund().getMutualfundExpenseratio(), manager);
    }

    private ArrayList<Assets> deserialiseAssets(Assets_Proto assetsProto) {
        ArrayList<Assets> assetsList = new ArrayList<>();

        for (Asset_Proto assetProto : assetsProto.getAssetsList()) {
            if (assetProto.hasStock()) {
                assetsList.add(deserialiseStock(assetProto));
            } else if (assetProto.hasBond()) {
                assetsList.add(deserialiseBond(assetProto));
            } else if (assetProto.hasMutualFund()) {
                assetsList.add(deserialiseMutualFund(assetProto));
            }
        }

        return assetsList;
    }

    private void processHistoricalSnapshots() {
        for (HistoricalSnapshots2 snapshot : snapshotsList) {
            Date currentDate = new Date();
            HistoricalSnapshots2 newSnapshot = snapshot.withNewSnapshot(currentDate);

            HistoricalSnapshot_Proto.Builder snapshotProtoBuilder = HistoricalSnapshot_Proto.newBuilder();
            snapshotProtoBuilder.setDate(Timestamp.newBuilder().setSeconds(newSnapshot.getSnapshotDate().getTime() / 1000).build());

            for (Assets asset : assetsList) {
                AssetSnapshot_Proto.Builder assetSnapshotProtoBuilder = processAssetSnapshot(asset);
                snapshotProtoBuilder.addAssetSnapshots(assetSnapshotProtoBuilder);
            }

            snapshots.addHistoricalSnapshots(snapshotProtoBuilder);
        }
    }

    private AssetSnapshot_Proto.Builder processAssetSnapshot(Assets asset) {
        if (asset instanceof Stock) {
            return processStockSnapshot((Stock) asset);
        } else if (asset instanceof Bond) {
            return processBondSnapshot((Bond) asset);
        } else if (asset instanceof MutualFund) {
            return processMutualFundSnapshot((MutualFund) asset);
        } else {
            throw new IllegalArgumentException("Unknown asset type: " + asset.getClass().getSimpleName());
        }
    }

    private AssetSnapshot_Proto.Builder processStockSnapshot(Stock stock) {
        StockSnapshot_Proto.Builder stockProtoBuilder = StockSnapshot_Proto.newBuilder()
            .setStockSnapshotAssetName(stock.get_name())
            .setStockSnapshotAssetValue(stock.get_value())
            .setStockSnapshotTicker(stock.get_ticker())
            .setStockSnapshotQuantity(stock.get_quantity())
            .setStockSnapshotYield(stock.get_yield())
            .setStockSnapshotIntermediaryName(stock.get_intermediary().get_name())
            .setStockSnapshotIntermediaryCommission(((Broker) stock.get_intermediary()).get_commission());

        return AssetSnapshot_Proto.newBuilder().setStockSnapshot(stockProtoBuilder);
    }

    private AssetSnapshot_Proto.Builder processBondSnapshot(Bond bond) {
        BondSnapshot_Proto.Builder bondProtoBuilder = BondSnapshot_Proto.newBuilder()
            .setBondSnapshotAssetName(bond.get_name())
            .setBondSnapshotAssetValue(bond.get_value())
            .setBondSnapshotInterestRate(bond.get_interestRate())
            .setBondSnapshotDaysToMaturity(bond.get_daysToMaturity())
            .setBondSnapshotIntermediaryName(bond.get_intermediary().get_name())
            .setBondSnapshotIntermediaryInterestRate(((Bank) bond.get_intermediary()).get_interestRate());

        return AssetSnapshot_Proto.newBuilder().setBondSnapshot(bondProtoBuilder);
    }

    private AssetSnapshot_Proto.Builder processMutualFundSnapshot(MutualFund mutualFund) {
        MutualFundSnapshot_Proto.Builder mutualFundProtoBuilder = MutualFundSnapshot_Proto.newBuilder()
            .setMutualFundSnapshotAssetName(mutualFund.get_name())
            .setMutualFundSnapshotAssetValue(mutualFund.get_value())
            .setMutualFundSnapshotExpenseRatio(mutualFund.get_expenseRatio())
            .setMutualFundSnapshotIntermediaryName(mutualFund.get_intermediary().get_name())
            .setMutualFundSnapshotIntermediaryEmployeeNumber(((MutualFundManager) mutualFund.get_intermediary()).get_employeeNumber())
            .setMutualFundSnapshotIntermediaryManagementFee(((MutualFundManager) mutualFund.get_intermediary()).get_managementFee());

        return AssetSnapshot_Proto.newBuilder().setMutualFundSnapshot(mutualFundProtoBuilder);
    }

    private ArrayList<HistoricalSnapshots2> deserialiseHistoricalSnapshots(HistoricalSnapshots_Proto historicalSnapshotsProto) {
        ArrayList<HistoricalSnapshots2> snapshotsList = new ArrayList<>();
        
        for (HistoricalSnapshot_Proto snapshotProto : historicalSnapshotsProto.getHistoricalSnapshotsList()) {
            Date snapshotDate = new Date(snapshotProto.getDate().getSeconds() * 1000);
    
            if (snapshotDate == null) {
                System.err.println("Error: Snapshot date is null.");
                continue;
            }
    
            for (AssetSnapshot_Proto assetSnapshotProto : snapshotProto.getAssetSnapshotsList()) {
                HistoricalSnapshots2 snapshot = createSnapshotFromAssetProto(assetSnapshotProto, snapshotDate);
                if (snapshot != null) {
                    snapshotsList.add(snapshot);
                    // System.out.println("Loaded snapshot: " + snapshot.displaySnapshot());
                } else {
                    System.err.println("Error: Could not create snapshot from assetProto.");
                }
            }
        }
        
        System.out.println("Total snapshots loaded: " + snapshotsList.size());
        return snapshotsList;
    }    

    private HistoricalSnapshots2 createSnapshotFromAssetProto(AssetSnapshot_Proto assetSnapshotProto, Date snapshotDate) {
        if (snapshotDate == null) {
            System.err.println("Error: snapshotDate is null during creation.");
            return null;
        }
        
        if (assetSnapshotProto.hasStockSnapshot()) {
            return createStockSnapshot(assetSnapshotProto.getStockSnapshot(), snapshotDate);
        } else if (assetSnapshotProto.hasBondSnapshot()) {
            return createBondSnapshot(assetSnapshotProto.getBondSnapshot(), snapshotDate);
        } else if (assetSnapshotProto.hasMutualFundSnapshot()) {
            return createMutualFundSnapshot(assetSnapshotProto.getMutualFundSnapshot(), snapshotDate);
        } else {
            System.err.println("Unknown asset type in assetSnapshotProto");
            return null;
        }
    }

    private HistoricalSnapshots2 createStockSnapshot(StockSnapshot_Proto stockProto, Date snapshotDate) {
        return new StockSnapshot.Builder()
                .setassetName(stockProto.getStockSnapshotAssetName())
                .setassetValue(stockProto.getStockSnapshotAssetValue())
                .setticker(stockProto.getStockSnapshotTicker())
                .setquantity(stockProto.getStockSnapshotQuantity())
                .setyield(stockProto.getStockSnapshotYield())
                .setintermediaryName(stockProto.getStockSnapshotIntermediaryName())
                .setcommission(stockProto.getStockSnapshotIntermediaryCommission())
                .sethistoricalSnapshots(new HashMap<>())
                .setsnapshotDate(snapshotDate)
                .build();
    }

    private HistoricalSnapshots2 createBondSnapshot(BondSnapshot_Proto bondProto, Date snapshotDate) {
        return new BondSnapshot.Builder()
                .setassetName(bondProto.getBondSnapshotAssetName())
                .setassetValue(bondProto.getBondSnapshotAssetValue())
                .setinterestRate(bondProto.getBondSnapshotInterestRate())
                .setdaysToMaturity(bondProto.getBondSnapshotDaysToMaturity())
                .setintermediaryName(bondProto.getBondSnapshotIntermediaryName())
                .setintermediaryInterestRate(bondProto.getBondSnapshotIntermediaryInterestRate())
                .sethistoricalSnapshots(new HashMap<>())
                .setsnapshotDate(snapshotDate)
                .build();
    }

    private HistoricalSnapshots2 createMutualFundSnapshot(MutualFundSnapshot_Proto mutualFundProto, Date snapshotDate) {
        return new MutualFundSnapshot.Builder()
                .setassetName(mutualFundProto.getMutualFundSnapshotAssetName())
                .setassetValue(mutualFundProto.getMutualFundSnapshotAssetValue())
                .setexpenseRatio(mutualFundProto.getMutualFundSnapshotExpenseRatio())
                .setemployeeNumber(mutualFundProto.getMutualFundSnapshotIntermediaryEmployeeNumber())
                .setmanagementFee(mutualFundProto.getMutualFundSnapshotIntermediaryManagementFee())
                .setintermediaryName(mutualFundProto.getMutualFundSnapshotIntermediaryName())
                .sethistoricalSnapshots(new HashMap<>())
                .setsnapshotDate(snapshotDate)
                .build();
    }

    public void save(ArrayList<Intermediaries> intermediariesList, ArrayList<Assets> assetsList, ArrayList<HistoricalSnapshots2> snapshotsList) {
        setLists(intermediariesList, assetsList, snapshotsList);
        processIntermediaries();
        processAssets();
        processHistoricalSnapshots();
        saveState();
    }

    public ArrayList<Object> load() {
        resetLists();
        File[] intermediaryFiles = getFiles("intermediaries_");
        loadCLI(intermediaryFiles);

        ArrayList<Object> result = new ArrayList<>();
        result.add(this.assetsList);
        result.add(this.intermediariesList);
        result.add(this.snapshotsList);
        return result;
    }
}
