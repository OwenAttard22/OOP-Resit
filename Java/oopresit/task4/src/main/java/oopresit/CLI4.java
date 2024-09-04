package oopresit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class CLI4 {
    private static Scanner input = new Scanner(System.in);
    private static int selection = -1;
    private static ArrayList<Assets> assetsList = new ArrayList<>();
    private static ArrayList<Intermediaries> intermediariesList = new ArrayList<>();
    private static ArrayList<HistoricalSnapshots2> portfolioList = new ArrayList<>();
    private static Date _date;
    private static Facade facade = new Facade();
    private static StockServer stockServer;

    static Date get_date(){
        return _date;
    }

    static void set_date(Date date){
        _date = date;
    }

    static void increment_date(){
        _date = new Date(_date.getTime() + 86400000);
    }

    public static void InitMenu() {
        startServer();
        set_date(new Date());
        System.out.println("Task 4 java version");
        do {
            System.out.println("\nInitialisation:");
            System.out.println("1. New Save");
            System.out.println("2. Load Save");
            System.out.println("3. Exit Application");
    
            try {
                selection = input.nextInt();
    
                switch (selection) {
                    case 1:
                        System.out.println("New save");
                        MainMenu();
                        break;
                    case 2:
                        System.out.println("Load save");
                        ArrayList<Object> loadedData = facade.load();
                        assetsList = (ArrayList<Assets>) loadedData.get(0);
                        intermediariesList = (ArrayList<Intermediaries>) loadedData.get(1);
                        portfolioList = (ArrayList<HistoricalSnapshots2>) loadedData.get(2);
                        MainMenu();
                        break;
                    case 3:
                        System.out.println("Exiting application...");
                        break;
                    default:
                        System.err.println("Invalid Selection");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer.");
                input.next();
            }
    
        } while (selection != 3);
    
        input.close();
    }

    public static void MainMenu() {
        do {
            System.out.println("\nMain Menu:");
            System.out.println("1. Portfolio");
            System.out.println("2. Assets Menu");
            System.out.println("3. Intermediaries Menu");
            System.out.println("4. Exit and save");
    
            try {
                selection = input.nextInt();
    
                switch (selection) {
                    case 1: // Portfolio
                        PortfolioMenu();
                        break;
                    case 2: // Assets
                        AssetsMenu();
                        break;
                    case 3: // Intermediaries
                        IntermediariesMenu();
                        break;
                    case 4: // Exit and save
                        facade.save(intermediariesList, assetsList, portfolioList);
                        System.out.println("Exiting and saving....");
                        stopServer();
                        System.exit(0);
                        break;
                    default:
                        System.err.println("Invalid Selection");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer.");
                input.next();
            }
    
        } while (true);
    }    

    public static void AssetsMenu() {
        do {
            System.out.println("\nAssets Menu:");
            System.out.println("1. Create Asset");
            System.out.println("2. Read Assets");
            System.out.println("3. Update Assets");
            System.out.println("4. Delete Assets");
            System.out.println("5. Back to Main Menu");

            try {
                selection = input.nextInt();

                switch (selection) {
                    case 1: // Create
                        createAsset();
                        break;
                    case 2: // Read
                        readAssets();
                        break;
                    case 3: // Update
                        updateAsset();
                        break;
                    case 4: // Delete
                        deleteAsset();
                        break;
                    case 5: // Main Menu
                        return; // Exit the AssetsMenu loop
                    default:
                        System.err.println("Invalid Selection");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer.");
                input.next();
            }

        } while (true);
    }

    public static void IntermediariesMenu() {
        do {
            System.out.println("\nIntermediaries Menu:");
            System.out.println("1. Create Intermediary");
            System.out.println("2. Read Intermediaries");
            System.out.println("3. Update Intermediaries");
            System.out.println("4. Delete Intermediaries");
            System.out.println("5. Back to Main Menu");

            try {
                selection = input.nextInt();

                switch (selection) {
                    case 1: // Create
                        createIntermediary();
                        break;
                    case 2: // Read
                        readIntermediaries();
                        break;
                    case 3: // Update
                        updateIntermediary();
                        break;
                    case 4: // Delete
                        deleteIntermediary();
                        break;
                    case 5: // Main Menu
                        return; // Exit the IntermediariesMenu loop
                    default:
                        System.err.println("Invalid Selection");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer.");
                input.next();
            }

        } while (true);
    }

    public static void createAsset() {
        do {
            System.out.println("\nChoose which type of asset to create:");
            System.out.println("1. Stock");
            System.out.println("2. Bond");
            System.out.println("3. Mutual Fund");
            System.out.println("4. Back to Assets Menu");

            try {
                selection = input.nextInt();

                switch (selection) {
                    case 1: // Stock
                        createStock();
                        return;
                    case 2: // Bond
                        createBond();
                        return;
                    case 3: // Mutual Fund
                        createMutualFund();
                        return;
                    case 4: // Back to Assets Menu
                        return; // Exit the createAsset loop
                    default:
                        System.err.println("Invalid Selection");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer.");
                input.next();
            }

        } while (true);
    }

    public static void createStock() {
    if (intermediariesList.isEmpty()) {
        System.out.println("No brokers available. Please create a broker first.");
        return;
    }

    List<Broker> brokers = new ArrayList<>();
    for (Intermediaries intermediary : intermediariesList) {
        if (intermediary instanceof Broker) {
            brokers.add((Broker) intermediary);
        }
    }

    if (brokers.isEmpty()) {
        System.out.println("No brokers available. Please create a broker first.");
        return;
    }

    System.out.println("\nEnter stock name:");
    String name = input.next();
    System.out.println("Enter stock value:");
    float value = input.nextFloat();
    System.out.println("Enter ticker:");
    String ticker = input.next();
    System.out.println("Enter quantity:");
    float quantity = input.nextFloat();
    System.out.println("Enter yield:");
    float yield = input.nextFloat();

    System.out.println("\nSelect a broker from the list:");
    for (int i = 0; i < brokers.size(); i++) {
        System.out.println((i + 1) + ": " + brokers.get(i).displayIntermediary());
    }

    try {
        int brokerSelection = input.nextInt();
        if (brokerSelection > 0 && brokerSelection <= brokers.size()) {
            Broker selectedBroker = brokers.get(brokerSelection - 1);
            Stock newStock = new Stock(name, value, ticker, quantity, yield, selectedBroker);
            assetsList.add(newStock);
            System.out.println("Stock created successfully.");
        } else {
            System.err.println("Invalid selection.");
        }
    } catch (InputMismatchException e) {
        System.err.println("Invalid input. Please enter an integer.");
        input.next();
    }
}

public static void createBond() {
    if (intermediariesList.isEmpty()) {
        System.out.println("No banks available. Please create a bank first.");
        return;
    }

    List<Bank> banks = new ArrayList<>();
    for (Intermediaries intermediary : intermediariesList) {
        if (intermediary instanceof Bank) {
            banks.add((Bank) intermediary);
        }
    }

    if (banks.isEmpty()) {
        System.out.println("No banks available. Please create a bank first.");
        return;
    }

    System.out.println("\nEnter bond name:");
    String name = input.next();
    System.out.println("Enter bond value:");
    float value = input.nextFloat();
    System.out.println("Enter interest rate:");
    float interestRate = input.nextFloat();
    System.out.println("Enter days to maturity:");
    int daysToMaturity = input.nextInt();

    System.out.println("\nSelect a bank from the list:");
    for (int i = 0; i < banks.size(); i++) {
        System.out.println((i + 1) + ": " + banks.get(i).displayIntermediary());
    }

    try {
        int bankSelection = input.nextInt();
        if (bankSelection > 0 && bankSelection <= banks.size()) {
            Bank selectedBank = banks.get(bankSelection - 1);
            Bond newBond = new Bond(name, value, interestRate, daysToMaturity, selectedBank);
            assetsList.add(newBond);
            System.out.println("Bond created successfully.");
        } else {
            System.err.println("Invalid selection.");
        }
    } catch (InputMismatchException e) {
        System.err.println("Invalid input. Please enter an integer.");
        input.next();
    }
}

public static void createMutualFund() {
    if (intermediariesList.isEmpty()) {
        System.out.println("No mutual fund managers available. Please create a mutual fund manager first.");
        return;
    }

    List<MutualFundManager> managers = new ArrayList<>();
    for (Intermediaries intermediary : intermediariesList) {
        if (intermediary instanceof MutualFundManager) {
            managers.add((MutualFundManager) intermediary);
        }
    }

    if (managers.isEmpty()) {
        System.out.println("No mutual fund managers available. Please create a mutual fund manager first.");
        return;
    }

    System.out.println("\nEnter mutual fund name:");
    String name = input.next();
    System.out.println("Enter mutual fund value:");
    float value = input.nextFloat();
    System.out.println("Enter expense ratio:");
    float expenseRatio = input.nextFloat();

    System.out.println("\nSelect a mutual fund manager from the list:");
    for (int i = 0; i < managers.size(); i++) {
        System.out.println((i + 1) + ": " + managers.get(i).displayIntermediary());
    }

    try {
        int managerSelection = input.nextInt();
        if (managerSelection > 0 && managerSelection <= managers.size()) {
            MutualFundManager selectedManager = managers.get(managerSelection - 1);
            MutualFund newMutualFund = new MutualFund(name, value, expenseRatio, selectedManager);
            assetsList.add(newMutualFund);
            System.out.println("Mutual fund created successfully.");
        } else {
            System.err.println("Invalid selection.");
        }
    } catch (InputMismatchException e) {
        System.err.println("Invalid input. Please enter an integer.");
        input.next();
    }
}

    public static void createIntermediary() {
        do {
            System.out.println("\nChoose which type of intermediary to create:");
            System.out.println("1. Broker");
            System.out.println("2. Bank");
            System.out.println("3. Mutual Fund Manager");
            System.out.println("4. Back to Intermediaries Menu");

            try {
                selection = input.nextInt();

                switch (selection) {
                    case 1: // broker
                        createBroker();
                        return;
                    case 2: // bank
                        createBank();
                        return;
                    case 3: // mutual fund manager
                        createMutualFundManager();
                        return;
                    case 4: // back to intermediaries menu
                        return; // break
                    default:
                        System.err.println("Invalid Selection");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer.");
                input.next();
            }

        } while (true);
    }

    public static void createBroker() {
        System.out.println("\nEnter broker name:");
        String name = input.next();
        System.out.println("Enter commission:");
        float commission = input.nextFloat();

        Broker newBroker = new Broker(name, commission);
        intermediariesList.add(newBroker);
        System.out.println("Broker created successfully.");
    }

    public static void createBank() {
        System.out.println("\nEnter bank name:");
        String name = input.next();
        System.out.println("Enter interest rate:");
        float interestRate = input.nextFloat();

        Bank newBank = new Bank(name, interestRate);
        intermediariesList.add(newBank);
        System.out.println("Bank created successfully.");
    }

    public static void createMutualFundManager() {
        System.out.println("\nEnter mutual fund manager name:");
        String name = input.next();
        System.out.println("Enter management fee:");
        float managementFee = input.nextFloat();
        System.out.println("Enter employee number:");
        String employeeNumber = input.next();

        MutualFundManager newManager = new MutualFundManager(name, employeeNumber, managementFee);
        intermediariesList.add(newManager);
        System.out.println("Mutual fund manager created successfully.");
    }

    public static void readAssets() {
        System.out.println("\nList of Assets:");
        if (assetsList.isEmpty()) {
            System.out.println("No assets available.");
        } else {
            for (int i = 0; i < assetsList.size(); i++) {
                System.out.println((i + 1) + ": " + assetsList.get(i).displayAsset());
            }
        }
    }

    public static void updateAsset() {
        if (assetsList.isEmpty()) {
            System.out.println("No assets available to update.");
            return;
        }
    
        System.out.println("\nList of Assets:");
        for (int i = 0; i < assetsList.size(); i++) {
            System.out.println((i + 1) + ": " + assetsList.get(i).displayAsset());
        }
    
        System.out.println("\nSelect an asset to update:");
        try {
            int assetSelection = input.nextInt();
            if (assetSelection > 0 && assetSelection <= assetsList.size()) {
                Assets selectedAsset = assetsList.get(assetSelection - 1);
    
                System.out.println("Updating asset: " + selectedAsset.displayAsset());
                
                if (selectedAsset instanceof Stock) {
                    updateStock((Stock) selectedAsset);
                } else if (selectedAsset instanceof Bond) {
                    updateBond((Bond) selectedAsset);
                } else if (selectedAsset instanceof MutualFund) {
                    updateMutualFund((MutualFund) selectedAsset);
                }
            } else if (assetSelection == 0) {
                return;
            } else {
                System.err.println("Invalid selection.");
            }
        } catch (InputMismatchException e) {
            System.err.println("Invalid input. Please enter an integer.");
            input.next();
        }
    }

    public static void updateStock(Stock stock) {
        System.out.println("\nEnter new stock name (current: " + stock.get_name() + "):");
        String name = input.next();
        System.out.println("Enter new stock value (current: " + stock.get_value() + "):");
        float value = input.nextFloat();
        System.out.println("Enter new ticker (current: " + stock.get_ticker() + "):");
        String ticker = input.next();
        System.out.println("Enter new quantity (current: " + stock.get_quantity() + "):");
        float quantity = input.nextFloat();
        System.out.println("Enter new yield (current: " + stock.get_yield() + "):");
        float yield = input.nextFloat();
    
        stock.set_name(name);
        stock.set_value(value);
        stock.set_ticker(ticker);
        stock.set_quantity(quantity);
        stock.set_yield(yield);
    
        System.out.println("Stock updated successfully.");
    }
    
    public static void updateBond(Bond bond) {
        System.out.println("\nEnter new bond name (current: " + bond.get_name() + "):");
        String name = input.next();
        System.out.println("Enter new bond value (current: " + bond.get_value() + "):");
        float value = input.nextFloat();
        System.out.println("Enter new interest rate (current: " + bond.get_interestRate() + "):");
        float interestRate = input.nextFloat();
        System.out.println("Enter new days to maturity (current: " + bond.get_daysToMaturity() + "):");
        int daysToMaturity = input.nextInt();
    
        bond.set_name(name);
        bond.set_value(value);
        bond.set_interestRate(interestRate);
        bond.set_daysToMaturity(daysToMaturity);
    
        System.out.println("Bond updated successfully.");
    }
    
    public static void updateMutualFund(MutualFund mutualFund) {
        System.out.println("\nEnter new mutual fund name (current: " + mutualFund.get_name() + "):");
        String name = input.next();
        System.out.println("Enter new mutual fund value (current: " + mutualFund.get_value() + "):");
        float value = input.nextFloat();
        System.out.println("Enter new expense ratio (current: " + mutualFund.get_expenseRatio() + "):");
        float expenseRatio = input.nextFloat();
    
        mutualFund.set_name(name);
        mutualFund.set_value(value);
        mutualFund.set_expenseRatio(expenseRatio);
    
        System.out.println("Mutual fund updated successfully.");
    }

    public static void deleteAsset() {
        if (assetsList.isEmpty()) {
            System.out.println("No assets available to delete.");
            return;
        }
    
        System.out.println("\nList of Assets:");
        for (int i = 0; i < assetsList.size(); i++) {
            System.out.println((i + 1) + ": " + assetsList.get(i).displayAsset());
        }
    
        System.out.println("\nSelect an asset to delete:");
        try {
            int assetSelection = input.nextInt();
            if (assetSelection > 0 && assetSelection <= assetsList.size()) {
                Assets selectedAsset = assetsList.get(assetSelection - 1);
    
                System.out.println("Are you sure you want to delete this asset? (Y/N): " + selectedAsset.displayAsset());
                String confirmation = input.next();
                if (confirmation.equalsIgnoreCase("Y")) {
                    assetsList.remove(selectedAsset);
                    System.out.println("Asset deleted successfully.");
                } else {
                    System.out.println("Asset deletion canceled.");
                }
            } else if (assetSelection == 0) {
                return;
            } else {
                System.err.println("Invalid selection.");
            }
        } catch (InputMismatchException e) {
            System.err.println("Invalid input. Please enter an integer.");
            input.next();
        }
    }    

    public static void readIntermediaries() {
        System.out.println("\nList of Intermediaries:");
        // for (Intermediaries intermediary : intermediariesList) {
        //     intermediary.displayIntermediary();
        // }
        if (intermediariesList.isEmpty()) {
            System.out.println("No intermediaries available.");
        } else {
            for (int i = 0; i < intermediariesList.size(); i++) {
                System.out.println((i + 1) + ": " + intermediariesList.get(i).displayIntermediary());
            }
        }
    }

    public static void updateIntermediary() {
        do {
            System.out.println("\nList of Intermediaries:");
            if (intermediariesList.isEmpty()) {
                System.out.println("No intermediaries available.");
                return;
            } else {
                for (int i = 0; i < intermediariesList.size(); i++) {
                    System.out.println((i + 1) + ": " + intermediariesList.get(i).displayIntermediary());
                }
                System.out.println((intermediariesList.size() + 1) + ": Back to Intermediaries Menu");
            }
    
            System.out.println("\nSelect an intermediary to update:");
    
            try {
                selection = input.nextInt();
    
                if (selection > 0 && selection <= intermediariesList.size()) {
                    Intermediaries intermediaryToUpdate = intermediariesList.get(selection - 1);
                    updateIntermediaryDetails(intermediaryToUpdate);
                    System.out.println("Intermediary updated successfully.");
                    return;
                } else if (selection == intermediariesList.size() + 1) {
                    return;
                } else {
                    System.err.println("Invalid Selection");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer.");
                input.next();
            }
        } while (true);
    }
    
    public static void updateIntermediaryDetails(Intermediaries intermediary) {
        if (intermediary instanceof Broker) {
            updateBrokerDetails((Broker) intermediary);
        } else if (intermediary instanceof Bank) {
            updateBankDetails((Bank) intermediary);
        } else if (intermediary instanceof MutualFundManager) {
            updateMutualFundManagerDetails((MutualFundManager) intermediary);
        } else {
            System.err.println("Unknown intermediary type.");
        }
    }
    
    public static void updateBrokerDetails(Broker broker) {
        System.out.println("Enter new broker name (current: " + broker.get_name() + "):");
        String name = input.next();
        broker.set_name(name);
    
        System.out.println("Enter new commission (current: " + broker.get_commission() + "):");
        float commission = input.nextFloat();
        broker.set_commission(commission);
    }
    
    public static void updateBankDetails(Bank bank) {
        System.out.println("Enter new bank name (current: " + bank.get_name() + "):");
        String name = input.next();
        bank.set_name(name);
    
        System.out.println("Enter new interest rate (current: " + bank.get_interestRate() + "):");
        float interestRate = input.nextFloat();
        bank.set_interestRate(interestRate);
    }
    
    public static void updateMutualFundManagerDetails(MutualFundManager manager) {
        System.out.println("Enter new manager name (current: " + manager.get_name() + "):");
        String name = input.next();
        manager.set_name(name);
    
        System.out.println("Enter new management fee (current: " + manager.get_managementFee() + "):");
        float managementFee = input.nextFloat();
        manager.set_managementFee(managementFee);

        System.out.println("Enter new employee number (current: " + manager.get_employeeNumber() + "):");
        String employeeNumber = input.next();
        manager.set_employeeNumber(employeeNumber);
    }

    public static void deleteIntermediary() {
        do {
            System.out.println("\nList of Intermediaries:");
            if (intermediariesList.isEmpty()) {
                System.out.println("No intermediaries available.");
                return;
            } else {
                for (int i = 0; i < intermediariesList.size(); i++) {
                    System.out.println((i + 1) + ": " + intermediariesList.get(i).displayIntermediary());
                }
                System.out.println((intermediariesList.size() + 1) + ": Back to Intermediaries Menu");
            }

            System.out.println("\nSelect an intermediary to delete:");

            try {
                selection = input.nextInt();

                if (selection > 0 && selection <= intermediariesList.size()) {
                    Intermediaries intermediaryToDelete = intermediariesList.get(selection - 1);
                    System.out.println("Are you sure you want to delete this intermediary? (Y/N): " + intermediaryToDelete.displayIntermediary());
                    
                    String confirmation = input.next();
                    if (confirmation.equalsIgnoreCase("Y")) {
                        intermediariesList.remove(intermediaryToDelete);
                        System.out.println("Intermediary deleted successfully.");
                        return;
                    } else if (confirmation.equalsIgnoreCase("N")) {
                        System.out.println("Deletion cancelled.");
                        return;
                    } else {
                        System.err.println("Invalid input. Deletion cancelled.");
                        return;
                    }
                } else if (selection == intermediariesList.size() + 1) {
                    return;
                } else {
                    System.err.println("Invalid Selection");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer.");
                input.next();
            }
        } while (true);
    }

    public static void PortfolioMenu() {
        do {
            System.out.println("\nPortfolio Menu:");
            System.out.println("1. Record Snapshot");
            System.out.println("2. Display Historical Listings");
            System.out.println("3. Calculate Annual Return");
            System.out.println("4. Back to Main Menu");
    
            try {
                selection = input.nextInt();
    
                switch (selection) {
                    case 1:
                        recordSnapshot();
                        break;
                    case 2:
                        displayHistoricalListings();
                        break;
                    case 3:
                        annualReturn();
                        break;
                    case 4:
                        return;
                    default:
                        System.err.println("Invalid Selection");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer.");
                input.next();
            }
    
        } while (true);
    }     

    private static void displayHistoricalListings() {
        if (portfolioList.isEmpty()) {
            System.out.println("No historical data available.");
            return;
        }
    
        System.out.println("Choose sort order: 1 for Ascending, 2 for Descending");
        int sortOrder = input.nextInt();
    
        portfolioList.sort((snapshot1, snapshot2) -> {
            Date date1 = snapshot1.getSnapshotDate();
            Date date2 = snapshot2.getSnapshotDate();
    
            if (date1 == null || date2 == null) {
                System.err.println("Error: Snapshot date is null.");
                return 0;
            }
    
            return sortOrder == 2 ? date2.compareTo(date1) : date1.compareTo(date2);
        });
    
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
        for (HistoricalSnapshots2 snapshot : portfolioList) {
            System.out.println("Snapshot recorded on " + dateFormat.format(snapshot.getSnapshotDate()) + ":");
            System.out.println(snapshot.displaySnapshot());
        }
    }    

    private static void recordSnapshot() {
        if (assetsList.isEmpty()) {
            System.out.println("No assets available to record a snapshot.");
            return;
        }

        Date currentDate = get_date();
        increment_date();

        for (Assets asset : assetsList) {
            HistoricalSnapshots2 snapshot = null;

            if (asset instanceof Stock) {
                Stock stock = (Stock) asset;
                snapshot = new StockSnapshot.Builder()
                        .setassetName(stock.get_name())
                        .setassetValue(stock.get_value())
                        .setticker(stock.get_ticker())
                        .setquantity(stock.get_quantity())
                        .setyield(stock.get_yield())
                        .setintermediaryName(stock.get_intermediary().get_name())
                        .setcommission(((Broker) stock.get_intermediary()).get_commission())
                        .sethistoricalSnapshots(new HashMap<>())
                        .setsnapshotDate(currentDate)
                        .build();
            } else if (asset instanceof Bond) {
                Bond bond = (Bond) asset;
                snapshot = new BondSnapshot.Builder()
                        .setassetName(bond.get_name())
                        .setassetValue(bond.get_value())
                        .setinterestRate(bond.get_interestRate())
                        .setdaysToMaturity(bond.get_daysToMaturity())
                        .setintermediaryName(bond.get_intermediary().get_name())
                        .setintermediaryInterestRate(((Bank) bond.get_intermediary()).get_interestRate())
                        .sethistoricalSnapshots(new HashMap<>())
                        .setsnapshotDate(currentDate)
                        .build();
            } else if (asset instanceof MutualFund) {
                MutualFund mutualFund = (MutualFund) asset;
                snapshot = new MutualFundSnapshot.Builder()
                        .setassetName(mutualFund.get_name())
                        .setassetValue(mutualFund.get_value())
                        .setexpenseRatio(mutualFund.get_expenseRatio())
                        .setemployeeNumber(((MutualFundManager) mutualFund.get_intermediary()).get_employeeNumber())
                        .setintermediaryName(mutualFund.get_intermediary().get_name())
                        .setmanagementFee(((MutualFundManager) mutualFund.get_intermediary()).get_managementFee())
                        .sethistoricalSnapshots(new HashMap<>())
                        .setsnapshotDate(currentDate)
                        .build();
            } else {
                System.err.println("Unsupported asset type.");
                continue;
            }

            if (snapshot != null) {
                snapshot = snapshot.withNewSnapshot(currentDate);
                portfolioList.add(snapshot);
                System.out.println("Snapshot recorded: " + snapshot.displaySnapshot());
            }
        }

        System.out.println("Snapshot recorded on " + currentDate);
        System.out.println("Current portfolio list size: " + portfolioList.size());
    }       

    private static void annualReturn() {
        if (assetsList.isEmpty()) {
            System.out.println("No assets available to calculate returns.");
            return;
        }
    
        StockSvc stockSvc = new StockSvc("127.0.0.1", 7000);
    
        double totalAnnualReturn = 0;
        for (Assets asset : assetsList) {
            if (asset instanceof Stock) {
                Stock stock = (Stock) asset;
                double stockReturn = stockSvc.calculateStockReturn(
                        stock.get_name(),
                        stock.get_value(),
                        stock.get_quantity(),
                        stock.get_yield(),
                        ((Broker) stock.get_intermediary()).get_commission()
                );
                totalAnnualReturn += stockReturn;
            } else {
                totalAnnualReturn += asset.calculateAnnualReturn();
            }
        }
    
        System.out.printf("Total Annual Return: %.2f%n", totalAnnualReturn);
    }
    
    private static void startServer() {
        try {
            stockServer = new StockServer();
            stockServer.start();
            System.out.println("gRPC Server started...");
        } catch (Exception e) {
            System.err.println("Failed to start gRPC server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void stopServer() {
        if (stockServer != null) {
            stockServer.stop();
            System.out.println("gRPC Server stopped...");
        }
    }
    
}   