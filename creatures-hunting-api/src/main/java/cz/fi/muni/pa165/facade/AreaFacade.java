package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.AreaCreateDTO;
import cz.fi.muni.pa165.dto.AreaDTO;
import cz.fi.muni.pa165.enums.AreaType;
import java.util.List;

/**
 * Facade interface for AreaDTO entities
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
public interface AreaFacade {

    List<AreaDTO> getAllAreas();

    Long createArea(AreaCreateDTO area);

    void deleteArea(Long areaId);

    List<AreaDTO> getAllForType(AreaType type);

    AreaDTO findById(Long id);

    AreaDTO findByName(String name);
    
    void addMonsterToArea(Long areaId, Long monsterId);
}
