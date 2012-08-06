package de.flapdoodle.mongoom;

import java.io.Serializable;


public interface IVersionedEntity<K,V extends Serializable> extends IEntity<K> {
	V getVersion();
}
