#include "Assets.h"
#include "SaveHelper.h"

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

void Stock::serialize(std::ofstream& ofs) const {
    writeString(ofs, _name);
    ofs.write(reinterpret_cast<const char*>(&_value), sizeof(_value));

    IntermediaryType type = _intermediary->get_intermediary_type();
    ofs.write(reinterpret_cast<const char*>(&type), sizeof(type));

    _intermediary->serialize(ofs);

    writeString(ofs, _ticker);
    ofs.write(reinterpret_cast<const char*>(&_quantity), sizeof(_quantity));
    ofs.write(reinterpret_cast<const char*>(&_yield), sizeof(_yield));
}

void Stock::deserialize(std::ifstream& ifs) {
    readString(ifs, _name);
    ifs.read(reinterpret_cast<char*>(&_value), sizeof(_value));

    IntermediaryType type;
    ifs.read(reinterpret_cast<char*>(&type), sizeof(type));

    switch (type) {
        case BROKER:
            _intermediary = new Broker();
            break;
        case BANK:
            _intermediary = new Bank();
            break;
        case MUTUAL_FUND_MANAGER:
            _intermediary = new MutualFundManager();
            break;
        default:
            _intermediary = nullptr;
            break;
    }
    if (_intermediary) {
        _intermediary->deserialize(ifs);
    }

    readString(ifs, _ticker);
    ifs.read(reinterpret_cast<char*>(&_quantity), sizeof(_quantity));
    ifs.read(reinterpret_cast<char*>(&_yield), sizeof(_yield));
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

void Bond::serialize(std::ofstream& ofs) const {
    writeString(ofs, _name);
    ofs.write(reinterpret_cast<const char*>(&_value), sizeof(_value));

    IntermediaryType type = _intermediary->get_intermediary_type();
    ofs.write(reinterpret_cast<const char*>(&type), sizeof(type));

    _intermediary->serialize(ofs);

    ofs.write(reinterpret_cast<const char*>(&_interestRate), sizeof(_interestRate));
    ofs.write(reinterpret_cast<const char*>(&_daysToMaturity), sizeof(_daysToMaturity));
}

void Bond::deserialize(std::ifstream& ifs) {
    readString(ifs, _name);
    ifs.read(reinterpret_cast<char*>(&_value), sizeof(_value));

    IntermediaryType type;
    ifs.read(reinterpret_cast<char*>(&type), sizeof(type));

    switch (type) {
        case BROKER:
            _intermediary = new Broker();
            break;
        case BANK:
            _intermediary = new Bank();
            break;
        case MUTUAL_FUND_MANAGER:
            _intermediary = new MutualFundManager();
            break;
        default:
            _intermediary = nullptr;
            break;
    }
    if (_intermediary) {
        _intermediary->deserialize(ifs);
    }

    ifs.read(reinterpret_cast<char*>(&_interestRate), sizeof(_interestRate));
    ifs.read(reinterpret_cast<char*>(&_daysToMaturity), sizeof(_daysToMaturity));
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

void MutualFund::serialize(std::ofstream& ofs) const {
    writeString(ofs, _name);
    ofs.write(reinterpret_cast<const char*>(&_value), sizeof(_value));

    IntermediaryType type = _intermediary->get_intermediary_type();
    ofs.write(reinterpret_cast<const char*>(&type), sizeof(type));

    _intermediary->serialize(ofs);

    ofs.write(reinterpret_cast<const char*>(&_expenseRatio), sizeof(_expenseRatio));
}

void MutualFund::deserialize(std::ifstream& ifs) {
    readString(ifs, _name);
    ifs.read(reinterpret_cast<char*>(&_value), sizeof(_value));

    IntermediaryType type;
    ifs.read(reinterpret_cast<char*>(&type), sizeof(type));

    switch (type) {
        case BROKER:
            _intermediary = new Broker();
            break;
        case BANK:
            _intermediary = new Bank();
            break;
        case MUTUAL_FUND_MANAGER:
            _intermediary = new MutualFundManager();
            break;
        default:
            _intermediary = nullptr;
            break;
    }
    if (_intermediary) {
        _intermediary->deserialize(ifs);
    }

    ifs.read(reinterpret_cast<char*>(&_expenseRatio), sizeof(_expenseRatio));
}