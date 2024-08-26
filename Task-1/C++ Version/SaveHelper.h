#ifndef SAVEHELPER_H
#define SAVEHELPER_H

#include <string>
#include <fstream>

void writeString(std::ofstream& ofs, const std::string& str);
void readString(std::ifstream& ifs, std::string& str);

#endif