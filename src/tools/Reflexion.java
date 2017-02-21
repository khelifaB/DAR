package tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import exception.DarException;

public class Reflexion {
	
	public static Object invokeMethod(String className, String methodName) throws DarException {
		try {
			Class<?> clazz = Class.forName(className);

			Constructor<?> constr = clazz.getConstructor();
			Object o = constr.newInstance();

			Method method = clazz.getMethod(methodName);
			return method.invoke(o);
		} catch (Exception e) {
			throw new DarException(e);
		}
	}
}
