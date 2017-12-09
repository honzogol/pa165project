package cz.fi.muni.pa165.rest.controllers;

import cz.fi.muni.pa165.dto.MonsterDTO;
import cz.fi.muni.pa165.dto.AreaCreateDTO;
import cz.fi.muni.pa165.dto.AreaDTO;
import cz.fi.muni.pa165.dto.AreaUpdateDTO;
import cz.fi.muni.pa165.enums.AreaType;
import cz.fi.muni.pa165.facade.AreaFacade;
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
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_AREAS)
public class AreaController {

    private final static Logger log = LoggerFactory.getLogger(AreaController.class);

    private final AreaFacade areaFacade;

    @Inject
    public AreaController(AreaFacade areaFacade) {
        this.areaFacade = areaFacade;
    }

    /**
     * Get list of Areas curl -i -X GET http://localhost:8080/pa165/rest/areas
     *
     * @return List<AreaDTO>
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AreaDTO> getAllAreas() {
        log.debug("rest getAllAreas()");

        return areaFacade.getAllAreas();
    }

    /**
     * Create a new Area by POST method curl -X POST -i -H "Content-Type:
     * application/json" --data '{"name":"New Area","type":"MOUNTAINS"}'
     * http://localhost:8080/pa165/rest/areas/create
     *
     * @param area AreaCreateDTO with required fields for creation
     * @return the created area AreaDTO
     * @throws ResourceAlreadyExistingException when area with given name
     * already exists
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AreaDTO createArea(@RequestBody AreaCreateDTO area) {

        log.debug("rest createArea({})", area);

        AreaDTO areaWithSameName = areaFacade.findByName(area.getName());
        if (areaWithSameName != null) {
            throw new ResourceAlreadyExistingException("Resource is already existing.");
        } else {
            Long id = areaFacade.createArea(area);
            return areaFacade.findById(id);
        }
    }

    /**
     * Delete one area by id curl -i -X DELETE
     * http://localhost:8080/pa165/rest/areas/1
     *
     * @param id identifier of area
     * @throws ResourceNotFoundException when area with given ID wasn't found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteArea(@PathVariable("id") long id) {

        log.debug("rest deleteArea({})", id);

        try {
            areaFacade.deleteArea(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Resource not found.");
        }
    }

    /**
     * Finds area with the same id and updates its attributes. Not provided
     * values are not changed. Id is required.
     *
     * curl -i -X PUT -H "Content-Type: application/json" --data '{"type":
     * "MOUNTAINS", "name": "Mountains full of Kyle Moms"}'
     * http://localhost:8080/pa165/rest/areas/1
     *
     * @param id identified of the area to be updated
     * @param areaUpdate required fields as specified in AreaUpdateDTO except id
     * @return the updated AreaDTO
     * @throws InvalidParameterException when the given parameters are invalid
     * @throws ResourceNotFoundException when area with given id is not found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AreaDTO updateArea(@PathVariable("id") long id, @RequestBody AreaUpdateDTO areaUpdate) {

        areaUpdate.setId(id);

        log.debug("rest updateArea({})", areaUpdate);

        if (areaUpdate.getId() == null) {
            throw new InvalidParameterException("Value 'id' is required.");
        }

        AreaDTO updatedArea = areaFacade.updateArea(areaUpdate);

        if (updatedArea == null) {
            throw new ResourceNotFoundException("Resource not found.");
        }

        return updatedArea;
    }

    /**
     * Gets all areas with given type by GET method curl -i -X GET
     * http://localhost:8080/pa165/rest/areas/filter/type/MOUNTAINS
     *
     * @param type type that will be used for search
     * @return List of AreaDTO with given type
     */
    @RequestMapping(value = "/filter/type/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AreaDTO> getAllForType(@PathVariable("type") AreaType type) {

        log.debug("rest getAllForType({})", type);

        if (type == null) {
            throw new InvalidParameterException("Invalid parameters given.");
        }

        return areaFacade.getAllForType(type);
    }

    /**
     *
     * Get Area by identifier id curl -i -X GET
     * http://localhost:8080/pa165/rest/areas/1
     *
     * @param id identifier of area
     * @return AreaDTO
     * @throws ResourceNotFoundException when area is not found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final AreaDTO findById(@PathVariable("id") long id) {

        log.debug("rest findById({})", id);

        AreaDTO area = areaFacade.findById(id);

        if (area == null) {
            throw new ResourceNotFoundException("Resource not found.");
        }

        return area;
    }

    /**
     *
     * Get Area by name id curl -i -X GET
     * http://localhost:8080/pa165/rest/areas/filter/name/district
     *
     * @param name name of area
     * @return AreaDTO
     * @throws ResourceNotFoundException when area is not found
     */
    @RequestMapping(value = "/filter/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final AreaDTO findByName(@PathVariable("name") String name) {

        log.debug("rest findByName({})", name);

        AreaDTO area = areaFacade.findByName(name);

        if (area == null) {
            throw new ResourceNotFoundException("Resource not found.");
        }

        return area;
    }

    /**
     *
     * Get most dangerous areas curl -i -X GET
     * http://localhost:8080/pa165/rest/areas/filter/mostDangerous
     *
     * @return List of AreaDTO which are most dangerous
     */
    @RequestMapping(value = "/filter/mostDangerous", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AreaDTO> getTheMostDangerousAreas() {

        log.debug("rest getTheMostDangerousAreas()");

        return areaFacade.getTheMostDangerousAreas();
    }

    /**
     * Add MonsterDTO to area.
     *
     * curl -i -X POST -H "Content-Type: application/json" --data 
     * '{"name":"Jiri Uhlir","height":155.2,"weight":70.5,"agility":"FAST"}'
     * http://localhost:8080/pa165/rest/areas/addMonsterToArea/1
     *
     * @param id identified of the area to be updated
     * @param monsterDTO MonsterDTO
     * @throws InvalidParameterException when the given parameters are invalid
     */
    @RequestMapping(value = "/addMonsterToArea/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final void addMonsterToArea(@PathVariable("id") long id, @RequestBody MonsterDTO monsterDTO) {
        log.debug("Rest addMonsterToArea with id ({}) to area with id({})", monsterDTO.getId(), id);

        if (monsterDTO == null) {
            throw new InvalidParameterException("Argument monsterDTO is null");
        }
        if (monsterDTO.getId() == null) {
            throw new InvalidParameterException("Value `id` in monster is required!");
        }
        areaFacade.addMonsterToArea(id, monsterDTO.getId());
    }

    /**
     * Remove MonsterDTO from area.
     *
     * curl -i -X POST -H "Content-Type: application/json" --data
     * '{"name":"Martin Kotala","height":150.2,"weight":70.5,"agility":"FAST"}'
     * http://localhost:8080/pa165/rest/areas/removeMonsterFromArea/1
     *
     * @param id identified of the area to be updated
     * @param monsterDTO monsterDTO
     * @throws InvalidParameterException when the given parameters are invalid
     */
    @RequestMapping(value = "/removeMonsterFromArea/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final void removeMonsterFromArea(@PathVariable("id") long id, MonsterDTO monsterDTO) {
        log.debug("Rest removeMonsterFromArea with id ({}) for area with id({})", monsterDTO.getId(), id);

        if (monsterDTO == null) {
            throw new InvalidParameterException("Argument monsterDTO is null");
        }
        if (monsterDTO.getId() == null) {
            throw new InvalidParameterException("Value `id` in monster is required!");
        }
        areaFacade.removeMonsterFromArea(id, monsterDTO.getId());
    }
}
