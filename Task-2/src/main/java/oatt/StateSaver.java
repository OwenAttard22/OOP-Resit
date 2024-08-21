package oatt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StateSaver {
    public void saveAssets(ArrayList<Assets> assetsList) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Saves/assets.ser"))) {
            oos.writeObject(assetsList);
        }
    }

    public void saveIntermediaries(ArrayList<Intermediaries> intermediariesList) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Saves/intermediaries.ser"))) {
            oos.writeObject(intermediariesList);
        }
    }

    public void saveHistoricalSnapshots(ArrayList<HistoricalSnapshots> historicalSnapshotsList) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Saves/historicalSnapshots.ser"))) {
            oos.writeObject(historicalSnapshotsList);
        }
    }
}
