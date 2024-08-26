#ifndef ASSETS_H
#define ASSETS_H

#include <string>
#include <fstream>
#include "Intermediaries.h"
#include <iostream>

enum AssetType {
    STOCK = 0,
    BOND = 1,
    MUTUAL_FUND = 2
};

class Assets {
protected:
    std::string _name;
    float _value;
    Intermediaries* _intermediary;

public:
    virtual ~Assets() = default;

    void set_name(const std::string& name);
    void set_value(float value);
    void set_intermediary(Intermediaries* intermediary);

    std::string get_name() const;
    float get_value() const;
    Intermediaries* get_intermediary() const;

    virtual double calculateAnnualReturn() const = 0;
    virtual std::string displayAsset() const = 0;

    virtual AssetType get_type() const = 0;

    virtual void serialize(std::ofstream& ofs) const = 0;
    virtual void deserialize(std::ifstream& ifs) = 0;
};

class Stock : public Assets {
private:
    std::string _ticker;
    float _quantity;
    float _yield;

public:
    Stock() = default;
    Stock(const std::string& name, float value, const std::string& ticker, float quantity, float yield, Broker* intermediary);

    void set_ticker(const std::string& ticker);
    void set_quantity(float quantity);
    void set_yield(float yield);

    std::string get_ticker() const;
    float get_quantity() const;
    float get_yield() const;

    double calculateAnnualReturn() const override;
    std::string displayAsset() const override;

    AssetType get_type() const override { return STOCK; }

    void serialize(std::ofstream& ofs) const override;
    void deserialize(std::ifstream& ifs) override;
};

class Bond : public Assets {
private:
    float _interestRate;
    int _daysToMaturity;

public:
    Bond() = default;
    Bond(const std::string& name, float value, float interestRate, int daysToMaturity, Bank* intermediary);

    void set_interestRate(float interestRate);
    void set_daysToMaturity(int daysToMaturity);

    float get_interestRate() const;
    int get_daysToMaturity() const;

    double calculateAnnualReturn() const override;
    std::string displayAsset() const override;

    AssetType get_type() const override { return BOND; }

    void serialize(std::ofstream& ofs) const override;
    void deserialize(std::ifstream& ifs) override;
};

class MutualFund : public Assets {
private:
    float _expenseRatio;

public:
    MutualFund() = default;
    MutualFund(const std::string& name, float value, float expenseRatio, MutualFundManager* intermediary);

    void set_expenseRatio(float expenseRatio);
    float get_expenseRatio() const;

    double calculateAnnualReturn() const override;
    std::string displayAsset() const override;

    AssetType get_type() const override { return MUTUAL_FUND; }

    void serialize(std::ofstream& ofs) const override;
    void deserialize(std::ifstream& ifs) override;
};

#endif
