#include "CLI.h"
#include <ctime>
#include <iomanip>
#include <sstream>
#include <fstream>
#include <filesystem>
#include <vector>
#include <iostream>
#include <string>

int CLI::selection = -1;
std::vector<Assets*> CLI::assetsList;
std::vector<Intermediaries*> CLI::intermediariesList;
const std::string CLI::SAVE_DIRECTORY = "Task-1/C++ Version/Saves/";
std::vector<Portfolio*> CLI::portfolioList;
std::time_t CLI::_date;

std::time_t CLI::get_date() {
    return _date;
}

void CLI::set_date(std::time_t date) {
    _date = date;
}

void CLI::increment_date() {
    _date += 86400;
}

std::string getCurrentTimestamp() {
    std::time_t now = std::time(nullptr);
    std::tm* localTime = std::localtime(&now);

    std::ostringstream oss;
    oss << std::put_time(localTime, "%Y%m%d_%H%M%S");
    return oss.str();
}

void CLI::InitMenu() {
    std::cout << "Task 1: C++ Version";
    set_date(std::time(0));
    do {
        std::cout << "\nInitialisation:\n";
        std::cout << "1. New Save\n";
        std::cout << "2. Load Save\n";
        std::cout << "3. Exit Application\n";

        std::cin >> selection;

        switch (selection) {
            case 1:
                std::cout << "New save\n";
                MainMenu();
                break;
            case 2:
                std::cout << "Load save\n";
                loadState();
                MainMenu();
                break;
            case 3:
                std::cout << "Exiting application...\n";
                break;
            default:
                std::cerr << "Invalid Selection\n";
                break;
        }
    } while (selection != 3);
}

void CLI::MainMenu() {
    do {
        std::cout << "\nMain Menu:\n";
        std::cout << "1. Portfolio\n";
        std::cout << "2. Assets Menu\n";
        std::cout << "3. Intermediaries Menu\n";
        std::cout << "4. Exit and save\n";

        std::cin >> selection;

        switch (selection) {
            case 1:
                PortfolioMenu();
                break;
            case 2:
                AssetsMenu();
                break;
            case 3:
                IntermediariesMenu();
                break;
            case 4:
                saveState();
                std::cout << "Exiting and saving....\n";
                exit(0);
                break;
            default:
                std::cerr << "Invalid Selection\n";
                break;
        }
    } while (true);
}

void CLI::AssetsMenu() {
    do {
        std::cout << "\nAssets Menu:\n";
        std::cout << "1. Create Asset\n";
        std::cout << "2. Read Assets\n";
        std::cout << "3. Update Assets\n";
        std::cout << "4. Delete Assets\n";
        std::cout << "5. Back to Main Menu\n";

        std::cin >> selection;

        switch (selection) {
            case 1:
                createAsset();
                break;
            case 2:
                readAssets();
                break;
            case 3:
                updateAsset();
                break;
            case 4:
                deleteAsset();
                break;
            case 5:
                return;
            default:
                std::cerr << "Invalid Selection\n";
                break;
        }
    } while (true);
}

void CLI::IntermediariesMenu() {
    do {
        std::cout << "\nIntermediaries Menu:\n";
        std::cout << "1. Create Intermediary\n";
        std::cout << "2. Read Intermediaries\n";
        std::cout << "3. Update Intermediaries\n";
        std::cout << "4. Delete Intermediaries\n";
        std::cout << "5. Back to Main Menu\n";

        std::cin >> selection;

        switch (selection) {
            case 1:
                createIntermediary();
                break;
            case 2:
                readIntermediaries();
                break;
            case 3:
                updateIntermediary();
                break;
            case 4:
                deleteIntermediary();
                break;
            case 5:
                return;
            default:
                std::cerr << "Invalid Selection\n";
                break;
        }
    } while (true);
}

