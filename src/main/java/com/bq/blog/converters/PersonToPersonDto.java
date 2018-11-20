package com.bq.blog.converters;

import com.bq.blog.dto.PersonDto;
import com.bq.blog.model.Person;

public interface PersonToPersonDto extends Converter<Person, PersonDto> {

	@Override
	default PersonDto apply(Person t){
		return new PersonDto(t.getID(), t.getName(), t.getLastName(), t.getAge());
	}
}
