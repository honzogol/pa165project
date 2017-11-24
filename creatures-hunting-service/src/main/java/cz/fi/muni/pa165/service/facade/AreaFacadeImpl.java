package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.AreaCreateDTO;
import cz.fi.muni.pa165.dto.AreaDTO;
import cz.fi.muni.pa165.entity.Area;
import cz.fi.muni.pa165.enums.AreaType;
import cz.fi.muni.pa165.facade.AreaFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.AreaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
public class AreaFacadeImpl implements AreaFacade{
    
    private final AreaService areaService;

	private final BeanMappingService beanMappingService;

	@Inject
	public AreaFacadeImpl(AreaService areaService, BeanMappingService beanMappingService) {
		this.areaService = areaService;
		this.beanMappingService = beanMappingService;
	}

	@Override
	public List<AreaDTO> getAllAreas() {
		return beanMappingService.mapTo(areaService.getAllAreas(), AreaDTO.class);
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
}