void CLI::createAsset() {
    do {
        std::cout << "\nChoose which type of asset to create:\n";
        std::cout << "1. Stock\n";
        std::cout << "2. Bond\n";
        std::cout << "3. Mutual Fund\n";
        std::cout << "4. Back to Assets Menu\n";

        std::cin >> selection;

        switch (selection) {
            case 1:
                createStock();
                return;
            case 2:
                createBond();
                return;
            case 3:
                createMutualFund();
                return;
            case 4:
                return;
            default:
                std::cerr << "Invalid Selection\n";
                break;
        }
    } while (true);
}

void CLI::createStock() {
    if (intermediariesList.empty()) {
        std::cout << "No brokers available. Please create a broker first.\n";
        return;
    }

    std::vector<Broker*> brokers;
    for (auto* intermediary : intermediariesList) {
        if (auto* broker = dynamic_cast<Broker*>(intermediary)) {
            brokers.push_back(broker);
        }
    }

    if (brokers.empty()) {
        std::cout << "No brokers available. Please create a broker first.\n";
        return;
    }

    std::string name, ticker;
    float value, quantity, yield;

    std::cout << "\nEnter stock name: ";
    std::cin >> name;
    std::cout << "Enter stock value: ";
    std::cin >> value;
    std::cout << "Enter ticker: ";
    std::cin >> ticker;
    std::cout << "Enter quantity: ";
    std::cin >> quantity;
    std::cout << "Enter yield: ";
    std::cin >> yield;

    std::cout << "\nSelect a broker from the list:\n";
    for (size_t i = 0; i < brokers.size(); ++i) {
        std::cout << (i + 1) << ": " << brokers[i]->displayIntermediary() << "\n";
    }

    int brokerSelection;
    std::cin >> brokerSelection;

    if (brokerSelection > 0 && static_cast<size_t>(brokerSelection) <= brokers.size()) {
        Broker* selectedBroker = brokers[brokerSelection - 1];
        Stock* newStock = new Stock(name, value, ticker, quantity, yield, selectedBroker);
        assetsList.push_back(newStock);
        std::cout << "Stock created successfully.\n";
    } else {
        std::cerr << "Invalid selection.\n";
    }
}

void CLI::createBond() {
    if (intermediariesList.empty()) {
        std::cout << "No banks available. Please create a bank first.\n";
        return;
    }

    std::vector<Bank*> banks;
    for (auto* intermediary : intermediariesList) {
        if (auto* bank = dynamic_cast<Bank*>(intermediary)) {
            banks.push_back(bank);
        }
    }

    if (banks.empty()) {
        std::cout << "No banks available. Please create a bank first.\n";
        return;
    }

    std::string name;
    float value, interestRate;
    int daysToMaturity;

    std::cout << "\nEnter bond name: ";
    std::cin >> name;
    std::cout << "Enter bond value: ";
    std::cin >> value;
    std::cout << "Enter interest rate: ";
    std::cin >> interestRate;
    std::cout << "Enter days to maturity: ";
    std::cin >> daysToMaturity;

    std::cout << "\nSelect a bank from the list:\n";
    for (size_t i = 0; i < banks.size(); ++i) {
        std::cout << (i + 1) << ": " << banks[i]->displayIntermediary() << "\n";
    }

    int bankSelection;
    std::cin >> bankSelection;

    if (bankSelection > 0 && static_cast<size_t>(bankSelection) <= banks.size()) {
        Bank* selectedBank = banks[bankSelection - 1];
        Bond* newBond = new Bond(name, value, interestRate, daysToMaturity, selectedBank);
        assetsList.push_back(newBond);
        std::cout << "Bond created successfully.\n";
    } else {
        std::cerr << "Invalid selection.\n";
    }
}

