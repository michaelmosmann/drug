package de.flapdoodle.functions;

import java.io.Serializable;

public interface Function2<R,T1,T2> extends Serializable
{
	public R apply(T1 value,T2 value2);
}
