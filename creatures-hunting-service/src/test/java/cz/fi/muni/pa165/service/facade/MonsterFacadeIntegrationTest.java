package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.MonsterCreateDTO;
import cz.fi.muni.pa165.dto.MonsterDTO;
import cz.fi.muni.pa165.enums.MonsterAgility;
import cz.fi.muni.pa165.facade.MonsterFacade;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class MonsterFacadeIntegrationTest extends AbstractTestNGSpringContextTests {

	@Inject
	private MonsterFacade monsterFacade;

	private MonsterCreateDTO prepareMonsterCreateDTO() {
		MonsterCreateDTO monsterDTO = new MonsterCreateDTO();
		monsterDTO.setName("Zombie");
		monsterDTO.setHeight(54.0);
		monsterDTO.setWeight(625.1);
		monsterDTO.setAgility(MonsterAgility.FAST);
		return monsterDTO;
	}

	private MonsterDTO prepareMonsterDTO() {
		MonsterDTO monsterDTO = new MonsterDTO();
		monsterDTO.setName("Zombie");
		monsterDTO.setHeight(54.0);
		monsterDTO.setWeight(625.1);
		monsterDTO.setAgility(MonsterAgility.FAST);
		return monsterDTO;
	}

	@Test
	public void testCreateMonster() {
		MonsterCreateDTO monsterCreateDTO = prepareMonsterCreateDTO();

		Long newId = monsterFacade.createMonster(monsterCreateDTO);
		MonsterDTO monsterDTO = prepareMonsterDTO();
		monsterDTO.setId(newId);

		List<MonsterDTO> monsters = monsterFacade.getAllMonsters();
		assertThat(monsters).contains(monsterDTO);
	}
}
