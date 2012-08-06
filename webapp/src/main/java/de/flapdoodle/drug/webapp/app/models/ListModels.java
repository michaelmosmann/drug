package de.flapdoodle.drug.webapp.app.models;

import java.util.List;

import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.Models;


public class ListModels {

	public static <T> IModel<T> ifOnlyOne(IModel<List<T>> descriptions) {
		return Models.on(descriptions).apply(new Function1<T, List<T>>() {
			@Override
			public T apply(List<T> list) {
				if ((list!=null) && (list.size()==1)) return list.get(0);
				return null;
			}
		});
	}

	public static <T> IModel<List<T>> ifMoreThanOne(IModel<List<T>> descriptions) {
		return Models.on(descriptions).apply(new Function1<List<T>, List<T>>() {
			@Override
			public List<T> apply(List<T> list) {
				if ((list!=null) && (list.size()>1)) return list;
				return null;
			}
		});
	}

}
