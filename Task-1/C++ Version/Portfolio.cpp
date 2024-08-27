#include "Portfolio.h"
#include "SaveHelper.h"

Portfolio::Portfolio(const std::vector<Assets*>& assetsList) {
    set_assetsList(assetsList);
}

Portfolio::~Portfolio() {
    // No need to delete assets here if the ownership is managed elsewhere
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

void Portfolio::serialize(std::ofstream& ofs) const {
    // Serialize the size of the asset list
    size_t assetsSize = _assetsList.size();
    ofs.write(reinterpret_cast<const char*>(&assetsSize), sizeof(assetsSize));

    // Serialize each asset directly
    for (const auto& asset : _assetsList) {
        AssetType type = asset->get_type();
        ofs.write(reinterpret_cast<const char*>(&type), sizeof(type));
        asset->serialize(ofs);
    }

    // Serialize the size of the historical snapshots map
    size_t snapshotsSize = _historicalSnapshots.size();
    ofs.write(reinterpret_cast<const char*>(&snapshotsSize), sizeof(snapshotsSize));

    // Serialize each snapshot
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
    // Deserialize the size of the asset list
    size_t assetsSize;
    ifs.read(reinterpret_cast<char*>(&assetsSize), sizeof(assetsSize));

    // Deserialize each asset directly
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

    // Deserialize the size of the historical snapshots map
    size_t snapshotsSize;
    ifs.read(reinterpret_cast<char*>(&snapshotsSize), sizeof(snapshotsSize));

    // Deserialize each snapshot
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
