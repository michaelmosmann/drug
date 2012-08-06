package de.flapdoodle.mongoom;

import java.io.Serializable;

import de.flapdoodle.mongoom.types.Reference;

public interface IEntity<K> extends Serializable
{
	Reference<K> getId();
}
