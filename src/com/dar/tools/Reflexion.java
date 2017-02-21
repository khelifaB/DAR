package com.dar.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.dar.exception.DarException;
import com.dar.serveur.ReponseHttp;
import com.dar.serveur.RequeteHttp;

public class Reflexion {

	public static Object invokeMethod(String className, String methodName, Object[] parameters) throws DarException {
		try {
			Class<?> clazz = Class.forName(className);
			Class<?>[] typeParameters = null;
			Constructor<?> constr = clazz.getConstructor();
			Object o = constr.newInstance();

			if (parameters != null) {
				typeParameters = new Class[parameters.length];
				for (int i = 0; i < parameters.length; ++i) {
					typeParameters[i] = parameters[i].getClass();
				}
			}

			Method method = clazz.getMethod(methodName, typeParameters);
			return method.invoke(o, parameters);
		} catch (Exception e) {
			throw new DarException(e);
		}
	}

}
