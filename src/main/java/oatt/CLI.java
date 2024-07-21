package oatt;

import java.util.Scanner;
import java.util.InputMismatchException;

public class CLI {
    private static Scanner input = new Scanner(System.in);
    private static int selection = -1;
    public static void main(String[] args){
        InitMenu();
    }

    public static void InitMenu(){

        do {
            System.out.println("Initialisation:");
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

    public static void MainMenu(){

        do {
            System.out.println("Main Menu:");
            System.out.println("1. Portfolio");
            System.out.println("2. Assets Menu");
            System.out.println("3. Intermediaries Menu");
            System.out.println("4. Exit and save");

            try {
                selection = input.nextInt();

                switch (selection) {
                    case 1: // Profolio
                        System.out.println("Protfolio"); // to-do
                        break;
                    case 2: // Assets
                        // System.out.println("Assets Menu");
                        AssetsMenu();
                        break;
                    case 3: // Intermediaries
                        // System.out.println("Intermediary Menu");
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

        } while (selection != 4);

        input.close();
    }

    public static void AssetsMenu(){

        do {
            System.out.println("Assets Menu:");
            System.out.println("1. Create Asset");
            System.out.println("2. Read Assets");
            System.out.println("3. Update Assets");
            System.out.println("4. Delete Assets");
            System.out.println("5. Back to Main Menu");

            try {
                selection = input.nextInt();

                switch (selection) {
                    case 1: // Create
                        System.out.println("Create asset");
                        break;
                    case 2: // Read
                        System.out.println("Read assets");
                        break;
                    case 3: // Update
                        System.out.println("Update assets");
                        break;
                    case 4: // Delete
                        System.out.println("Delete assets");
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

    public static void IntermediariesMenu(){

        do {
            System.out.println("Intermediaries Menu:");
            System.out.println("1. Create Intermediary");
            System.out.println("2. Read Intermediaries");
            System.out.println("3. Update Intermediaries");
            System.out.println("4. Delete Intermediaries");
            System.out.println("5. Back to Main Menu");

            try {
                selection = input.nextInt();

                switch (selection) {
                    case 1: // Create
                        System.out.println("Create Intermediarie");
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
}
