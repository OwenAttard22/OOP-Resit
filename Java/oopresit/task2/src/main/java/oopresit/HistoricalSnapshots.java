package oopresit;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class HistoricalSnapshots implements Serializable {
    private final String _assetName;
    private final float _assetValue;
    private final String _intermediaryName;
    private final Map<Date, List<String>> _historicalSnapshots;
    private final Date _snapshotDate;

    protected HistoricalSnapshots(String assetName, float assetValue, String intermediaryName, Date snapshotDate) {
        _assetName = assetName;
        _assetValue = assetValue;
        _intermediaryName = intermediaryName;
        _historicalSnapshots = new HashMap<>();
        _snapshotDate = snapshotDate;
    }

    private Map<Date, List<String>> deepCopy(Map<Date, List<String>> original) {
        Map<Date, List<String>> copy = new HashMap<>();
        for (Map.Entry<Date, List<String>> entry : original.entrySet()) {
            copy.put(new Date(entry.getKey().getTime()), List.copyOf(entry.getValue()));
        }
        return copy;
    }

    public Date getSnapshotDate() {
        return new Date(_snapshotDate.getTime());
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
        return deepCopy(_historicalSnapshots);
    }

    public abstract String displaySnapshot();

    protected abstract List<String> generateSnapshotDetails();

    public HistoricalSnapshots recordSnapshot(Date date) {
        _historicalSnapshots.put(date, generateSnapshotDetails());
        return this;
    }
    

    protected abstract HistoricalSnapshots createNewInstance(Map<Date, List<String>> newSnapshots);
}

class StockSnapshot extends HistoricalSnapshots {
    private final String _ticker;
    private final float _quantity;
    private final float _yield;
    private final float _commission;

    private StockSnapshot(Builder builder) {
        super(builder.assetName, builder.assetValue, builder.intermediaryName, builder.snapshotDate);
        _ticker = builder.ticker;
        _quantity = builder.quantity;
        _yield = builder.yield;
        _commission = builder.commission;
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
        return new Builder()
                .setassetName(getAssetName())
                .setassetValue(getAssetValue())
                .setticker(_ticker)
                .setquantity(_quantity)
                .setyield(_yield)
                .setintermediaryName(getIntermediaryName())
                .setcommission(_commission)
                .sethistoricalSnapshots(newSnapshots)
                .build();
    }

    public static class Builder {
        private String assetName;
        private float assetValue;
        private String ticker;
        private float quantity;
        private float yield;
        private String intermediaryName;
        private float commission;
        private Map<Date, List<String>> historicalSnapshots = new HashMap<>();
        private Date snapshotDate;

        public Builder setassetName(String assetName) {
            this.assetName = assetName;
            return this;
        }

        public Builder setassetValue(float assetValue) {
            this.assetValue = assetValue;
            return this;
        }

        public Builder setticker(String ticker) {
            this.ticker = ticker;
            return this;
        }

        public Builder setquantity(float quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setyield(float yield) {
            this.yield = yield;
            return this;
        }

        public Builder setintermediaryName(String intermediaryName) {
            this.intermediaryName = intermediaryName;
            return this;
        }

        public Builder setcommission(float commission) {
            this.commission = commission;
            return this;
        }

        public Builder sethistoricalSnapshots(Map<Date, List<String>> historicalSnapshots) {
            this.historicalSnapshots = historicalSnapshots;
            return this;
        }

        public Builder setsnapshotDate(Date snapshotDate) {
            this.snapshotDate = snapshotDate;
            return this;
        }

        public StockSnapshot build() {
            return new StockSnapshot(this);
        }
    }
}

class BondSnapshot extends HistoricalSnapshots {
    private final float _interestRate;
    private final int _daysToMaturity;
    private final float _Intermediary_interestRate;

