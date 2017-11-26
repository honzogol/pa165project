package cz.fi.muni.pa165.service.facade;


import cz.fi.muni.pa165.dto.AreaDTO;
import cz.fi.muni.pa165.entity.Area;
import cz.fi.muni.pa165.entity.Monster;
import cz.fi.muni.pa165.enums.AreaType;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.AreaService;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */


@ContextConfiguration(classes = ServiceConfiguration.class)
public class AreaFacadeUnitTest {

}
