#ifndef PORTFOLIO_H
#define PORTFOLIO_H

#include <vector>
#include <string>
#include <map>
#include <iostream>
#include <ctime>
#include <fstream>
#include "Assets.h"

class Portfolio {
private:
    std::vector<Assets*> _assetsList;
    std::map<std::time_t, std::vector<std::string>> _historicalSnapshots;

public:
    Portfolio(const std::vector<Assets*>& assetsList);
    ~Portfolio();

    void set_assetsList(const std::vector<Assets*>& assetsList);
    std::vector<Assets*> get_assetsList() const;

    std::map<std::time_t, std::vector<std::string>> get_historicalSnapshots() const;

    void recordSnapshot(std::time_t date);
    void displayHistoricalListings(std::time_t startDate, std::time_t endDate) const;

    // Serialization
    void serialize(std::ofstream& ofs) const;
    void deserialize(std::ifstream& ifs, const std::vector<Assets*>& allAssets);  // Use existing assets
};

#endif
