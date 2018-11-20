package com.bq.blog.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.bq.blog.model.Person;


interface MockPersonDaoComponent extends IPersonDaoComponent {

	default DaoBasicOperations personDao() {
		return new MockPersonDAO();
	}

	class MockPersonDAO implements DaoBasicOperations {
		Logger LOG = Logger.getLogger(MockPersonDAO.class.getName());
		Collection entities = new ArrayList();

		@Override
		public void addEntity(Person entity) {
			LOG.info("Mock AddEntity called.");
			entities.add(entity);
		}

		@Override
		public Stream<Person> filterEntities(Predicate<Person> predicate) {
			LOG.info("Mock filterEntities called.");
			return entities.stream().filter(predicate);
		}

		@Override
		public boolean existEntities(Predicate<Person> predicate) {
			LOG.info("Mock existEntities called.");
			return entities.stream().anyMatch(predicate);
		}

		@Override
		public void removeEntities(Predicate<Person> predicate) {
			LOG.info("Mock removeEntities called.");
			List<Person> tmp = filterEntities(predicate).collect(Collectors.toList());
			entities.removeAll(
					filterEntities(predicate).collect(Collectors.toList())
			);
		}
	}
}
