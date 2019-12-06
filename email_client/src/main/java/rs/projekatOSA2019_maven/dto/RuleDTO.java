package rs.projekatOSA2019_maven.dto;

import java.io.Serializable;

import rs.projekatOSA2019_maven.entity.Rule;
import rs.projekatOSA2019_maven.entity.Rule.Condition;
import rs.projekatOSA2019_maven.entity.Rule.Operation;


public class RuleDTO implements Serializable {
    private int id;
    private Condition condition;
    private Operation operation;


    public RuleDTO(){}

    public RuleDTO(int id, Condition condition, Operation operation) {
        this.id = id;
        this.condition = condition;
        this.operation = operation;
    }
    
    public RuleDTO(Rule rule) {
    	
    	this(rule.getId(), rule.getCondition(), rule.getOperation());
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }


    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", condition=" + condition +
                ", operation=" + operation +
                '}';
    }
}
