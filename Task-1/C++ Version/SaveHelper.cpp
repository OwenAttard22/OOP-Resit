#include "SaveHelper.h"

void writeString(std::ofstream& ofs, const std::string& str) {
    size_t length = str.size();
    ofs.write(reinterpret_cast<const char*>(&length), sizeof(length));
    ofs.write(str.data(), length);
}

void readString(std::ifstream& ifs, std::string& str) {
    size_t length;
    ifs.read(reinterpret_cast<char*>(&length), sizeof(length));
    str.resize(length);
    ifs.read(&str[0], length);
}