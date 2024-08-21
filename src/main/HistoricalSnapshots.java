package oatt;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class HistoricalSnapshots implements Serializable {
    private final String _assetName;
    private final float _assetValue;
    private final String _intermediaryName;
    private final Map<Date, List<String>> _historicalSnapshots;

    HistoricalSnapshots(String assetName, float assetValue, String intermediaryName, Map<Date, List<String>> historicalSnapshots) {
        _assetName = assetName;
        _assetValue = assetValue;
        _intermediaryName = intermediaryName;
        _historicalSnapshots = new HashMap<>(historicalSnapshots);
    }

    String getAssetName() {
        return _assetName;
    }

    float getAssetValue() {
        return _assetValue;
    }

    String getIntermediaryName() {
        return _intermediaryName;
    }

    Map<Date, List<String>> getHistoricalSnapshots() {
        return Collections.unmodifiableMap(_historicalSnapshots);
    }

    public abstract String displaySnapshot();

    protected abstract List<String> generateSnapshotDetails();

    public HistoricalSnapshots recordSnapshot() {
        Date date = new Date();
        Map<Date, List<String>> newSnapshots = new HashMap<>(_historicalSnapshots);
        newSnapshots.put(date, generateSnapshotDetails());
        return createNewInstance(newSnapshots);
    }

    protected abstract HistoricalSnapshots createNewInstance(Map<Date, List<String>> newSnapshots);
}

class StockSnapshot extends HistoricalSnapshots {
    private final String _ticker;
    private final float _quantity;
    private final float _yield;
    private final float _commission;

    StockSnapshot(String assetName, float assetValue, String ticker, float quantity, float yield, String intermediaryName, float commission, Map<Date, List<String>> historicalSnapshots) {
        super(assetName, assetValue, intermediaryName, historicalSnapshots);
        _ticker = ticker;
        _quantity = quantity;
        _yield = yield;
        _commission = commission;
    }

    String getTicker() {
        return _ticker;
    }

    float getQuantity() {
        return _quantity;
    }

    float getYield() {
        return _yield;
    }

    float getCommission() {
        return _commission;
    }

    @Override
    public String displaySnapshot() {
        return String.format("Stock: %s, Value: %.2f, Ticker: %s, Quantity: %.2f, Yield: %.2f | Intermediary: %s, Commission: %.2f",
                getAssetName(), getAssetValue(), getTicker(), getQuantity(), getYield(), getIntermediaryName(), getCommission());
    }

    @Override
    protected List<String> generateSnapshotDetails() {
        return List.of(
                "Name: " + getAssetName(),
                "Value: " + getAssetValue(),
                "Ticker: " + getTicker(),
                "Quantity: " + getQuantity(),
                "Yield: " + getYield(),
                "Intermediary: " + getIntermediaryName(),
                "Commission: " + getCommission()
        );
    }

    @Override
    protected HistoricalSnapshots createNewInstance(Map<Date, List<String>> newSnapshots) {
        return new StockSnapshot(getAssetName(), getAssetValue(), _ticker, _quantity, _yield, getIntermediaryName(), _commission, newSnapshots);
    }
}

class BondSnapshot extends HistoricalSnapshots {
    private final float _interestRate;
    private final int _daysToMaturity;
    private final float _Intermediary_interestRate;

    BondSnapshot(String assetName, float assetValue, float interestRate, int daysToMaturity, String intermediaryName, float Intermediary_interestRate, Map<Date, List<String>> historicalSnapshots) {
        super(assetName, assetValue, intermediaryName, historicalSnapshots);
        _interestRate = interestRate;
        _daysToMaturity = daysToMaturity;
        _Intermediary_interestRate = Intermediary_interestRate;
    }

    float getInterestRate() {
        return _interestRate;
    }

    int getDaysToMaturity() {
        return _daysToMaturity;
    }

    float getIntermediaryInterestRate() {
        return _Intermediary_interestRate;
    }

    @Override
    public String displaySnapshot() {
        return String.format("Bond: %s, Value: %.2f, Interest Rate: %.2f, Days to Maturity: %d | Intermediary: %s, Interest Rate: %.2f",
                getAssetName(), getAssetValue(), getInterestRate(), getDaysToMaturity(), getIntermediaryName(), getIntermediaryInterestRate());
    }

    @Override
    protected List<String> generateSnapshotDetails() {
        return List.of(
                "Name: " + getAssetName(),
                "Value: " + getAssetValue(),
                "Interest Rate: " + getInterestRate(),
                "Days to Maturity: " + getDaysToMaturity(),
                "Intermediary: " + getIntermediaryName(),
                "Interest Rate: " + getIntermediaryInterestRate()
        );
    }

    @Override
    protected HistoricalSnapshots createNewInstance(Map<Date, List<String>> newSnapshots) {
        return new BondSnapshot(getAssetName(), getAssetValue(), getInterestRate(), getDaysToMaturity(), getIntermediaryName(), getIntermediaryInterestRate(), newSnapshots);
    }
}

class MutualFundSnapshot extends HistoricalSnapshots{
    private final float _expenseRatio;
    private final String _employeeNumber;
    private final float _managementFee;

    MutualFundSnapshot(String assetName, float assetValue, float expenseRatio, String intermediaryName, String employeeNumber, float managementFee, Map<Date, List<String>> historicalSnapshots) {
        super(assetName, assetValue, intermediaryName, historicalSnapshots);
        _expenseRatio = expenseRatio;
        _employeeNumber = employeeNumber;
        _managementFee = managementFee;
    }

    float getExpenseRatio() {
        return _expenseRatio;
    }

    String getEmployeeNumber() {
        return _employeeNumber;
    }

    float getManagementFee() {
        return _managementFee;
    }

    @Override
    public String displaySnapshot() {
        return String.format("Mutual Fund: %s, Value: %.2f, Expense Ratio: %.2f | Intermediary: %s, Employee Number: %s, Management Fee: %.2f",
                getAssetName(), getAssetValue(), getExpenseRatio(), getIntermediaryName(), getEmployeeNumber(), getManagementFee());
    }

    @Override
    protected List<String> generateSnapshotDetails() {
        return List.of(
                "Name: " + getAssetName(),
                "Value: " + getAssetValue(),
                "Expense Ratio: " + getExpenseRatio(),
                "Intermediary: " + getIntermediaryName(),
                "Employee Number: " + getEmployeeNumber(),
                "Management Fee: " + getManagementFee()
        );
    }

    @Override
    protected HistoricalSnapshots createNewInstance(Map<Date, List<String>> newSnapshots) {
        return new MutualFundSnapshot(getAssetName(), getAssetValue(), getExpenseRatio(), getIntermediaryName(), getEmployeeNumber(), getManagementFee(), newSnapshots);
    }
}
