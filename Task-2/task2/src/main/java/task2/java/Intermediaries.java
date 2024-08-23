package task2.java;

import java.io.Serializable;

abstract class Intermediaries implements Serializable{
    private String _name;
    private static final long serialVersionUID = 1L;

    void set_name(String name){
        _name = name;   
    }

    String get_name(){
        return _name;
    }

    public abstract String displayIntermediary();
}

class Broker extends Intermediaries {
    private float _commission;
    private static final long serialVersionUID = 1L;

    public Broker(String name, float commission){
        set_name(name);
        set_commission(commission);
    }

    void set_commission(float commission){
        _commission = commission;
    }

    float get_commission(){
        return _commission;
    }

    @Override
    public String displayIntermediary(){
        return "Broker: " + get_name() + ", Commission: " + get_commission();
    }
}

class Bank extends Intermediaries {
    private float _interestRate;
    private static final long serialVersionUID = 1L;

    public Bank(String name, float interestRate){
        set_name(name);
        set_interestRate(interestRate);
    }

    void set_interestRate(float interestRate){
        _interestRate = interestRate;
    }

    float get_interestRate(){
        return _interestRate;
    }

    @Override
    public String displayIntermediary(){
        return "Bank: " + get_name() + ", Interest Rate: " + get_interestRate();
    }
}

class MutualFundManager extends Intermediaries {
    private String _employeeNumber;
    private float _managementFee;
    private static final long serialVersionUID = 1L;

    public MutualFundManager(String name, String employeeNumber, float managementFee){
        set_name(name);
        set_employeeNumber(employeeNumber);
        set_managementFee(managementFee);
    }

    void set_employeeNumber(String employeeNumber){
        _employeeNumber = employeeNumber;
    }

    void set_managementFee(float managementFee){
        _managementFee = managementFee;
    }

    String get_employeeNumber(){
        return _employeeNumber;
    }

    float get_managementFee(){
        return _managementFee;
    }

    @Override
    public String displayIntermediary(){
        return "Mutual Fund Manager: " + get_name() + ", Employee Number: " + get_employeeNumber() + ", Management Fee: " + get_managementFee();
    }
}
