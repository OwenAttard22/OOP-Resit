#ifndef INTERMEDIARIES_H
#define INTERMEDIARIES_H

#include <string>
#include <fstream>

class Intermediaries {
protected:
    std::string _name;

public:
    virtual ~Intermediaries() = default;

    void set_name(const std::string& name);
    std::string get_name() const;

    virtual std::string displayIntermediary() const = 0;
};

class Broker : public Intermediaries {
private:
    float _commission;

public:
    Broker() = default;
    Broker(const std::string& name, float commission);

    void set_commission(float commission);
    float get_commission() const;

    std::string displayIntermediary() const override;
};

class Bank : public Intermediaries {
private:
    float _interestRate;

public:
    Bank() = default;
    Bank(const std::string& name, float interestRate);

    void set_interestRate(float interestRate);
    float get_interestRate() const;

    std::string displayIntermediary() const override;
};

class MutualFundManager : public Intermediaries {
private:
    std::string _employeeNumber;
    float _managementFee;

public:
    MutualFundManager() = default;
    MutualFundManager(const std::string& name, const std::string& employeeNumber, float managementFee);

    void set_employeeNumber(const std::string& employeeNumber);
    void set_managementFee(float managementFee);

    std::string get_employeeNumber() const;
    float get_managementFee() const;

    std::string displayIntermediary() const override;
};

#endif
