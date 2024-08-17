package oatt;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class StateLoader {
    public ArrayList<Assets> loadAssets() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Saves/assets.ser"))) {
            return (ArrayList<Assets>) ois.readObject();
        }
    }

    public ArrayList<Intermediaries> loadIntermediaries() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Saves/intermediaries.ser"))) {
            return (ArrayList<Intermediaries>) ois.readObject();
        }
    }

    public ArrayList<Portfolio> loadHistoricalSnapshots() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Saves/historicalSnapshots.ser"))) {
            return (ArrayList<Portfolio>) ois.readObject();
        }
    }
}