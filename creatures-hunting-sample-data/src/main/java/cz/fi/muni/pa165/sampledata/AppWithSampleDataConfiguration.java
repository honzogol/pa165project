package cz.fi.muni.pa165.sampledata;

import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class AppWithSampleDataConfiguration {

	final
	SampleDataLoadingFacade sampleDataLoadingFacade;

	@Inject
	public AppWithSampleDataConfiguration(SampleDataLoadingFacade sampleDataLoadingFacade) {
		this.sampleDataLoadingFacade = sampleDataLoadingFacade;
	}

	@PostConstruct
	public void dataLoading() throws IOException {
		sampleDataLoadingFacade.loadMonsterData();
		sampleDataLoadingFacade.loadWeaponData();
		sampleDataLoadingFacade.loadAreaData();
		sampleDataLoadingFacade.loadUserData();

	}
}
