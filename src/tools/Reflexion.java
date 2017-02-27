package tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import serveur.RequeteHttp;
import serveur.Service;
import exception.DarException;

public class Reflexion {

	public static Object invokeMethod(String className, String methodName, Object[] parameters, RequeteHttp requete) throws DarException {
		try {
			Class<?> clazz = Class.forName(className);
			Class<?>[] typeParameters = null;
			Constructor<?> constr = clazz.getConstructor();
			Object o = constr.newInstance();
			Service service = (Service) o;
			service.setRequete(requete);
			
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