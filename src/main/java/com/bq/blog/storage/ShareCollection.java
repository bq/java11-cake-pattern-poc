package com.bq.blog.storage;

import java.util.ArrayList;
import java.util.Collection;

public enum ShareCollection {

	INSTANCE;

	private Collection entitiesStorage;

	ShareCollection(){
		entitiesStorage = new ArrayList();
	}

	public Collection getEntitiesStorage() {
		return entitiesStorage;
	}
}
