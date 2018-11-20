package com.bq.blog.model;

import io.vertx.core.json.JsonObject;


public class Person {

	private Person(JsonObject personAsJson) {
		this.name = personAsJson.getString("name");
		this.lastName = personAsJson.getString("lastName");
		this.age = personAsJson.getInteger("age");
		this.ID = personAsJson.getString("ID");
	}

	public Person(String ID, String name, String lastName, int age) {
		this.name = name;
		this.lastName = lastName;
		this.age = age;
		this.ID = ID;
	}


	private String ID;

	private String name;

	private String lastName;

	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public static PersonBuilder builder() {
		return new PersonBuilder();
	}


	public static final class PersonBuilder {
		private JsonObject personAsJson;

		public PersonBuilder withPersonAsJson(JsonObject personAsJson) {
			this.personAsJson = personAsJson;
			return this;
		}

		public Person build() {
			return new Person(personAsJson);
		}
	}
}
