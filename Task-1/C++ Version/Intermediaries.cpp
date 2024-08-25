#include "Intermediaries.h"

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

