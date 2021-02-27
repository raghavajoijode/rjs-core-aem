package org.subra.aem.rjs.core.samples.multicinfig.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.subra.aem.rjs.core.samples.multicinfig.services.MyTestSampleMultiConfigService;

@Component(service = MyTestSampleMultiConfigService.class)
@Designate(ocd = MyTestSampleMultiConfigServiceImpl.Config.class, factory = true)
public class MyTestSampleMultiConfigServiceImpl implements MyTestSampleMultiConfigService {

	String memName;
	String memPlace;
	int memPIN;

	@ObjectClassDefinition(name = "MyTest Multi Config factory Demo")
	public @interface Config {

		@AttributeDefinition(name = "Name", defaultValue = "Raghava")
		String getMemberName();

		@AttributeDefinition(name = "Place", defaultValue = "Hyderabad")
		String getMemberPlace();

		@AttributeDefinition(name = "Pin", defaultValue = "500001")
		int getMemberPIN();
	}

	@Activate
	protected void activate(final Config config) {
		memName = config.getMemberName();
		memPlace = config.getMemberPlace();
		memPIN = config.getMemberPIN();
	}

	@Override
	public String getMemberName() {
		return memName;
	}

	@Override
	public String getMemberPlace() {
		return memPlace;
	}

	@Override
	public int getMemberPIN() {
		return memPIN;
	}

}
