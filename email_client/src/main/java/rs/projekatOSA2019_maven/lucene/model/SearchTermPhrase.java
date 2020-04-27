package rs.projekatOSA2019_maven.lucene.model;

public class SearchTermPhrase {
	private String term;
	private String field;
	
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public SearchTermPhrase(String term, String field) {
		super();
		this.term = term;
		this.field = field;
	}
	
}
