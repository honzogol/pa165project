package cz.fi.muni.pa165.rest.controllers;

import cz.fi.muni.pa165.dto.MonsterDTO;
import cz.fi.muni.pa165.dto.WeaponCreateDTO;
import cz.fi.muni.pa165.dto.WeaponDTO;
import cz.fi.muni.pa165.dto.WeaponUpdateDTO;
import cz.fi.muni.pa165.entity.Weapon;
import cz.fi.muni.pa165.enums.WeaponType;
import cz.fi.muni.pa165.facade.WeaponFacade;
import cz.fi.muni.pa165.rest.ApiUris;
import cz.fi.muni.pa165.rest.exceptions.InvalidParameterException;
import cz.fi.muni.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.fi.muni.pa165.rest.exceptions.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * Weapon REST Controller
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_WEAPONS)
public class WeaponController {
    private static final Logger log = LoggerFactory.getLogger(WeaponController.class);

    private final WeaponFacade weaponFacade;

    @Inject
    public WeaponController(WeaponFacade weaponFacade) {
        this.weaponFacade = weaponFacade;
    }


    /**
     * Create a new Weapon by POST method
     * curl -X POST -i -H "Content-Type: application/json" --data
     * '{"name":"test","type":"OTHER","range":100,"magazineCapacity":10}'
     * http://localhost:8080/pa165/rest/weapons/create
     *
     * @param weaponCreateDTO WeaponCreateDTO with required fields for creation
     * @return the created weapon WeaponDTO
     * @throws ResourceAlreadyExistingException when weapon with given name already exists
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO createWeapon(@RequestBody WeaponCreateDTO weaponCreateDTO){

        log.debug("Rest createWeapon ({})" , weaponCreateDTO);

        WeaponDTO weaponDTO = weaponFacade.findByName(weaponCreateDTO.getName());
        if (weaponDTO != null){
            throw new ResourceAlreadyExistingException("Resource is already existing!");
        } else {
            Long id = weaponFacade.createWeapon(weaponCreateDTO);
            return weaponFacade.findById(id);
        }
    }

    /**
     * Delete one weapon by id curl -i -X DELETE
     * http://localhost:8080/pa165/rest/weapons/delete/1
     *
     * @param id identifier of weapon
     * @throws ResourceNotFoundException when weapon with given ID wasn't found
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteWeapon(@PathVariable("id") long id){

        log.debug("Rest deleteWeapon with id: {}" , id );

        try{
            weaponFacade.deleteWeapon(id);
        } catch (Exception ex){
            throw new ResourceNotFoundException("Resource not found");
        }
    }

    /**
     * Finds weapon with the same id and updates its attributes.
     * Not provided values are not changed. Id is required.
     *
     * curl -i -X PUT -H
     * "Content-Type: application/json" --data '{"type": "OTHER",
     * "name": "Name", "range": 100, "magazineCapacity": 10}'
     * http://localhost:8080/pa165/rest/weapons/update/1
     *
     * @param id identified of the weapon to be updated
     * @param weaponUpdateDTO required fields as specified in WeaponUpdateDTO except id
     * @return the updated WeaponDTO
     * @throws InvalidParameterException when the given parameters are invalid
     * @throws ResourceNotFoundException when weapon with given id is not found
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO updateWeapon(@PathVariable("id") long id, @RequestBody WeaponUpdateDTO weaponUpdateDTO){
        log.debug("Rest updateWeapon ({})",weaponUpdateDTO);

        if (weaponUpdateDTO == null){
            throw new InvalidParameterException("Argument is null");
        }
        if (weaponUpdateDTO.getId() == null){
            throw new InvalidParameterException("Value `id` is required!");
        }
        WeaponDTO weaponDTO = weaponFacade.updateWeapon(weaponUpdateDTO);

        if (weaponDTO == null){
            throw new ResourceNotFoundException("Resource not found.");
        }

        return weaponDTO;
    }

    /**
     * Add appropriate MonsterDTO to weapon.
     *
     * curl -i -X POST -H
     * "Content-Type: application/json" --data '{"name":"test","height":155.2,"weight":70.5,"agility":"SLOW"}'
     * http://localhost:8080/pa165/rest/weapons/addAppropriateWeapons/1
     *
     * @param id identified of the weapon to be updated
     * @param monsterDTO monsterDTO
     * @throws InvalidParameterException when the given parameters are invalid
     */
    @RequestMapping(value = "/addAppropriateMonster/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final void addAppropriateMonster(@PathVariable("id") long id, @RequestBody MonsterDTO monsterDTO){
        log.debug("Rest addAppropriateMonster with id ({}) for weapon with id({})", monsterDTO.getId(), id );

        if (monsterDTO == null){
            throw new InvalidParameterException("Argument monsterDTO is null");
        }
        if (monsterDTO.getId() == null){
            throw new InvalidParameterException("Value `id` in monster is required!");
        }
        weaponFacade.addAppropriateMonster(id, monsterDTO.getId());
    }

