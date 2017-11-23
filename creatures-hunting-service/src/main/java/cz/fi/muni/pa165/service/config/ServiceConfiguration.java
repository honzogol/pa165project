package cz.fi.muni.pa165.service.config;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.dto.MonsterDTO;
import cz.fi.muni.pa165.entity.Monster;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackages = "cz.fi.muni.pa165")
public class ServiceConfiguration {

	@Bean
	public Mapper dozer() {
		DozerBeanMapper dozer = new DozerBeanMapper();
		dozer.addMapping(new DozerCustomConfig());
		return dozer;
	}

	/**
	 * Custom config for Dozer
	 */
	public class DozerCustomConfig extends BeanMappingBuilder {
		@Override
		protected void configure() {
			mapping(Monster.class, MonsterDTO.class);
		}
	}
}
