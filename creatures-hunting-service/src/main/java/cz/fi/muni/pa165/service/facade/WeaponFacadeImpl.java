package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.WeaponCreateDTO;
import cz.fi.muni.pa165.dto.WeaponDTO;
import cz.fi.muni.pa165.dto.WeaponUpdateDTO;
import cz.fi.muni.pa165.entity.Weapon;
import cz.fi.muni.pa165.enums.WeaponType;
import cz.fi.muni.pa165.facade.WeaponFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.MonsterService;
import cz.fi.muni.pa165.service.WeaponService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of WeaponFacade interface
 *
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@Service
@Transactional
public class WeaponFacadeImpl implements WeaponFacade{

    private final WeaponService weaponService;

    private final MonsterService monsterService;

    private final BeanMappingService beanMappingService;

    @Inject
    public WeaponFacadeImpl(WeaponService weaponService, MonsterService monsterService, BeanMappingService beanMappingService){
        this.weaponService = weaponService;
        this.monsterService = monsterService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public Long createWeapon(WeaponCreateDTO weapon) {
        Weapon mappedWeapon = beanMappingService.mapTo(weapon, Weapon.class);
        weaponService.createWeapon(mappedWeapon);
        return mappedWeapon.getId();
    }

    @Override
    public WeaponDTO updateWeapon(WeaponUpdateDTO weaponUpdateDTO) {
        Weapon weapon = weaponService.findById(weaponUpdateDTO.getId());


        if (weapon == null){
            return null;
        }
        weapon.setType(weaponUpdateDTO.getType());
        weapon.setMagazineCapacity(weaponUpdateDTO.getMagazineCapacity());
        weapon.setName(weaponUpdateDTO.getName());
        weapon.setRange(weaponUpdateDTO.getRange());
        return beanMappingService.mapTo(weapon, WeaponDTO.class);
    }

    @Override
    public void deleteWeapon(Long weaponId) {
        weaponService.deleteWeapon(weaponService.findById(weaponId));
    }

    @Override
    public WeaponDTO findById(Long id) {
        return beanMappingService.mapTo(weaponService.findById(id), WeaponDTO.class);
    }

    @Override
    public WeaponDTO findByName(String name) {
        return beanMappingService.mapTo(weaponService.findByName(name), WeaponDTO.class);
    }

    @Override
    public List<WeaponDTO> getAll() {
        return beanMappingService.mapTo(weaponService.getAllWeapons(), WeaponDTO.class);
    }

    @Override
    public List<WeaponDTO> getAllForType(WeaponType type) {
        return beanMappingService.mapTo(weaponService.getAllForType(type), WeaponDTO.class);
    }

    @Override
    public void addAppropriateMonster(Long weaponId, Long monsterId) {
        weaponService.addAppropriateMonster(weaponService.findById(weaponId), monsterService.findById(monsterId));
    }

    @Override
    public void removeAppropriateMonster(Long weaponId, Long monsterId) {
        weaponService.removeAppropriateMonster(weaponService.findById(weaponId), monsterService.findById(monsterId));
    }

    @Override
    public List<WeaponDTO> getTheMostEffectiveWeapon() {
        return beanMappingService.mapTo(weaponService.getTheMostEffectiveWeapon(), WeaponDTO.class);
    }
}