void CLI::createMutualFund() {
    if (intermediariesList.empty()) {
        std::cout << "No mutual fund managers available. Please create a mutual fund manager first.\n";
        return;
    }

    std::vector<MutualFundManager*> managers;
    for (auto* intermediary : intermediariesList) {
        if (auto* manager = dynamic_cast<MutualFundManager*>(intermediary)) {
            managers.push_back(manager);
        }
    }

    if (managers.empty()) {
        std::cout << "No mutual fund managers available. Please create a mutual fund manager first.\n";
        return;
    }

    std::string name;
    float value, expenseRatio;

    std::cout << "\nEnter mutual fund name: ";
    std::cin >> name;
    std::cout << "Enter mutual fund value: ";
    std::cin >> value;
    std::cout << "Enter expense ratio: ";
    std::cin >> expenseRatio;

    std::cout << "\nSelect a mutual fund manager from the list:\n";
    for (size_t i = 0; i < managers.size(); ++i) {
        std::cout << (i + 1) << ": " << managers[i]->displayIntermediary() << "\n";
    }

    int managerSelection;
    std::cin >> managerSelection;

    if (managerSelection > 0 && static_cast<size_t>(managerSelection) <= managers.size()) {
        MutualFundManager* selectedManager = managers[managerSelection - 1];
        MutualFund* newMutualFund = new MutualFund(name, value, expenseRatio, selectedManager);
        assetsList.push_back(newMutualFund);
        std::cout << "Mutual fund created successfully.\n";
    } else {
        std::cerr << "Invalid selection.\n";
    }
}

void CLI::createIntermediary() {
    do {
        std::cout << "\nChoose which type of intermediary to create:\n";
        std::cout << "1. Broker\n";
        std::cout << "2. Bank\n";
        std::cout << "3. Mutual Fund Manager\n";
        std::cout << "4. Back to Intermediaries Menu\n";

        std::cin >> selection;

        switch (selection) {
            case 1:
                createBroker();
                return;
            case 2:
                createBank();
                return;
            case 3:
                createMutualFundManager();
                return;
            case 4:
                return;
            default:
                std::cerr << "Invalid Selection\n";
                break;
        }
    } while (true);
}

void CLI::createBroker() {
    std::string name;
    float commission;

    std::cout << "\nEnter broker name: ";
    std::cin >> name;
    std::cout << "Enter commission: ";
    std::cin >> commission;

    Broker* newBroker = new Broker(name, commission);
    intermediariesList.push_back(newBroker);
    std::cout << "Broker created successfully.\n";
}

void CLI::createBank() {
    std::string name;
    float interestRate;

    std::cout << "\nEnter bank name: ";
    std::cin >> name;
    std::cout << "Enter interest rate: ";
    std::cin >> interestRate;

    Bank* newBank = new Bank(name, interestRate);
    intermediariesList.push_back(newBank);
    std::cout << "Bank created successfully.\n";
}

void CLI::createMutualFundManager() {
    std::string name, employeeNumber;
    float managementFee;

    std::cout << "\nEnter mutual fund manager name: ";
    std::cin >> name;
    std::cout << "Enter management fee: ";
    std::cin >> managementFee;
    std::cout << "Enter employee number: ";
    std::cin >> employeeNumber;

    MutualFundManager* newManager = new MutualFundManager(name, employeeNumber, managementFee);
    intermediariesList.push_back(newManager);
    std::cout << "Mutual fund manager created successfully.\n";
}

void CLI::readAssets() {
    std::cout << "\nList of Assets:\n";
    if (assetsList.empty()) {
        std::cout << "No assets available.\n";
    } else {
        for (size_t i = 0; i < assetsList.size(); ++i) {
            std::cout << (i + 1) << ": " << assetsList[i]->displayAsset() << "\n";
        }
    }
}