    /**
     * Remove appropriate MonsterDTO to weapon.
     *
     * curl -i -X POST -H
     * "Content-Type: application/json" --data '{"name":"test","height":155.2,"weight":70.5,"agility":"SLOW"}'
     * http://localhost:8080/pa165/rest/weapons/removeAppropriateWeapons/1
     *
     * @param id identified of the weapon to be updated
     * @param monsterDTO monsterDTO
     * @throws InvalidParameterException when the given parameters are invalid
     */
    @RequestMapping(value = "/removeAppropriateMonster/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final void removeAppropriateMonster(@PathVariable("id") long id, MonsterDTO monsterDTO){
        log.debug("Rest removeAppropriateMonster with id ({}) for weapon with id({})", monsterDTO.getId(), id );

        if (monsterDTO == null){
            throw new InvalidParameterException("Argument monsterDTO is null");
        }
        if (monsterDTO.getId() == null){
            throw new InvalidParameterException("Value `id` in monster is required!");
        }
        weaponFacade.removeAppropriateMonster(id, monsterDTO.getId());
    }

    /**
     * Get list of Weapons curl -i -X GET
     * http://localhost:8080/pa165/rest/weapons
     *
     * @return List<WeaponDTO>
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<WeaponDTO> getAllWeapons(){

        log.debug("Rest get all weapons");

        return weaponFacade.getAll();
    }

    /**
     * Gets all weapons with given type by GET method
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/weapons/filter/type/OTHER
     *
     * @param type WeaponType that will be used for search
     * @return List of WeaponDTOs with given agility
     */
    @RequestMapping(value = "/filter/type/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<WeaponDTO> getAllWeaponsForType(@PathVariable("type") WeaponType type){

        log.debug("Rest get all weapons for type ({})", type);

        if (type == null){
            throw new IllegalArgumentException("Invalid parametres given!");
        }

        return weaponFacade.getAllForType(type);
    }

    /**
     * Get Weapon by identifier id curl -i -X GET
     * http://localhost:8080/pa165/rest/weapons/1
     *
     * @param id identifier of weapon
     * @return WeaponDTO
     * @throws ResourceNotFoundException when weapon is not found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO findWeaponById(@PathVariable("id") long id){

        log.debug("Rest find weapon by Id: {}", id);

        WeaponDTO weapon = weaponFacade.findById(id);
        if (weapon == null){
            throw new ResourceNotFoundException("Resource not found");
        }
        return weapon;
    }

    /**
     * Get Weapon by name id curl -i -X GET
     * http://localhost:8080/pa165/rest/weapons/filter/name/Pistol
     *
     * @param name name of weapon
     * @return WeaponDTO
     * @throws ResourceNotFoundException when weapon is not found
     */
    @RequestMapping(value = "/filter/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO findWeaponByName(@PathVariable("name") String name){

        log.debug("Rest find weapon by name: {}", name);

        WeaponDTO weapon = weaponFacade.findByName(name);
        if (weapon == null){
            throw new ResourceNotFoundException("Resource not found");
        }
        return weapon;
    }

    /**
     * Get most effective weapons
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/weapons/filter/mostEffectiveWeapon
     *
     * @return List of WeaponDTOs that are the most effective
     */
    @RequestMapping(value = "/filter/mostEffectiveWeapon", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<WeaponDTO> getTheMostEffectiveWeapon(){

        log.debug("Rest get the most effective weapon");

        return weaponFacade.getTheMostEffectiveWeapon();
    }
}
