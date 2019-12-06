package rs.projekatOSA2019_maven.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="rules")
public class Rule implements Serializable {
	
	public Rule() {}
	
	public Rule(Integer id, Condition condition, String value, Operation operation, Folder folder) {
		super();
		this.id = id;
		this.condition = condition;
		this.value = value;
		this.operation = operation;
		this.folder = folder;
	}

	public enum Condition{
		TO,
		FROM,
		CC,
		SUBJECT
	}
	
	public enum Operation{
		MOVE,
		COPY,
		DELETE
	}
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="rule_id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="rule_condition", unique=false, nullable=false)
	private Condition condition;
	
	@Column(name="rule_value", unique=false, nullable=false, length=100)
	private String value;
	
	@Column(name="rule_operation", unique=false, nullable=false)
	private Operation operation;
	
	@ManyToOne
	@JoinColumn(name="folder_id", referencedColumnName="folder_id", nullable=false)
	private Folder folder;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	@Override
	public String toString() {
		return "Rule [id=" + id + ", condition=" + condition + ", value=" + value + ", operation=" + operation
				+ ", folder=" + folder + "]";
	}
	
	
	

}
