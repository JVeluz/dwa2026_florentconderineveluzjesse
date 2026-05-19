package m1.dwa.cv.daos;


import java.util.HashMap;
import java.util.Map;

import m1.dwa.cv.entities.Bonus;

public class BonusDaoMock implements BonusDao {
    private Map<Integer, Bonus> storage = new HashMap<>();

    public BonusDaoMock() {
        storage.put(0,
            new Bonus()
            .setId(0)
            .setName("Pinceau moyen")
            .setDescription("Colorie 3x3 pixels en un click.")
            .setPrice(100)
        );
        storage.put(1,
            new Bonus()
            .setId(1)
            .setName("Grand pinceau")
            .setDescription("Colorie 5x5 pixels en un click.")
            .setPrice(1000)
        );
    }

    @Override
    public Bonus find(int id) {
        return storage.get(id);
    }

    @Override
    public Bonus[] every() {
        return storage.values().toArray(new Bonus[0]);
    }
}
