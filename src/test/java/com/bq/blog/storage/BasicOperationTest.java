package com.bq.blog.storage;

import org.junit.Test;

import com.bq.blog.model.Person;
import com.bq.blog.service.PersonServiceComponent;

public class BasicOperationTest {

    class App implements PersonServiceComponent, InMemoryPersonDaoComponent {};
    class MockApp implements PersonServiceComponent, MockPersonDaoComponent {};

	@Test
	public void addEntitiesTest() {

         var appInstance = new App();
         appInstance.addEntity(new Person("123","tesName", "testLastName", 18));

		var mockAppInstance = new MockApp();
		mockAppInstance.addEntity(new Person("345","tesName", "testLastName", 18));
	}

}
