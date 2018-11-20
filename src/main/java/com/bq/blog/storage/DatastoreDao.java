package com.bq.blog.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.bq.blog.configuration.SharedConfig;
import com.bq.blog.model.Person;

import io.vertx.core.json.JsonObject;

import com.spotify.asyncdatastoreclient.DatastoreConfig;
import com.spotify.asyncdatastoreclient.Datastore;
import com.spotify.asyncdatastoreclient.QueryBuilder;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

public interface DatastoreDao extends IPersonDaoComponent {


	default DaoBasicOperations personDao() {
		return PersonDAO.getInstance(SharedConfig.INSTANCE.getConfig());
	}

	class PersonDAO implements DaoBasicOperations {

		private final ClassLoader classLoader = getClass().getClassLoader();
		final Datastore datastore;

		private static PersonDAO INSTANCE = null;
		private static final Byte OBJLOCK = new Byte((byte) 0);

		public static PersonDAO getInstance(Optional<JsonObject> config) {
			if(INSTANCE == null) {
				synchronized(OBJLOCK) {
					if (INSTANCE == null) {
						INSTANCE = new PersonDAO(config);
					}
				}
			}
			return INSTANCE;
		}

		private PersonDAO(Optional<JsonObject> config) {
			try {
			var projectID = config.get().getString("projectID");
			var keyFile = new File(config.get().getString("keyfilePath"));

			var datastoreConfig = DatastoreConfig.builder()
					.requestTimeout(1000)
					.requestRetry(3)
					.project(projectID)
					.credential(GoogleCredential
							.fromStream(new FileInputStream(keyFile))
							.createScoped(DatastoreConfig.SCOPES))
					.build();

			datastore = Datastore.create(datastoreConfig);
			} catch (IOException e) {
				e.printStackTrace();
				throw new MissingResourceException("Error when Datastore client was created", "DatastoreDao", "config");
			}
		}

		@Override
		public void addEntity(Person entity) {
			var insert = QueryBuilder.insert("people", entity.getID())
					.value("name", entity.getName())
					.value("lastName", entity.getLastName())
					.value("age", entity.getAge());

			datastore.executeAsync(insert);
		}

		@Override
		public Stream<Person> filterEntities(Predicate<Person> predicate) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean existEntities(Predicate<Person> predicate) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void removeEntities(Predicate<Person> predicate) {
			throw new UnsupportedOperationException();
		}
	}

}
