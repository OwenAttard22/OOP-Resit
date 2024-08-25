#ifndef CLI_H
#define CLI_H

#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <ctime>
#include <algorithm>
#include <fstream>
#include <iomanip>
#include <sstream>
#include "Assets.h"
#include "Portfolio.h"
#include "Intermediaries.h"
#include <iostream>

class CLI {
private:
    static int selection;
    static std::vector<Assets*> assetsList;
    static std::vector<Intermediaries*> intermediariesList;
    static const std::string SAVE_DIRECTORY;
    static std::vector<Portfolio*> portfolioList;
    static std::time_t _date;

public:
    static std::time_t get_date();
    static void set_date(std::time_t date);
    static void increment_date();

    static void InitMenu();
    static void MainMenu();
    static void AssetsMenu();
    static void IntermediariesMenu();

    static void createAsset();
    static void createStock();
    static void createBond();
    static void createMutualFund();

    static void createIntermediary();
    static void createBroker();
    static void createBank();
    static void createMutualFundManager();

    static void readAssets();
    static void updateAsset();
    static void updateStock(Stock* stock);
    static void updateBond(Bond* bond);
    static void updateMutualFund(MutualFund* mutualFund);

    static void deleteAsset();
    static void readIntermediaries();
    static void updateIntermediary();
    static void updateIntermediaryDetails(Intermediaries* intermediary);
    static void updateBrokerDetails(Broker* broker);
    static void updateBankDetails(Bank* bank);
    static void updateMutualFundManagerDetails(MutualFundManager* manager);

    static void deleteIntermediary();
    static void PortfolioMenu();
    static void recordSnapshot();
    static void displayHistoricalListings();
    static void loadState();
    static void saveState();
    static void annualReturn();
};

#endif
