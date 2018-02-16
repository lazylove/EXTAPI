package th.co.entronica.parser;

import cauldron.eqx.log.LogDetailData;

//import th.co.ais.pantry.scf.log.LogDetailData;

public class HttpHeader {
	@LogDetailData
	private String name;
	
	@LogDetailData
	private String value;
	
	public HttpHeader(String name, String value) {
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<Header name=");
		builder.append(name);
		builder.append(" value=");
		builder.append(value);
		builder.append(" />");
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HttpHeader other = (HttpHeader) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
