package cz.fi.muni.pa165.sampledata;

import cz.fi.muni.pa165.entity.Monster;
import cz.fi.muni.pa165.enums.MonsterAgility;
import cz.fi.muni.pa165.service.MonsterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

	private final MonsterService monsterService;

	@Inject
	public SampleDataLoadingFacadeImpl(MonsterService monsterService) {
		this.monsterService = monsterService;
	}

	@Override
	public void loadData() {
		Monster zombie = new Monster("Zombie");
		Monster headlessChicken = new Monster("Headless Chicken");
		Monster rat = new Monster("Rat");
		Monster spider = new Monster("Spider");
		Monster mom = new Monster("Mom");

		zombie.setWeight(5.4);
		headlessChicken.setWeight(5.6);
		spider.setWeight(5.3);
		mom.setWeight(1.4);

		zombie.setHeight(5.4);
		headlessChicken.setHeight(5.6);
		rat.setHeight(8.4);
		spider.setHeight(5.3);


		headlessChicken.setAgility(MonsterAgility.FAST);
		rat.setAgility(MonsterAgility.SLOW);
		spider.setAgility(MonsterAgility.FAST);
		mom.setAgility(MonsterAgility.SLOW);

		monsterService.createMonster(zombie);
		monsterService.createMonster(headlessChicken);
		monsterService.createMonster(rat);
		monsterService.createMonster(spider);
		monsterService.createMonster(mom);
	}
}
