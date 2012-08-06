package de.flapdoodle.drug.persistence.config;

import java.util.Set;

import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;

public class PersistentClasses extends AbstractModule {

	@Override
	protected void configure() {
		Set<Class<?>> annotatedClasses = Sets.newLinkedHashSet();

		annotatedClasses.add(Description.class);
		annotatedClasses.add(Transformation.class);

		bind(new TypeLiteral<Set<Class<?>>>() {}).annotatedWith(Names.named("beans")).toInstance(annotatedClasses);

	}
}
