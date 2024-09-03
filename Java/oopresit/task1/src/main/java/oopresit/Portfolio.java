package oopresit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio implements Serializable {
    private ArrayList<Assets> _assetsList;
    private Map<Date, ArrayList<String>> _historicalSnapshots;

    public Portfolio(ArrayList<Assets> assetsList) {
        set_assetsList(assetsList);
        _historicalSnapshots = new HashMap<>();
    }

    void set_assetsList(ArrayList<Assets> assetsList){
        _assetsList = assetsList;
    }

    ArrayList<Assets> get_assetsList(){
        return _assetsList;
    }

    Map<Date, ArrayList<String>> get_historicalSnapshots(){
        return _historicalSnapshots;
    }

    public void recordSnapshot(Date date) {
        ArrayList<String> snapshot = new ArrayList<>();
        
        for (Assets asset : _assetsList) {
            Intermediaries intermediary = asset.get_intermediary();
            String snapshotEntry = asset.displayAsset() + " || " + intermediary.displayIntermediary();
            snapshot.add(snapshotEntry);
        }
        
        _historicalSnapshots.put(date, snapshot);
    }

    public void displayHistoricalListings(int sortOrder) {
        if (_historicalSnapshots.isEmpty()) {
            System.out.println("No historical data available.");
            return;
        }

        List<Date> sortedDates = new ArrayList<>(_historicalSnapshots.keySet());

        if (sortOrder == 2) {
            sortedDates.sort(Comparator.reverseOrder());
        } else {
            sortedDates.sort(Comparator.naturalOrder());
        }

        for (Date date : sortedDates) {
            System.out.println("\nSnapshot on " + date);
            List<String> details = _historicalSnapshots.get(date);
            for (String detail : details) {
                System.out.println(detail);
            }
        }
    }
}
