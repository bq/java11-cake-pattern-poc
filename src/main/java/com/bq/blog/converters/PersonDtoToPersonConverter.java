package com.bq.blog.converters;

import com.bq.blog.dto.PersonDto;
import com.bq.blog.model.Person;


public interface PersonDtoToPersonConverter extends Converter<PersonDto, Person> {

	@Override
	default Person apply(PersonDto t) {
		return new Person(t.getID(), t.getName(), t.getLastName(), t.getAge());
	}
}
