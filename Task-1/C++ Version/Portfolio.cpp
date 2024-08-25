#include "Portfolio.h"
#include "Assets.h"

Portfolio::Portfolio(const std::vector<Assets*>& assetsList) {
    set_assetsList(assetsList);
}

Portfolio::~Portfolio() {
    for (auto asset : _assetsList) {
        delete asset;
    }
}

void Portfolio::set_assetsList(const std::vector<Assets*>& assetsList) {
    _assetsList = assetsList;
}

std::vector<Assets*> Portfolio::get_assetsList() const {
    return _assetsList;
}

std::map<std::time_t, std::vector<std::string>> Portfolio::get_historicalSnapshots() const {
    return _historicalSnapshots;
}

void Portfolio::recordSnapshot(std::time_t date) {
    std::vector<std::string> snapshot;

    for (const auto& asset : _assetsList) {
        Intermediaries* intermediary = asset->get_intermediary();
        std::string snapshotEntry = asset->displayAsset() + " || " + intermediary->displayIntermediary();
        snapshot.push_back(snapshotEntry);
    }

    _historicalSnapshots[date] = snapshot;
}

void Portfolio::displayHistoricalListings(std::time_t startDate, std::time_t endDate) const {
    bool found = false;
    for (const auto& entry : _historicalSnapshots) {
        std::time_t date = entry.first;
        if (date >= startDate && date <= endDate) {
            found = true;
            std::cout << "Snapshot for date: " << std::ctime(&date);
            for (const auto& snapshotEntry : entry.second) {
                std::cout << snapshotEntry << std::endl;
            }
        }
    }
    if (!found) {
        std::cout << "No snapshots found within the specified date range.\n";
    }
}


