#include "Portfolio.h"
#include "SaveHelper.h"
#include <algorithm>

Portfolio::Portfolio(const std::vector<Assets*>& assetsList) {
    set_assetsList(assetsList);
}

Portfolio::~Portfolio() {
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

void Portfolio::displayHistoricalListings(int sortOrder) const {
    if (_historicalSnapshots.empty()) {
        std::cout << "No historical data available.\n";
        return;
    }

    std::vector<std::time_t> sortedDates;
    for (const auto& snapshot : _historicalSnapshots) {
        sortedDates.push_back(snapshot.first);
    }

    if (sortOrder == 2) {
        std::sort(sortedDates.rbegin(), sortedDates.rend());
    } else {
        std::sort(sortedDates.begin(), sortedDates.end());
    }

    for (auto date : sortedDates) {
        std::cout << "\nSnapshot on " << std::ctime(&date);
        for (const auto& detail : _historicalSnapshots.at(date)) {
            std::cout << detail << "\n";
        }
    }
}

void Portfolio::serialize(std::ofstream& ofs) const {
    size_t assetsSize = _assetsList.size();
    ofs.write(reinterpret_cast<const char*>(&assetsSize), sizeof(assetsSize));

    for (const auto& asset : _assetsList) {
        AssetType type = asset->get_type();
        ofs.write(reinterpret_cast<const char*>(&type), sizeof(type));
        asset->serialize(ofs);
    }

    size_t snapshotsSize = _historicalSnapshots.size();
    ofs.write(reinterpret_cast<const char*>(&snapshotsSize), sizeof(snapshotsSize));

    for (const auto& snapshot : _historicalSnapshots) {
        std::time_t date = snapshot.first;
        ofs.write(reinterpret_cast<const char*>(&date), sizeof(date));

        size_t snapshotEntriesSize = snapshot.second.size();
        ofs.write(reinterpret_cast<const char*>(&snapshotEntriesSize), sizeof(snapshotEntriesSize));

        for (const auto& entry : snapshot.second) {
            size_t entryLength = entry.size();
            ofs.write(reinterpret_cast<const char*>(&entryLength), sizeof(entryLength));
            ofs.write(entry.c_str(), entryLength);
        }
    }
}

void Portfolio::deserialize(std::ifstream& ifs, const std::vector<Assets*>& allAssets) {
    size_t assetsSize;
    ifs.read(reinterpret_cast<char*>(&assetsSize), sizeof(assetsSize));

    for (size_t i = 0; i < assetsSize; ++i) {
        AssetType type;
        ifs.read(reinterpret_cast<char*>(&type), sizeof(type));

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
            asset->deserialize(ifs);
            _assetsList.push_back(asset);
        }
    }

    size_t snapshotsSize;
    ifs.read(reinterpret_cast<char*>(&snapshotsSize), sizeof(snapshotsSize));

    for (size_t i = 0; i < snapshotsSize; ++i) {
        std::time_t date;
        ifs.read(reinterpret_cast<char*>(&date), sizeof(date));

        size_t snapshotEntriesSize;
        ifs.read(reinterpret_cast<char*>(&snapshotEntriesSize), sizeof(snapshotEntriesSize));

        std::vector<std::string> snapshotEntries(snapshotEntriesSize);
        for (size_t j = 0; j < snapshotEntriesSize; ++j) {
            size_t entryLength;
            ifs.read(reinterpret_cast<char*>(&entryLength), sizeof(entryLength));
            snapshotEntries[j].resize(entryLength);
            ifs.read(&snapshotEntries[j][0], entryLength);
        }

        _historicalSnapshots[date] = snapshotEntries;
    }
}
