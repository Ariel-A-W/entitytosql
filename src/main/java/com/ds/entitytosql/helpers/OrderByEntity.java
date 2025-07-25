package com.ds.entitytosql.helpers;

public class OrderByEntity {
    public String field; 
    public String order_type;
    
    public OrderByEntity() {}
    
    public OrderByEntity(String _field, String _order_type) {
        this.field = _field;
        this.order_type = _order_type;
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
     * @return the order_type
     */
    public String getOrder_type() {
        return order_type;
    }

    /**
     * @param order_type the order_type to set
     */
    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }      
}
