package serveur;

import java.util.HashMap;
import java.util.Set;

public class SessionHttp {

	private HashMap<String, Object> attributs;
	private long dateCreation;
	private long dateDerniereVisite;
	private String ID;
	
	public SessionHttp() {
	attributs = new HashMap<String, Object>();
	dateCreation = System.currentTimeMillis();
	dateDerniereVisite=dateCreation;
	ID="";
	}
	public Object getAttribut(String nomAttr) {
		return attributs.get(nomAttr);
	}
	public void setAttribut(String nomAttr, Object ValeurAttr) {
		attributs.put(nomAttr, ValeurAttr);
	}
	public boolean existeAttribut(String nomAttr) {
		return attributs.containsKey(nomAttr);
	}
	public void supprimerAttribut(String nomAttr) {
		attributs.remove(nomAttr);
	}
	public Set<String> getNomsAttributs(){
		return attributs.keySet();
	}
	public long getDateCreation() {
		return dateCreation;
	}
	public long getDateDerniereVisite() {
		return dateDerniereVisite;
	}
	
	public void setDateDerniereVisite(long date) {
		dateDerniereVisite=date;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID="+ID);
		for(String nomAttr: attributs.keySet()){
			sb.append(nomAttr +":"+attributs.get(nomAttr)+System.lineSeparator());
		}
		return sb.toString();
	}
}
