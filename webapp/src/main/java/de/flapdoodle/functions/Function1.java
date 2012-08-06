package de.flapdoodle.functions;

import java.io.Serializable;

import com.google.common.base.Function;

public interface Function1<R,T> extends Function<T, R>, Serializable
{
	R apply(T value);
}
