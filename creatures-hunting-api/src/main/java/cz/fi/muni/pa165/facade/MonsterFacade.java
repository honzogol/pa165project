package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.MonsterChangeAgilityDTO;
import cz.fi.muni.pa165.dto.MonsterCreateDTO;
import cz.fi.muni.pa165.dto.MonsterDTO;
import cz.fi.muni.pa165.enums.MonsterAgility;

import java.util.List;

/**
 * Facade interface for {@link MonsterDTO} entities.
 *
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
public interface MonsterFacade {

	List<MonsterDTO> getAllMonsters();

	Long createMonster(MonsterCreateDTO monster);

	void deleteMonster(Long monsterId);

	void changeMonsterAgility(MonsterChangeAgilityDTO change);

	List<MonsterDTO> getAllForAgility(MonsterAgility agility);

	MonsterDTO findById(Long id);

	MonsterDTO findByName(String name);
}
