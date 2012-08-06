package de.flapdoodle.functions;

import java.io.Serializable;

public interface Function3<R,T1,T2,T3> extends Serializable
{
	public R apply(T1 value,T2 value2, T3 value3);
}