void CLI::updateAsset() {
    if (assetsList.empty()) {
        std::cout << "No assets available to update.\n";
        return;
    }

    std::cout << "\nList of Assets:\n";
    for (size_t i = 0; i < assetsList.size(); ++i) {
        std::cout << (i + 1) << ": " << assetsList[i]->displayAsset() << "\n";
    }

    std::cout << "\nSelect an asset to update:\n";
    std::cin >> selection;

    if (selection > 0 && selection <= assetsList.size()) {
        Assets* selectedAsset = assetsList[selection - 1];
        if (dynamic_cast<Stock*>(selectedAsset)) {
            updateStock(dynamic_cast<Stock*>(selectedAsset));
        } else if (dynamic_cast<Bond*>(selectedAsset)) {
            updateBond(dynamic_cast<Bond*>(selectedAsset));
        } else if (dynamic_cast<MutualFund*>(selectedAsset)) {
            updateMutualFund(dynamic_cast<MutualFund*>(selectedAsset));
        }
    } else {
        std::cerr << "Invalid selection.\n";
    }
}

void CLI::updateStock(Stock* stock) {
    std::string name, ticker;
    float value, quantity, yield;

    std::cout << "\nEnter new stock name (current: " << stock->get_name() << "): ";
    std::cin >> name;
    std::cout << "Enter new stock value (current: " << stock->get_value() << "): ";
    std::cin >> value;
    std::cout << "Enter new ticker (current: " << stock->get_ticker() << "): ";
    std::cin >> ticker;
    std::cout << "Enter new quantity (current: " << stock->get_quantity() << "): ";
    std::cin >> quantity;
    std::cout << "Enter new yield (current: " << stock->get_yield() << "): ";
    std::cin >> yield;

    stock->set_name(name);
    stock->set_value(value);
    stock->set_ticker(ticker);
    stock->set_quantity(quantity);
    stock->set_yield(yield);

    std::cout << "Stock updated successfully.\n";
}

void CLI::updateBond(Bond* bond) {
    std::string name;
    float value, interestRate;
    int daysToMaturity;

    std::cout << "\nEnter new bond name (current: " << bond->get_name() << "): ";
    std::cin >> name;
    std::cout << "Enter new bond value (current: " << bond->get_value() << "): ";
    std::cin >> value;
    std::cout << "Enter new interest rate (current: " << bond->get_interestRate() << "): ";
    std::cin >> interestRate;
    std::cout << "Enter new days to maturity (current: " << bond->get_daysToMaturity() << "): ";
    std::cin >> daysToMaturity;

    bond->set_name(name);
    bond->set_value(value);
    bond->set_interestRate(interestRate);
    bond->set_daysToMaturity(daysToMaturity);

    std::cout << "Bond updated successfully.\n";
}

void CLI::updateMutualFund(MutualFund* mutualFund) {
    std::string name;
    float value, expenseRatio;

    std::cout << "\nEnter new mutual fund name (current: " << mutualFund->get_name() << "): ";
    std::cin >> name;
    std::cout << "Enter new mutual fund value (current: " << mutualFund->get_value() << "): ";
    std::cin >> value;
    std::cout << "Enter new expense ratio (current: " << mutualFund->get_expenseRatio() << "): ";
    std::cin >> expenseRatio;

    mutualFund->set_name(name);
    mutualFund->set_value(value);
    mutualFund->set_expenseRatio(expenseRatio);

    std::cout << "Mutual fund updated successfully.\n";
}

void CLI::deleteAsset() {
    if (assetsList.empty()) {
        std::cout << "No assets available to delete.\n";
        return;
    }

    std::cout << "\nList of Assets:\n";
    for (size_t i = 0; i < assetsList.size(); ++i) {
        std::cout << (i + 1) << ": " << assetsList[i]->displayAsset() << "\n";
    }

    std::cout << "\nSelect an asset to delete:\n";
    std::cin >> selection;

    if (selection > 0 && static_cast<size_t>(selection) <= assetsList.size()) {
        Assets* selectedAsset = assetsList[selection - 1];

        std::cout << "Are you sure you want to delete this asset? (Y/N): " << selectedAsset->displayAsset() << "\n";
        std::string confirmation;
        std::cin >> confirmation;

        if (confirmation == "Y" || confirmation == "y") {
            assetsList.erase(assetsList.begin() + selection - 1);
            std::cout << "Asset deleted successfully.\n";
        } else {
            std::cout << "Asset deletion canceled.\n";
        }
    } else {
        std::cerr << "Invalid selection.\n";
    }
}

