package com.ds.entitytosql.helpers;

public class WhereOperParam {
    public String field; 
    public String operator;
    public String valueParam;
    
    public WhereOperParam() {}
    
    public WhereOperParam(String _field, String _operator, String _valueParam) {
        this.field = _field; 
        this.operator = _operator;
        this.valueParam = _valueParam;
    }
    
    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    /**
     * @return the valueParam
     */
    public String getValueParam() {
        return valueParam;
    }

    /**
     * @param valueParam the valueParam to set
     */
    public void setValueParam(String valueParam) {
        this.valueParam = valueParam;
    }    
}
