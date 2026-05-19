package m1.dwa.cv.game.skills;

import java.util.HashMap;
import java.util.Map;

// Les objets Skill contientent des informations supplémentaires sur les bonus utilisable.
// On veut être très libre sur ce que peux faire nos skills et ne pas complexifier la BD avec des champs spécifique qui serait vide pour beaucoup d'entrées.
// Il nous faut quand même faire le lien entre les informations du backend et de la bd.
// => Map sur l'id des bonus
// C'est le paterne "registery"
// https://www.geeksforgeeks.org/system-design/registry-pattern/#implementation-strategies
// La difference ici est qu'on enregistre nos instances dans le constructeur

public class SkillRegistery {
	private Map<Integer, Skill> skills = new HashMap<>();

	public SkillRegistery() {
		skills.put(3, new PassiveCreditsSkill(-1, 1, 1));
		skills.put(4, new PassiveCreditsSkill(-1, 1, 2));
        skills.put(5, new ClickMultiplierSkill(60, 1, 2 ));
	}

	public Skill get(int bonusId) {
        return skills.get(bonusId);
	}
}
