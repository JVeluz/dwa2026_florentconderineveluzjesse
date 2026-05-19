package m1.dwa.cv.daos;

import m1.dwa.cv.entities.SkillState;

public interface SkillDao {
    public SkillState create(SkillState skill);
    public SkillState delete(SkillState skill);
    public SkillState[] every();
    public SkillState update(SkillState skill);
}
