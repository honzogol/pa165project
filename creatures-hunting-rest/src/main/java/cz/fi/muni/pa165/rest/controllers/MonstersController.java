package cz.fi.muni.pa165.rest.controllers;

import cz.fi.muni.pa165.dto.MonsterCreateDTO;
import cz.fi.muni.pa165.dto.MonsterDTO;
import cz.fi.muni.pa165.dto.MonsterUpdateDTO;
import cz.fi.muni.pa165.enums.MonsterAgility;
import cz.fi.muni.pa165.facade.MonsterFacade;
import cz.fi.muni.pa165.rest.ApiUris;
import cz.fi.muni.pa165.rest.exceptions.InvalidParameterException;
import cz.fi.muni.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.fi.muni.pa165.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_MONSTERS)
public class MonstersController {

	private final static Logger log = LoggerFactory.getLogger(MonstersController.class);

	private final MonsterFacade monsterFacade;

	@Inject
	public MonstersController(MonsterFacade monsterFacade) {
		this.monsterFacade = monsterFacade;
	}

	/**
	 * Get list of Monsters curl -i -X GET
	 * http://localhost:8080/pa165/rest/monsters
	 *
	 * @return List<MonsterDTO>
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final List<MonsterDTO> getAllMonsters() {
		log.debug("rest getAllMonsters()");

		return monsterFacade.getAllMonsters();
	}

	/**
	 * Create a new Monster by POST method
	 * curl -X POST -i -H "Content-Type: application/json" --data
	 * '{"name":"test","height":155.2,"weight":70.5,"agility":"SLOW"}'
	 * http://localhost:8080/pa165/rest/monsters/create
	 *
	 * @param monster MonsterCreateDTO with required fields for creation
	 * @return the created monster MonsterDTO
	 * @throws ResourceAlreadyExistingException when monster with given name already exists
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final MonsterDTO createMonster(@RequestBody MonsterCreateDTO monster) {

		log.debug("rest createMonster({})", monster);

		MonsterDTO monsterWithSameName = monsterFacade.findByName(monster.getName());
		if(monsterWithSameName != null) {
			throw new ResourceAlreadyExistingException("Resource is already existing.");
		} else {
			Long id = monsterFacade.createMonster(monster);
			return monsterFacade.findById(id);
		}
	}

	/**
	 * Delete one monster by id curl -i -X DELETE
	 * http://localhost:8080/pa165/rest/monsters/1
	 *
	 * @param id identifier of monster
	 * @throws ResourceNotFoundException when monster with given ID wasn't found
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public final void deleteMonster(@PathVariable("id") long id) {

		log.debug("rest deleteMonster({})", id);

		try {
			monsterFacade.deleteMonster(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Resource not found.");
		}
	}

	/**
	 * Finds monster with the same id and updates its attributes.
	 * Not provided values are not changed. Id is required.
	 *
	 * curl -i -X PUT -H
	 * "Content-Type: application/json" --data '{"agility": "FAST",
	 * "name": "Zidan", "height": 160.2, "weight": 40.2}'
	 * http://localhost:8080/pa165/rest/monsters/1
	 *
	 * @param id identified of the monster to be updated
	 * @param monsterUpdate required fields as specified in MonsterUpdateDTO except id
	 * @return the updated MonsterDTO
	 * @throws InvalidParameterException when the given parameters are invalid
	 * @throws ResourceNotFoundException when monster with given id is not found
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final MonsterDTO updateMonster(@PathVariable("id") long id, @RequestBody MonsterUpdateDTO monsterUpdate) {

		monsterUpdate.setId(id);

		log.debug("rest updateMonster({})", monsterUpdate);

		if (monsterUpdate.getId() == null) {
			throw new InvalidParameterException("Value 'id' is required.");
		}

		MonsterDTO updatedMonster = monsterFacade.updateMonster(monsterUpdate);

		if (updatedMonster == null) {
			throw new ResourceNotFoundException("Resource not found.");
		}

		return updatedMonster;
	}

	/**
	 * Gets all monsters with given agility by GET method
	 * curl -i -X GET
	 * http://localhost:8080/pa165/rest/monsters/filter/agility/SLOW
	 *
	 * @param agility agility that will be used for search
	 * @return List of MonsterDTO with given agility
	 */
	@RequestMapping(value = "/filter/agility/{agility}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final List<MonsterDTO> getAllForAgility(@PathVariable("agility") MonsterAgility agility) {

		log.debug("rest getAllForAgility({})", agility);

		if (agility == null) {
			throw new InvalidParameterException("Invalid parameters given.");
		}

		return monsterFacade.getAllForAgility(agility);
	}

	/**
	 *
	 * Get Monster by identifier id curl -i -X GET
	 * http://localhost:8080/pa165/rest/monsters/1
	 *
	 * @param id identifier of monster
	 * @return MonsterDTO
	 * @throws ResourceNotFoundException when monster is not found
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final MonsterDTO findById(@PathVariable("id") long id) {

		log.debug("rest findById({})", id);

		MonsterDTO monster = monsterFacade.findById(id);

		if (monster == null) {
			throw new ResourceNotFoundException("Resource not found.");
		}

		return monster;
	}

	/**
	 *
	 * Get Monster by name id curl -i -X GET
	 * http://localhost:8080/pa165/rest/monsters/filter/name/zombie
	 *
	 * @param name name of monster
	 * @return MonsterDTO
	 * @throws ResourceNotFoundException when monster is not found
	 */
	@RequestMapping(value = "/filter/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final MonsterDTO findByName(@PathVariable("name") String name) {

		log.debug("rest findByName({})", name);

		MonsterDTO monster = monsterFacade.findByName(name);

		if (monster == null) {
			throw new ResourceNotFoundException("Resource not found.");
		}

		return monster;
	}

	/**
	 *
	 * Get most wideSpread monsters
	 * curl -i -X GET
	 * http://localhost:8080/pa165/rest/monsters/filter/mostWidespread
	 *
	 * @return List of MonsterDTO that are the most widespread
	 */
	@RequestMapping(value = "/filter/mostWidespread", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final List<MonsterDTO> getTheMostWidespreadMonsters() {

		log.debug("rest getTheMostWidespreadMonsters()");

		return monsterFacade.getTheMostWidespreadMonsters();
	}
}