void CLI::readIntermediaries() {
    std::cout << "\nList of Intermediaries:\n";
    if (intermediariesList.empty()) {
        std::cout << "No intermediaries available.\n";
    } else {
        for (size_t i = 0; i < intermediariesList.size(); ++i) {
            std::cout << (i + 1) << ": " << intermediariesList[i]->displayIntermediary() << "\n";
        }
    }
}

void CLI::updateIntermediary() {
    if (intermediariesList.empty()) {
        std::cout << "No intermediaries available.\n";
        return;
    }

    std::cout << "\nList of Intermediaries:\n";
    for (size_t i = 0; i < intermediariesList.size(); ++i) {
        std::cout << (i + 1) << ": " << intermediariesList[i]->displayIntermediary() << "\n";
    }
    std::cout << (intermediariesList.size() + 1) << ": Back to Intermediaries Menu\n";

    std::cout << "\nSelect an intermediary to update:\n";
    std::cin >> selection;

    if (selection > 0 && static_cast<size_t>(selection) <= intermediariesList.size()) {
        Intermediaries* intermediaryToUpdate = intermediariesList[selection - 1];
        updateIntermediaryDetails(intermediaryToUpdate);
        std::cout << "Intermediary updated successfully.\n";
    } else if (selection == intermediariesList.size() + 1) {
        return;
    } else {
        std::cerr << "Invalid Selection\n";
    }
}

void CLI::updateIntermediaryDetails(Intermediaries* intermediary) {
    if (auto* broker = dynamic_cast<Broker*>(intermediary)) {
        updateBrokerDetails(broker);
    } else if (auto* bank = dynamic_cast<Bank*>(intermediary)) {
        updateBankDetails(bank);
    } else if (auto* manager = dynamic_cast<MutualFundManager*>(intermediary)) {
        updateMutualFundManagerDetails(manager);
    } else {
        std::cerr << "Unknown intermediary type.\n";
    }
}

void CLI::updateBrokerDetails(Broker* broker) {
    std::string name;
    float commission;

    std::cout << "Enter new broker name (current: " << broker->get_name() << "): ";
    std::cin >> name;
    broker->set_name(name);

    std::cout << "Enter new commission (current: " << broker->get_commission() << "): ";
    std::cin >> commission;
    broker->set_commission(commission);
}

void CLI::updateBankDetails(Bank* bank) {
    std::string name;
    float interestRate;

    std::cout << "Enter new bank name (current: " << bank->get_name() << "): ";
    std::cin >> name;
    bank->set_name(name);

    std::cout << "Enter new interest rate (current: " << bank->get_interestRate() << "): ";
    std::cin >> interestRate;
    bank->set_interestRate(interestRate);
}

void CLI::updateMutualFundManagerDetails(MutualFundManager* manager) {
    std::string name, employeeNumber;
    float managementFee;

    std::cout << "Enter new manager name (current: " << manager->get_name() << "): ";
    std::cin >> name;
    manager->set_name(name);

    std::cout << "Enter new management fee (current: " << manager->get_managementFee() << "): ";
    std::cin >> managementFee;
    manager->set_managementFee(managementFee);

    std::cout << "Enter new employee number (current: " << manager->get_employeeNumber() << "): ";
    std::cin >> employeeNumber;
    manager->set_employeeNumber(employeeNumber);
}

