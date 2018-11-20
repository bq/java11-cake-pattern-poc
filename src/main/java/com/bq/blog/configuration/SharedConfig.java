package com.bq.blog.configuration;

import java.util.Optional;

import io.vertx.core.json.JsonObject;

public enum SharedConfig {

	INSTANCE;

	private JsonObject config;

	SharedConfig(){
		config = new JsonObject();
	}

	public void init(JsonObject configInit){
		config = configInit;
	}

	public Optional<JsonObject> getConfig(){
		return Optional.of(config);
	}

	public Optional<String> getProjectID(){
		return Optional.ofNullable(config.getString("projectID"));
	}

	public Optional<String> getKeyFilePath(){
		return Optional.ofNullable(config.getString("keyfilePath"));
	}

	public  Optional<Integer> getPort(){
		return Optional.ofNullable(config.getInteger("port"));
	}


	public Optional<Integer> getVerticlesAmount(){
		return Optional.ofNullable(config.getInteger("verticlesAmount"));
	}
}
