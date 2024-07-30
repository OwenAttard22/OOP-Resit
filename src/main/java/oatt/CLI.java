package oatt;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

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
                        System.out.println("Create Intermediary");
                        break;
                    case 2: // Read
                        System.out.println("Read Intermediaries");
                        break;
                    case 3: // Update
                        System.out.println("Update Intermediaries");
                        break;
                    case 4: // Delete
                        System.out.println("Delete Intermediaries");
                        break;
                    case 5: // Main Menu
                        MainMenu();
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
                        AssetsMenu();
                        return;
                    case 2: // Bond
                        createBond();
                        AssetsMenu();
                        return;
                    case 3: // Mutual Fund
                        createMutualFund();
                        AssetsMenu();
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

        Stock newStock = new Stock(name, value, ticker, quantity, yield);
        assetsList.add(newStock);
        System.out.println("Stock created successfully.");
    }

    public static void createBond() {
        System.out.println("\nEnter bond name:");
        String name = input.next();
        System.out.println("Enter bond value:");
        float value = input.nextFloat();
        System.out.println("Enter interest rate:");
        float interestRate = input.nextFloat();
        System.out.println("Enter days to maturity:");
        int daysToMaturity = input.nextInt();

        Bond newBond = new Bond(name, value, interestRate, daysToMaturity);
        assetsList.add(newBond);
        System.out.println("Bond created successfully.");
    }

    public static void createMutualFund() {
        System.out.println("\nEnter mutual fund name:");
        String name = input.next();
        System.out.println("Enter mutual fund value:");
        float value = input.nextFloat();
        System.out.println("Enter expense ratio:");
        float expenseRatio = input.nextFloat();

        MutualFund newMutualFund = new MutualFund(name, value, expenseRatio);
        assetsList.add(newMutualFund);
        System.out.println("Mutual fund created successfully.");
    }

    public static void readAssets() {
        if (assetsList.isEmpty()) {
            System.out.println("No assets available.");
        } else {
            for (int i = 0; i < assetsList.size(); i++) {
                System.out.println((i + 1) + ": " + assetsList.get(i).displayAsset());
            }
        }
    }

    public static void updateAsset() {
        System.out.println("\nEnter the asset number to update:");
        int index = input.nextInt() - 1;

        if (index >= 0 && index < assetsList.size()) {
            Assets asset = assetsList.get(index);

            if (asset instanceof Stock) {
                updateStock((Stock) asset);
            } else if (asset instanceof Bond) {
                updateBond((Bond) asset);
            } else if (asset instanceof MutualFund) {
                updateMutualFund((MutualFund) asset);
            } else {
                System.out.println("Unknown asset type.");
            }
        } else {
            System.out.println("Invalid asset number.");
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
        readAssets();
        System.out.println("Enter the asset number to delete:");
        int index = input.nextInt() - 1;

        if (index >= 0 && index < assetsList.size()) {
            assetsList.remove(index);
            System.out.println("Asset deleted successfully.");
        } else {
            System.out.println("Invalid asset number.");
        }
    }
}