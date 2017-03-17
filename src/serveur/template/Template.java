package serveur.template;

import java.lang.reflect.Field;
import java.util.Map;

import tools.Fichier;
import exception.DarException;

public class Template {
	public static String transform (String pathfile,Map<String, String> map ){
		String content = Fichier.lectureFichier(pathfile);
		for(Map.Entry<String, String> entry : map.entrySet()){
			content = content.replace("${"+entry.getKey()+"}", entry.getValue());
		}
		return content;
	}

	public static String transform(String pathfile, Object obj) throws DarException{
		String content = Fichier.lectureFichier(pathfile);
		try
        {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields)
            {
                field.setAccessible(true);
            	content = content.replace("${"+field.getName()+"}", field.get(obj).toString());
            }
        }
        catch (Exception e)
        {
            throw new DarException("Erreur de generation Template",e);
        }
		return content;
	}
}
