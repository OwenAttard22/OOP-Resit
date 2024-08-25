#include "Assets.h"

void Assets::set_name(const std::string& name) {
    _name = name;
}

void Assets::set_value(float value) {
    _value = value;
}

void Assets::set_intermediary(Intermediaries* intermediary) {
    _intermediary = intermediary;
}

std::string Assets::get_name() const {
    return _name;
}

float Assets::get_value() const {
    return _value;
}

Intermediaries* Assets::get_intermediary() const {
    return _intermediary;
}

Stock::Stock(const std::string& name, float value, const std::string& ticker, float quantity, float yield, Broker* intermediary) {
    set_name(name);
    set_value(value);
    set_ticker(ticker);
    set_quantity(quantity);
    set_yield(yield);
    set_intermediary(intermediary);
}

void Stock::set_ticker(const std::string& ticker) {
    _ticker = ticker;
}

void Stock::set_quantity(float quantity) {
    _quantity = quantity;
}

void Stock::set_yield(float yield) {
    _yield = yield;
}

std::string Stock::get_ticker() const {
    return _ticker;
}

float Stock::get_quantity() const {
    return _quantity;
}

float Stock::get_yield() const {
    return _yield;
}

double Stock::calculateAnnualReturn() const {
    Intermediaries* intermediary = get_intermediary();

    if (Broker* broker = dynamic_cast<Broker*>(intermediary)) {
        return 365 * (get_yield() + broker->get_commission()) * (get_value() * get_quantity());
    }
    return -1;
}

std::string Stock::displayAsset() const {
    return "Stock: " + get_name() + ", Value: " + std::to_string(get_value()) + 
           ", Ticker: " + get_ticker() + ", Quantity: " + std::to_string(get_quantity()) + 
           ", Dividend Yield: " + std::to_string(get_yield());
}

Bond::Bond(const std::string& name, float value, float interestRate, int daysToMaturity, Bank* intermediary) {
    set_name(name);
    set_value(value);
    set_interestRate(interestRate);
    set_daysToMaturity(daysToMaturity);
    set_intermediary(intermediary);
}

void Bond::set_interestRate(float interestRate) {
    _interestRate = interestRate;
}

void Bond::set_daysToMaturity(int daysToMaturity) {
    _daysToMaturity = daysToMaturity;
}

float Bond::get_interestRate() const {
    return _interestRate;
}

int Bond::get_daysToMaturity() const {
    return _daysToMaturity;
}

double Bond::calculateAnnualReturn() const {
    Intermediaries* intermediary = get_intermediary();

    if (Bank* bank = dynamic_cast<Bank*>(intermediary)) {
        if (get_daysToMaturity() < 365) {
            return get_daysToMaturity() * (get_interestRate() + bank->get_interestRate()) * get_value();
        } else {
            return 0;
        }
    }
    return -1;
}

std::string Bond::displayAsset() const {
    return "Bond: " + get_name() + ", Value: " + std::to_string(get_value()) +
           ", Interest Rate: " + std::to_string(get_interestRate()) + 
           ", Days to Maturity: " + std::to_string(get_daysToMaturity());
}

MutualFund::MutualFund(const std::string& name, float value, float expenseRatio, MutualFundManager* intermediary) {
    set_name(name);
    set_value(value);
    set_expenseRatio(expenseRatio);
    set_intermediary(intermediary);
}

void MutualFund::set_expenseRatio(float expenseRatio) {
    _expenseRatio = expenseRatio;
}

float MutualFund::get_expenseRatio() const {
    return _expenseRatio;
}

double MutualFund::calculateAnnualReturn() const {
    Intermediaries* intermediary = get_intermediary();

    if (MutualFundManager* mutualFundManager = dynamic_cast<MutualFundManager*>(intermediary)) {
        return 365 * (get_value() * (get_expenseRatio() + mutualFundManager->get_managementFee()));
    }
    return -1;
}

std::string MutualFund::displayAsset() const {
    return "Mutual Fund: " + get_name() + ", Value: " + std::to_string(get_value()) +
           ", Expense Ratio: " + std::to_string(get_expenseRatio());
}
