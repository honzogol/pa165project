package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.AreaCreateDTO;
import cz.fi.muni.pa165.dto.AreaDTO;
import cz.fi.muni.pa165.entity.Area;
import cz.fi.muni.pa165.enums.AreaType;
import cz.fi.muni.pa165.facade.AreaFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.AreaService;
import cz.fi.muni.pa165.service.MonsterService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
@Service
@Transactional
public class AreaFacadeImpl implements AreaFacade {

    private final AreaService areaService;

    private final MonsterService monsterService;

    private final BeanMappingService beanMappingService;

    @Inject
    public AreaFacadeImpl(AreaService areaService, MonsterService monsterService, BeanMappingService beanMappingService) {
        this.areaService = areaService;
        this.monsterService = monsterService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public Long createArea(AreaCreateDTO area) {
        Area mappedArea = beanMappingService.mapTo(area, Area.class);
        areaService.createArea(mappedArea);
        return mappedArea.getId();
    }

    @Override
    public void deleteArea(Long id) {
        areaService.deleteArea(areaService.findById(id));
    }

    @Override
    public List<AreaDTO> getAllAreas() {
        return beanMappingService.mapTo(areaService.getAllAreas(), AreaDTO.class);
    }

    @Override
    public List<AreaDTO> getAllForType(AreaType type) {
        return beanMappingService.mapTo(areaService.getAllForType(type), AreaDTO.class);
    }

    @Override
    public AreaDTO findById(Long id) {
        return beanMappingService.mapTo(areaService.findById(id), AreaDTO.class);
    }

    @Override
    public AreaDTO findByName(String name) {
        return beanMappingService.mapTo(areaService.findByName(name), AreaDTO.class);
    }

    @Override
    public void addMonsterToArea(Long areaId, Long monsterId) {
        areaService.addMonsterToArea(areaService.findById(monsterId),
                monsterService.findById(areaId));
    }

    @Override
    public void removeMonsterFromArea(Long areaId, Long monsterId) {
        areaService.removeMonsterFromArea(areaService.findById(monsterId),
                monsterService.findById(areaId));
    }
}
