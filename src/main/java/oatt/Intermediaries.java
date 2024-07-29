package oatt;

abstract class Intermediaries {
    private String _name;

    void set_name(String name){
        _name = name;
    }

    String get_name(){
        return _name;
    }

    public String displayIntermediary(){
        return "";
    }
}

class Broker extends Intermediaries{
    private float _commission;

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

    public String displayIntermediary(){
        return "Broker: " + get_name() + ", Commission: " + get_commission();
    }
}

class Bank extends Intermediaries{
    private float _interestRate;

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

    public String displayIntermediary(){
        return "Bank: " + get_name() + ", Interest Rate: " + get_interestRate();
    }
}

class MututalFundManager extends Intermediaries{
    private String _employeeNumber;
    private float _managementFee;

    public MututalFundManager(String name, String employeeNumber, float managementFee){
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

    public String displayIntermediary(){
        return "Mututal Fund Manager: " + get_name() + ", Employee Number: " + get_employeeNumber() + ", Management Fee: " + get_managementFee();
    }
}