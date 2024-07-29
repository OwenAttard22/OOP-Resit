package oatt;

abstract class Assets {
    private String _name;
    private float _value;

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

    public double calculateAnnualReturn(){
        System.out.println("Annual Return: 0");
        return 0;
    }

    public String displayAsset(){
        return "";
    }
    
}

class Stock extends Assets{
    private String _ticker;
    private float _quantity, _yield;

    public Stock(String name, float value, String ticker, float quantity, float yield){
        set_name(name);
        set_value(value);
        set_ticker(ticker);
        set_quantity(quantity);
        set_yield(yield);
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
        return get_yield()*get_quantity()*get_value();
    }

    @Override
    public String displayAsset() {
        return String.format("Stock: %s, Value: %.2f, Ticker: %s, Quantity: %.2f, Dividend Yield: %.2f",
                get_name(), get_value(), get_ticker(), get_quantity(), get_yield());
    }
}

class Bond extends Assets{
    private float _interestRate;
    private int _daysToMaturity;

    public Bond(String name, float value, float interestRate, int daysToMaturity){
        set_name(name);
        set_value(value);
        set_interestRate(interestRate);
        set_daysToMaturity(daysToMaturity);
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
        return get_interestRate()*get_value();
    }

    @Override
    public String displayAsset() {
        return String.format("Bond: %s\nValue: %.2f\nInterest Rate: %.2f\nDays to Maturity: %d",
                get_name(), get_value(), get_interestRate(), get_daysToMaturity());
    }
}

class MutualFund extends Assets{
    private float _expenseRatio;

    public MutualFund(String name, float value, float expenseRatio){
        set_name(name);
        set_value(value);
        set_expenseRatio(expenseRatio);
    }

    void set_expenseRatio(float expenseRatio){
        _expenseRatio = expenseRatio;
    }

    float get_expenseRatio(){
        return _expenseRatio;
    }

    @Override
    public double calculateAnnualReturn(){
        return get_value()*get_expenseRatio();
    }

    @Override
    public String displayAsset() {
        return String.format("Mutual Fund: %s\nValue: %.2f\nExpense Ratio: %.2f",
                get_name(), get_value(), get_expenseRatio());
    }
}