void CLI::deleteIntermediary() {
    if (intermediariesList.empty()) {
        std::cout << "No intermediaries available.\n";
        return;
    }

    std::cout << "\nList of Intermediaries:\n";
    for (size_t i = 0; i < intermediariesList.size(); ++i) {
        std::cout << (i + 1) << ": " << intermediariesList[i]->displayIntermediary() << "\n";
    }
    std::cout << (intermediariesList.size() + 1) << ": Back to Intermediaries Menu\n";

    std::cout << "\nSelect an intermediary to delete:\n";
    std::cin >> selection;

    if (selection > 0 && static_cast<size_t>(selection) <= intermediariesList.size()) {
        Intermediaries* intermediaryToDelete = intermediariesList[selection - 1];
        std::cout << "Are you sure you want to delete this intermediary? (Y/N): " << intermediaryToDelete->displayIntermediary() << "\n";
        std::string confirmation;
        std::cin >> confirmation;

        if (confirmation == "Y" || confirmation == "y") {
            intermediariesList.erase(intermediariesList.begin() + selection - 1);
            std::cout << "Intermediary deleted successfully.\n";
        } else {
            std::cout << "Intermediary deletion cancelled.\n";
        }
    } else {
        std::cerr << "Invalid Selection\n";
    }
}

void CLI::PortfolioMenu() {
    do {
        std::cout << "\nPortfolio Menu:\n";
        std::cout << "1. Record Snapshot\n";
        std::cout << "2. Display Historical Listings\n";
        std::cout << "3. Calculate Annual Return\n";
        std::cout << "4. Back to Main Menu\n";

        std::cin >> selection;

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
                std::cerr << "Invalid Selection\n";
                break;
        }
    } while (true);
}

void CLI::recordSnapshot() {
    std::time_t date = get_date();
    increment_date();

    if (!portfolioList.empty()) {
        Portfolio* portfolio = portfolioList[0];
        portfolio->recordSnapshot(date);
        std::cout << "Snapshot recorded on " << std::ctime(&date);
    } else {
        Portfolio* newPortfolio = new Portfolio(assetsList);
        newPortfolio->recordSnapshot(date);
        portfolioList.push_back(newPortfolio);
        std::cout << "Snapshot recorded and new portfolio created on " << std::ctime(&date);
    }
}

void CLI::displayHistoricalListings() {
    if (portfolioList.empty() || portfolioList[0]->get_historicalSnapshots().empty()) {
        std::cout << "No historical data available.\n";
        return;
    }

    std::cout << "Choose sort order: 1 for Ascending, 2 for Descending\n";
    int sortOrder;
    std::cin >> sortOrder;

    Portfolio* portfolio = portfolioList[0];
    portfolio->displayHistoricalListings(sortOrder);
}