    private BondSnapshot(Builder builder) {
        super(builder.assetName, builder.assetValue, builder.intermediaryName, builder.snapshotDate);
        _interestRate = builder.interestRate;
        _daysToMaturity = builder.daysToMaturity;
        _Intermediary_interestRate = builder.Intermediary_interestRate;
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
        return new Builder()
                .setassetName(getAssetName())
                .setassetValue(getAssetValue())
                .setinterestRate(_interestRate)
                .setdaysToMaturity(_daysToMaturity)
                .setintermediaryName(getIntermediaryName())
                .setintermediaryInterestRate(_Intermediary_interestRate)
                .sethistoricalSnapshots(newSnapshots)
                .build();
    }

    public static class Builder {
        private String assetName;
        private float assetValue;
        private float interestRate;
        private int daysToMaturity;
        private String intermediaryName;
        private float Intermediary_interestRate;
        private Map<Date, List<String>> historicalSnapshots = new HashMap<>();
        private Date snapshotDate;

        public Builder setassetName(String assetName) {
            this.assetName = assetName;
            return this;
        }

        public Builder setassetValue(float assetValue) {
            this.assetValue = assetValue;
            return this;
        }

        public Builder setinterestRate(float interestRate) {
            this.interestRate = interestRate;
            return this;
        }

        public Builder setdaysToMaturity(int daysToMaturity) {
            this.daysToMaturity = daysToMaturity;
            return this;
        }

        public Builder setintermediaryName(String intermediaryName) {
            this.intermediaryName = intermediaryName;
            return this;
        }

        public Builder setintermediaryInterestRate(float Intermediary_interestRate) {
            this.Intermediary_interestRate = Intermediary_interestRate;
            return this;
        }

        public Builder sethistoricalSnapshots(Map<Date, List<String>> historicalSnapshots) {
            this.historicalSnapshots = historicalSnapshots;
            return this;
        }

        public Builder setsnapshotDate(Date snapshotDate) {
            this.snapshotDate = snapshotDate;
            return this;
        }

        public BondSnapshot build() {
            return new BondSnapshot(this);
        }
    }
}

class MutualFundSnapshot extends HistoricalSnapshots {
    private final float _expenseRatio;
    private final String _employeeNumber;
    private final float _managementFee;

    private MutualFundSnapshot(Builder builder) {
        super(builder.assetName, builder.assetValue, builder.intermediaryName, builder.snapshotDate);
        _expenseRatio = builder.expenseRatio;
        _employeeNumber = builder.employeeNumber;
        _managementFee = builder.managementFee;
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
        return new Builder()
                .setassetName(getAssetName())
                .setassetValue(getAssetValue())
                .setexpenseRatio(_expenseRatio)
                .setintermediaryName(getIntermediaryName())
                .setemployeeNumber(_employeeNumber)
                .setmanagementFee(_managementFee)
                .sethistoricalSnapshots(newSnapshots)
                .build();
    }

    public static class Builder {
        private String assetName;
        private float assetValue;
        private float expenseRatio;
        private String employeeNumber;
        private float managementFee;
        private String intermediaryName;
        private Map<Date, List<String>> historicalSnapshots = new HashMap<>();
        private Date snapshotDate;

        public Builder setassetName(String assetName) {
            this.assetName = assetName;
            return this;
        }

        public Builder setassetValue(float assetValue) {
            this.assetValue = assetValue;
            return this;
        }

        public Builder setexpenseRatio(float expenseRatio) {
            this.expenseRatio = expenseRatio;
            return this;
        }

        public Builder setemployeeNumber(String employeeNumber) {
            this.employeeNumber = employeeNumber;
            return this;
        }

        public Builder setmanagementFee(float managementFee) {
            this.managementFee = managementFee;
            return this;
        }

        public Builder setintermediaryName(String intermediaryName) {
            this.intermediaryName = intermediaryName;
            return this;
        }

        public Builder sethistoricalSnapshots(Map<Date, List<String>> historicalSnapshots) {
            this.historicalSnapshots = historicalSnapshots;
            return this;
        }

        public Builder setsnapshotDate(Date snapshotDate) {
            this.snapshotDate = snapshotDate;
            return this;
        }

        public MutualFundSnapshot build() {
            return new MutualFundSnapshot(this);
        }
    }
}