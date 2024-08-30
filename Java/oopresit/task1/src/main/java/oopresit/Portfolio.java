package oopresit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    public void displayHistoricalListings(Date startDate, Date endDate) {
        for (Map.Entry<Date, ArrayList<String>> entry : _historicalSnapshots.entrySet()) {
            Date date = entry.getKey();
            if (!date.before(startDate) && !date.after(endDate)) {
                System.out.println("Snapshot for date: " + date);
                for (String snapshotEntry : entry.getValue()) {
                    System.out.println(snapshotEntry);
                }
            }
        }
    }
}
