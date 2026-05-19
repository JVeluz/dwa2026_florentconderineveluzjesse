package m1.dwa.cv.daos;

import java.util.HashMap;
import java.util.Map;

import m1.dwa.cv.entities.SkillState;

public class SkillDaoMock implements SkillDao {
    private int count = 0;
    private Map<Integer, SkillState> storage = new HashMap<>();

	public SkillState find(int id) {
	    return storage.get(id);
	}

	@Override
	public SkillState create(SkillState skill) {
        skill.setId(count);
        storage.put(count, skill);
        count++;
        return skill;
	}

	@Override
	public SkillState delete(SkillState skill) {
	    return storage.remove(skill.getId());
	}

	@Override
	public SkillState update(SkillState skill) {
	    storage.put(skill.getId(), skill);
		return skill;
	}

	@Override
	public SkillState[] every() {
	    return storage.values().toArray(new SkillState[0]);
	}
}
