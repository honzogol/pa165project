package cz.fi.muni.pa165.sampledata;

import cz.fi.muni.pa165.entity.Monster;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.entity.Weapon;
import cz.fi.muni.pa165.enums.MonsterAgility;
import cz.fi.muni.pa165.enums.WeaponType;
import cz.fi.muni.pa165.service.AreaService;
import cz.fi.muni.pa165.enums.UserRole;
import cz.fi.muni.pa165.service.MonsterService;
import cz.fi.muni.pa165.service.UserService;
import cz.fi.muni.pa165.service.WeaponService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@Service
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

	private final MonsterService monsterService;

	private final WeaponService weaponService;

	private final AreaService areaService;

	private final UserService userService;

	@Inject
	public SampleDataLoadingFacadeImpl(MonsterService monsterService, WeaponService weaponService, AreaService areaService, UserService userService) {
		this.monsterService = monsterService;
		this.weaponService = weaponService;
		this.areaService = areaService;
		this.userService = userService;
	}

	@Override
	public void loadMonsterData() {

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

	@Override
	public void loadWeaponData() {
		Weapon pistol = new Weapon();
		pistol.setName("Pistol");
		pistol.setType(WeaponType.PISTOL);
		pistol.setRange(100);
		pistol.setMagazineCapacity(10);

		Weapon rifle = new Weapon();
		rifle.setName("Rifle");
		rifle.setType(WeaponType.RIFLE);
		rifle.setRange(200);
		rifle.setMagazineCapacity(50);

		Weapon shotgun = new Weapon();
		shotgun.setName("Shotgun");
		shotgun.setType(WeaponType.SHOTGUN);
		shotgun.setRange(150);
		shotgun.setMagazineCapacity(20);

		Weapon other = new Weapon();
		other.setName("Other");
		other.setType(WeaponType.OTHER);
		other.setRange(120);
		other.setMagazineCapacity(25);

		Weapon weapon = new Weapon();
		weapon.setName("Weapon");
		weapon.setType(WeaponType.OTHER);
		weapon.setRange(110);
		weapon.setMagazineCapacity(15);


		Monster test =new Monster();
		test.setAgility(MonsterAgility.FAST);
		test.setName("Test");
		test.setWeight(150.2);
		test.setHeight(100.00);

		pistol.addAppropriateMonster(test);

		monsterService.createMonster(test);
		weaponService.createWeapon(pistol);
		weaponService.createWeapon(rifle);
		weaponService.createWeapon(shotgun);
		weaponService.createWeapon(other);
		weaponService.createWeapon(weapon);

	}

	@Override
	public void loadAreaData() {

	}

	@Override
	public void loadUserData() {
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();

		user1.setFirstName("Alojz");
		user1.setLastName("Hlinka");
		user1.setEmail("regular@regular.cz");
		user1.setRole(UserRole.REGULAR);

		user2.setFirstName("Jozef");
		user2.setLastName("Mak");
		user2.setEmail("jozef@mak.com");
		user2.setRole(UserRole.REGULAR);

		user3.setFirstName("Lucia");
		user3.setLastName("Srnova");
		user3.setEmail("admin@admin.cz");
		user3.setRole(UserRole.ADMIN);

		userService.registerUser(user1, "regular");
		userService.registerUser(user2, "1234");
		userService.registerUser(user3, "admin");
	}
}
