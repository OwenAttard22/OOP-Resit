#include "Intermediaries.h"
#include "SaveHelper.h"

void Intermediaries::set_name(const std::string& name) {
    _name = name;
}

std::string Intermediaries::get_name() const {
    return _name;
}

Broker::Broker(const std::string& name, float commission) {
    set_name(name);
    set_commission(commission);
}

void Broker::set_commission(float commission) {
    _commission = commission;
}

float Broker::get_commission() const {
    return _commission;
}

std::string Broker::displayIntermediary() const {
    return "Broker: " + get_name() + ", Commission: " + std::to_string(get_commission());
}

void Broker::serialize(std::ofstream& ofs) const {
    writeString(ofs, _name);
    ofs.write(reinterpret_cast<const char*>(&_commission), sizeof(_commission));
}

void Broker::deserialize(std::ifstream& ifs) {
    readString(ifs, _name);
    ifs.read(reinterpret_cast<char*>(&_commission), sizeof(_commission));
}

Bank::Bank(const std::string& name, float interestRate) {
    set_name(name);
    set_interestRate(interestRate);
}

void Bank::set_interestRate(float interestRate) {
    _interestRate = interestRate;
}

float Bank::get_interestRate() const {
    return _interestRate;
}

std::string Bank::displayIntermediary() const {
    return "Bank: " + get_name() + ", Interest Rate: " + std::to_string(get_interestRate());
}

void Bank::serialize(std::ofstream& ofs) const {
    writeString(ofs, _name);
    ofs.write(reinterpret_cast<const char*>(&_interestRate), sizeof(_interestRate));
}

void Bank::deserialize(std::ifstream& ifs) {
    readString(ifs, _name);
    ifs.read(reinterpret_cast<char*>(&_interestRate), sizeof(_interestRate));
}

MutualFundManager::MutualFundManager(const std::string& name, const std::string& employeeNumber, float managementFee) {
    set_name(name);
    set_employeeNumber(employeeNumber);
    set_managementFee(managementFee);
}

void MutualFundManager::set_employeeNumber(const std::string& employeeNumber) {
    _employeeNumber = employeeNumber;
}

void MutualFundManager::set_managementFee(float managementFee) {
    _managementFee = managementFee;
}

std::string MutualFundManager::get_employeeNumber() const {
    return _employeeNumber;
}

float MutualFundManager::get_managementFee() const {
    return _managementFee;
}

std::string MutualFundManager::displayIntermediary() const {
    return "Mutual Fund Manager: " + get_name() + ", Employee Number: " + get_employeeNumber() + ", Management Fee: " + std::to_string(get_managementFee());
}

void MutualFundManager::serialize(std::ofstream& ofs) const {
    writeString(ofs, _name);
    writeString(ofs, _employeeNumber);
    ofs.write(reinterpret_cast<const char*>(&_managementFee), sizeof(_managementFee));
}

void MutualFundManager::deserialize(std::ifstream& ifs) {
    readString(ifs, _name);
    readString(ifs, _employeeNumber);
    ifs.read(reinterpret_cast<char*>(&_managementFee), sizeof(_managementFee));
}