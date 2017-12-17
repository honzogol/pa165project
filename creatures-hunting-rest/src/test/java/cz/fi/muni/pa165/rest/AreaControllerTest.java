package cz.fi.muni.pa165.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.fi.muni.pa165.dto.MonsterDTO;
import cz.fi.muni.pa165.dto.AreaCreateDTO;
import cz.fi.muni.pa165.dto.AreaDTO;
import cz.fi.muni.pa165.dto.AreaUpdateDTO;
import cz.fi.muni.pa165.enums.AreaType;
import cz.fi.muni.pa165.facade.AreaFacade;
import cz.fi.muni.pa165.facade.MonsterFacade;
import cz.fi.muni.pa165.rest.controllers.GlobalExceptionController;
import cz.fi.muni.pa165.rest.controllers.AreaController;
import cz.fi.muni.pa165.rest.security.RoleResolver;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
public class AreaControllerTest {

    private AreaFacade areaFacade = mock(AreaFacade.class);
    private MonsterFacade monsterFacade = mock(MonsterFacade.class);
    private RoleResolver roleResolver = mock(RoleResolver.class);

    @InjectMocks
    private AreaController areaController = new AreaController(areaFacade, monsterFacade, roleResolver);

    private MockMvc mockMvc;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(areaController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setHandlerExceptionResolvers(createExceptionResolver())
                .build();
    }

    @BeforeMethod
    public void setUpResolver() {
        when(roleResolver.isSelf(any(), any())).thenReturn(true);
        when(roleResolver.hasRole(any(), any())).thenReturn(true);
    }

    private List<AreaDTO> createAreas() {
        AreaDTO district = new AreaDTO();
        district.setName("District");
        district.setId(1L);
        district.setType(AreaType.DISTRICT);

        AreaDTO mountains = new AreaDTO();
        mountains.setName("Mountains");
        mountains.setId(2L);
        mountains.setType(AreaType.OTHER);

        AreaDTO desert = new AreaDTO();
        desert.setName("Desert");
        desert.setId(3L);
        desert.setType(AreaType.OTHER);

        return Arrays.asList(district, mountains, desert);
    }

    private static String convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(GlobalExceptionController.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new GlobalExceptionController(), method);
            }
        };
        exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

    @Test
    public void debugTest() throws Exception {
        doReturn(Collections.unmodifiableList(this.createAreas())).when(
                areaFacade).getAllAreas();
        mockMvc.perform(get("/auth/areas")).andDo(print());
    }

    @Test
    public void testCreateArea() throws Exception {
        AreaCreateDTO areaCreateDTO = new AreaCreateDTO();
        areaCreateDTO.setName("Test Area");
        doReturn(10L).when(areaFacade).createArea(areaCreateDTO);

        String json = convertObjectToJsonBytes(areaCreateDTO);

        mockMvc.perform(post("/auth/areas/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteExistingArea() throws Exception {
        mockMvc.perform(delete("/auth/areas/1")).andExpect(status().isOk());

        verify(areaFacade, times(1)).deleteArea(1L);
    }

    @Test
    public void testDeleteNonExistingArea() throws Exception {
        doThrow(new RuntimeException("The product does not exist")).when(areaFacade).deleteArea(1L);

        mockMvc.perform(delete("/auth/areas/delete/1")).andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateArea() throws Exception {
        AreaUpdateDTO areaUpdateDTO = new AreaUpdateDTO();
        areaUpdateDTO.setId(1L);
        areaUpdateDTO.setType(AreaType.OTHER);
        areaUpdateDTO.setName("Desert");

        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setId(1L);
        areaDTO.setType(areaUpdateDTO.getType());
        areaDTO.setName(areaUpdateDTO.getName());

        when(areaFacade.updateArea(areaUpdateDTO)).thenReturn(areaDTO);

        String json = convertObjectToJsonBytes(areaUpdateDTO);

        mockMvc.perform(put("/auth/areas/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Desert"))
                .andExpect(jsonPath("$.[?(@.id==1)].type").value("OTHER"));
    }

    @Test
    public void testGetAllAreas() throws Exception {
        doReturn(Collections.unmodifiableList(this.createAreas())).when(areaFacade).getAllAreas();
        mockMvc.perform(get("/auth/areas"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("District"))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("Mountains"))
                .andExpect(jsonPath("$.[?(@.id==3)].name").value("Desert"));
    }

    @Test
    public void testGetAllAreasForType() throws Exception {
        List<AreaDTO> areas = this.createAreas();
        when(areaFacade.getAllForType(AreaType.OTHER)).thenReturn(Arrays.asList(areas.get(1), areas.get(2)));

        mockMvc.perform(get("/auth/areas/filter/type/OTHER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("Mountains"))
                .andExpect(jsonPath("$.[?(@.id==3)].name").value("Desert"));
    }

    @Test
    public void findAreaById() throws Exception {
        List<AreaDTO> areas = this.createAreas();
        when(areaFacade.findById(1L)).thenReturn(areas.get(0));

        mockMvc.perform(get("/auth/areas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("District"));
    }

    @Test
    public void testFindAreaByName() throws Exception {
        List<AreaDTO> areas = this.createAreas();
        when(areaFacade.findByName("District")).thenReturn(areas.get(0));

        mockMvc.perform(get("/auth/areas/filter/name/District"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("District"));
    }

    @Test
    public void testGetTheMostDangerousAreas() throws Exception {
        List<AreaDTO> areas = this.createAreas();

        when(areaFacade.getTheMostDangerousAreas()).thenReturn(areas);

        mockMvc.perform(get("/auth/areas/filter/mostDangerous"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("District"))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("Mountains"))
                .andExpect(jsonPath("$.[?(@.id==3)].name").value("Desert"));
    }

    @Test

    public void testAddMonsterToArea() throws Exception {

        MonsterDTO monsterDTO = new MonsterDTO();
        monsterDTO.setId(1L);
        monsterDTO.setName("Petr Adamek");

        String json = convertObjectToJsonBytes(monsterDTO);

        when(monsterFacade.findById(1L)).thenReturn(monsterDTO);

        mockMvc.perform(post("/auth/areas/1/addMonsterToArea?id=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk());

        verify(areaFacade, times(1)).addMonsterToArea(1L, monsterDTO.getId());

    }

    @Test

    public void testRemoveMonsterFromArea() throws Exception {

        MonsterDTO monsterDTO = new MonsterDTO();
        monsterDTO.setId(1L);
        monsterDTO.setName("Tomáš Pitner");

        String json = convertObjectToJsonBytes(monsterDTO);

        when(monsterFacade.findById(1L)).thenReturn(monsterDTO);

        areaController.addMonsterToArea(1L, monsterDTO.getId());

        mockMvc.perform(post("/auth/areas/1/removeMonsterFromArea?id=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk());

        verify(areaFacade, times(1)).removeMonsterFromArea(1L, monsterDTO.getId());

    }
}
