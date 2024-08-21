package oatt;

import java.io.Serializable;

abstract class Assets implements Serializable{
    private String _name;
    private float _value;
    private Intermediaries _intermediary;
    private static final long serialVersionUID = 1L;

    void set_name(String name){
        _name = name;
    }
 
    void set_value(float value){
        _value = value;
    }

    String get_name(){
        return _name;
    }

    float get_value(){
        return _value;
    }

    void set_intermediary(Intermediaries intermediary) {
        _intermediary = intermediary;
    }

    Intermediaries get_intermediary() {
        return _intermediary;
    }

    public abstract double calculateAnnualReturn();

    public abstract String displayAsset();
}

class Stock extends Assets {
    private String _ticker;
    private float _quantity, _yield;
    private static final long serialVersionUID = 1L;

    public Stock(String name, float value, String ticker, float quantity, float yield, Broker intermediary){
        set_name(name);
        set_value(value);
        set_ticker(ticker);
        set_quantity(quantity);
        set_yield(yield);
        set_intermediary(intermediary);
    }

    void set_ticker(String ticker){
        _ticker = ticker;
    }

    void set_quantity(float quantity){
        _quantity = quantity;
    }

    void set_yield(float yield){
        _yield = yield;
    }

    String get_ticker(){
        return _ticker;
    }

    float get_quantity(){
        return _quantity;
    }

    float get_yield(){
        return _yield;
    }

    @Override
    public double calculateAnnualReturn(){
        Intermediaries intermediary = get_intermediary();

        if (intermediary instanceof Broker) {
            Broker broker = (Broker) intermediary;
            return 365*(get_yield()+broker.get_commission()) * (get_value()*get_quantity()); // avoiding negative returns
        }
        return -1;
    }

    @Override
    public String displayAsset() {
        return String.format("Stock: %s, Value: %.2f, Ticker: %s, Quantity: %.2f, Dividend Yield: %.2f",
                get_name(), get_value(), get_ticker(), get_quantity(), get_yield());
    }
}

class Bond extends Assets {
    private float _interestRate;
    private int _daysToMaturity;
    private static final long serialVersionUID = 1L;

    public Bond(String name, float value, float interestRate, int daysToMaturity, Bank intermediary){
        set_name(name);
        set_value(value);
        set_interestRate(interestRate);
        set_daysToMaturity(daysToMaturity);
        set_intermediary(intermediary);
    }

    void set_interestRate(float interestRate){
        _interestRate = interestRate;
    }

    void set_daysToMaturity(int daysToMaturity){
        _daysToMaturity = daysToMaturity;
    }

    float get_interestRate(){
        return _interestRate;
    }

    int get_daysToMaturity(){
        return _daysToMaturity;
    }

    @Override
    public double calculateAnnualReturn(){
        Intermediaries intermediary = get_intermediary();

        if (intermediary instanceof Bank) {
            if (get_daysToMaturity() < 365) {
                Bank bank = (Bank) intermediary;
                return get_daysToMaturity() * (get_interestRate() + bank.get_interestRate()) * get_value(); // avoiding negative returns
            }else{
                return 0;
            }
        }
        return -1;
    }

    @Override
    public String displayAsset() {
        return String.format("Bond: %s, Value: %.2f, Interest Rate: %.2f, Days to Maturity: %d",
                get_name(), get_value(), get_interestRate(), get_daysToMaturity());
    }
}

class MutualFund extends Assets {
    private float _expenseRatio;
    
    private static final long serialVersionUID = 1L;

    public MutualFund(String name, float value, float expenseRatio, MutualFundManager intermediary){
        set_name(name);
        set_value(value);
        set_expenseRatio(expenseRatio);
        set_intermediary(intermediary);
    }

    void set_expenseRatio(float expenseRatio){
        _expenseRatio = expenseRatio;
    }

    float get_expenseRatio(){
        return _expenseRatio;
    }

    @Override
    public double calculateAnnualReturn(){
        Intermediaries intermediary = get_intermediary();

        if (intermediary instanceof MutualFundManager) {
            MutualFundManager mutualFundManager = (MutualFundManager) intermediary;
            return 365 * (get_value() * (get_expenseRatio() + mutualFundManager.get_managementFee())); // avoiding negative returns
        }
        return -1;
    }

    @Override
    public String displayAsset() {
        return String.format("Mutual Fund: %s, Value: %.2f, Expense Ratio: %.2f",
                get_name(), get_value(), get_expenseRatio());
    }
}
