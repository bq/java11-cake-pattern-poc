package com.bq.blog.dto;


import io.vertx.core.json.JsonObject;

public class PersonDto {

	private String ID;

	private String name;

	private String lastName;

	private int age;

	public PersonDto(JsonObject personAsJson) {
		this.name = personAsJson.getString("name");
		this.lastName = personAsJson.getString("lastName");
		this.age = personAsJson.getInteger("age");
		this.ID = personAsJson.getString("ID");
	}

	public PersonDto(String ID, String name, String lastName, int age) {
		this.name = name;
		this.lastName = lastName;
		this.age = age;
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

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

	public static PersonDto.PersonBuilder builder() {
		return new PersonDto.PersonBuilder();
	}


	public static final class PersonBuilder {
		private JsonObject personAsJson;

		public PersonDto.PersonBuilder withPersonAsJson(JsonObject personAsJson) {
			this.personAsJson = personAsJson;
			return this;
		}

		public PersonDto build() {
			return new PersonDto(personAsJson);
		}
	}
}
