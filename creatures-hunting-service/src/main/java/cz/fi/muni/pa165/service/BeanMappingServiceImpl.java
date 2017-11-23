package cz.fi.muni.pa165.service;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BeanMappingServiceImpl implements BeanMappingService {

	private Mapper dozer;

	@Inject
	public BeanMappingServiceImpl(Mapper dozer) {
		this.dozer = dozer;
	}

	@Override
	public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
		List<T> mappedCollection = new ArrayList<>();
		for (Object object : objects) {
			mappedCollection.add(dozer.map(object, mapToClass));
		}
		return mappedCollection;
	}

	@Override
	public <T> T mapTo(Object o, Class<T> mapToClass) {
		return dozer.map(o, mapToClass);
	}

	@Override
	public Mapper getMapper() {
		return dozer;
	}
}
