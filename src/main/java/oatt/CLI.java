package oatt;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private static Scanner input = new Scanner(System.in);
    private static int selection = -1;
    private static ArrayList<Assets> assetsList = new ArrayList<>();
    private static ArrayList<Intermediaries> intermediariesList = new ArrayList<>();

    public static void InitMenu() {
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
                        System.out.println("Portfolio"); // to-do
                        break;
                    case 2: // Assets
                        AssetsMenu();
                        break;
                    case 3: // Intermediaries
                        IntermediariesMenu();
                        break;
                    case 4: // Exit and save
                        System.out.println("Exiting and saving....");
                        System.exit(0); // remember to change for save
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
                    case 1: // Broker
                        createBroker();
                        return;
                    case 2: // Bank
                        createBank();
                        return;
                    case 3: // Mutual Fund Manager
                        createMutualFundManager();
                        return;
                    case 4: // Back to Intermediaries Menu
                        return; // Exit the createIntermediary loop
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
        // Implement update functionality
    }

    public static void deleteAsset() {
        // Implement delete functionality
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
                    intermediariesList.remove(intermediaryToDelete);
                    System.out.println("Intermediary deleted successfully.");
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

    public static Broker findBrokerByName(String name) {
        for (Intermediaries intermediary : intermediariesList) {
            if (intermediary instanceof Broker && intermediary.get_name().equalsIgnoreCase(name)) {
                return (Broker) intermediary;
            }
        }
        return null;
    }

    public static Bank findBankByName(String name) {
        for (Intermediaries intermediary : intermediariesList) {
            if (intermediary instanceof Bank && intermediary.get_name().equalsIgnoreCase(name)) {
                return (Bank) intermediary;
            }
        }
        return null;
    }

    public static MutualFundManager findMutualFundManagerByName(String name) {
        for (Intermediaries intermediary : intermediariesList) {
            if (intermediary instanceof MutualFundManager && intermediary.get_name().equalsIgnoreCase(name)) {
                return (MutualFundManager) intermediary;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        InitMenu();
    }
}