void CLI::loadState() {
    std::vector<std::string> saveFiles;
    for (const auto& entry : std::filesystem::directory_iterator(SAVE_DIRECTORY)) {
        if (entry.is_regular_file()) {
            saveFiles.push_back(entry.path().filename().string());
        }
    }

    if (saveFiles.empty()) {
        std::cout << "No save files available in the directory.\n";
        return;
    }

    std::cout << "\nSelect a save file to load:\n";
    for (size_t i = 0; i < saveFiles.size(); ++i) {
        std::cout << (i + 1) << ". " << saveFiles[i] << "\n";
    }

    std::cout << (saveFiles.size() + 1) << ". Cancel\n";

    int selection;
    std::cin >> selection;

    if (selection > 0 && static_cast<size_t>(selection) <= saveFiles.size()) {
        std::string selectedFile = SAVE_DIRECTORY + saveFiles[selection - 1];
        std::ifstream inFile(selectedFile, std::ios::binary);

        if (!inFile) {
            std::cerr << "Error opening file for loading state. Filename: " << selectedFile << "\n";
            return;
        }

        for (auto& asset : assetsList) delete asset;
        for (auto& intermediary : intermediariesList) delete intermediary;
        for (auto& portfolio : portfolioList) delete portfolio;

        assetsList.clear();
        intermediariesList.clear();
        portfolioList.clear();

        size_t intermediariesSize;
        inFile.read(reinterpret_cast<char*>(&intermediariesSize), sizeof(intermediariesSize));
        for (size_t i = 0; i < intermediariesSize; ++i) {
            IntermediaryType type;
            inFile.read(reinterpret_cast<char*>(&type), sizeof(type));

            Intermediaries* intermediary = nullptr;
            switch (type) {
                case BROKER:
                    intermediary = new Broker();
                    break;
                case BANK:
                    intermediary = new Bank();
                    break;
                case MUTUAL_FUND_MANAGER:
                    intermediary = new MutualFundManager();
                    break;
            }
            if (intermediary) {
                intermediary->deserialize(inFile);
                intermediariesList.push_back(intermediary);
            }
        }

        size_t assetsSize;
        inFile.read(reinterpret_cast<char*>(&assetsSize), sizeof(assetsSize));
        for (size_t i = 0; i < assetsSize; ++i) {
            AssetType type;
            inFile.read(reinterpret_cast<char*>(&type), sizeof(type));

            Assets* asset = nullptr;
            switch (type) {
                case STOCK:
                    asset = new Stock();
                    break;
                case BOND:
                    asset = new Bond();
                    break;
                case MUTUAL_FUND:
                    asset = new MutualFund();
                    break;
            }
            if (asset) {
                asset->deserialize(inFile);
                assetsList.push_back(asset);
            }
        }

        size_t portfolioSize;
        inFile.read(reinterpret_cast<char*>(&portfolioSize), sizeof(portfolioSize));
        for (size_t i = 0; i < portfolioSize; ++i) {
            Portfolio* portfolio = new Portfolio(assetsList);
            portfolio->deserialize(inFile, assetsList);
            portfolioList.push_back(portfolio);
        }

        inFile.close();

        if (!inFile) {
            std::cerr << "Error occurred while loading the state.\n";
        } else {
            std::cout << "State loaded successfully from " << selectedFile << ".\n";
        }
    } else if (selection == static_cast<int>(saveFiles.size() + 1)) {
        std::cout << "Loading state canceled.\n";
    } else {
        std::cerr << "Invalid selection.\n";
    }
}

void CLI::saveState() {
    if (!std::filesystem::exists(SAVE_DIRECTORY)) {
        std::filesystem::create_directories(SAVE_DIRECTORY);
    }

    std::string filename = SAVE_DIRECTORY + "save_state_" + getCurrentTimestamp() + ".dat";
    std::ofstream outFile(filename, std::ios::binary);

    if (!outFile) {
        std::cerr << "Error opening file for saving state. Filename: " << filename << "\n";
        return;
    }

    size_t intermediariesSize = intermediariesList.size();
    outFile.write(reinterpret_cast<const char*>(&intermediariesSize), sizeof(intermediariesSize));
    for (const auto& intermediary : intermediariesList) {
        IntermediaryType type = intermediary->get_intermediary_type();
        outFile.write(reinterpret_cast<const char*>(&type), sizeof(type));
        intermediary->serialize(outFile);
    }

    size_t assetsSize = assetsList.size();
    outFile.write(reinterpret_cast<const char*>(&assetsSize), sizeof(assetsSize));
    for (const auto& asset : assetsList) {
        AssetType type = asset->get_type();
        outFile.write(reinterpret_cast<const char*>(&type), sizeof(type));
        asset->serialize(outFile);
    }

    size_t portfolioSize = portfolioList.size();
    outFile.write(reinterpret_cast<const char*>(&portfolioSize), sizeof(portfolioSize));
    for (const auto& portfolio : portfolioList) {
        portfolio->serialize(outFile);
    }

    outFile.close();

    if (!outFile) {
        std::cerr << "Error occurred while saving the state.\n";
    } else {
        std::cout << "State saved successfully as " << filename << ".\n";
    }
}

void CLI::annualReturn() {
    if (assetsList.empty()) {
        std::cout << "No assets available to calculate returns.\n";
        return;
    }

    double totalAnnualReturn = 0;
    for (const auto& asset : assetsList) {
        totalAnnualReturn += asset->calculateAnnualReturn();
    }

    std::cout << "Total Annual Return: " << std::fixed << std::setprecision(2) << totalAnnualReturn << "\n";
}
