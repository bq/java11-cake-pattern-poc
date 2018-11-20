package com.bq.blog.service;

import java.util.function.Predicate;

import com.bq.blog.model.Person;

public class PersonPredicates {

	public static Predicate<Person> byID(String ID) {
		return person -> person.getID().equalsIgnoreCase(ID);
	}

	public static Predicate<Person> alwaysTrue() {
		return product -> true;
	}
}
