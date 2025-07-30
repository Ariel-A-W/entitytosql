package com.ds.entitytosql.helpers;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ReflectorEntityToSQL {
    // *************************************************************************
    // *** SELECT **************************************************************
    // *************************************************************************        
    private boolean _distinct;
    
    /**
     * Set DISTINCT clausule in SELECT sentence string SQL. 
     * 
     * Ajusta la cláusula DISTINCT para la cade SQL para la sentencia SELECT
     * 
     * @param value The value true DISTINCT add and false (default) drop DISTINCT. 
     * El valor true añade DISTINCT y false quita DISTINCT.
     */
    public void set_DISTINCT(boolean value) {
        this._distinct = value;
    }
    
    /**
     * Get DISTINCT clausule in SELECT sentence string SQL. 
     * 
     * Obtiene la cláusula DISTINCT para la cade SQL para la sentencia SELECT.
     * 
     * @return Return a string SQL for SELECT DISTINCT or SELECT simple. 
     * Retorna la cadena SQL SELECT DISTINCT o un solo SELECT. 
     */
    public boolean get_DISTINCT() {
        return this._distinct;
    }
    
    /**
     * Create a sentence SELECT for SQL string. 
     * 
     * Crea una cadena SQL con SELECT.
     * 
     * @param schema Schema of databse. Esquema de la base de datos.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT(String schema, Class<?> classProp) {
        try {
            String outSQL = "";
            Field[] fields = classProp.getDeclaredFields();
            
            String fldBrut = "";
            for (Field field : fields) {
                // Fields list name enumerate              
                fldBrut += field.getName() + ", ";
            }
            String fldPure = fldBrut.substring(0, fldBrut.lastIndexOf(",")).trim();
            String distinct = this._distinct == true ? "DISTINCT " : "";
            if (schema != null) {                 
                outSQL = "SELECT " + distinct + fldPure + " FROM " + schema + "." + classProp.getSimpleName();
            } else {
                outSQL = "SELECT " + distinct + fldPure + " FROM " + classProp.getSimpleName();
            }                        
            return outSQL;        
        } catch (Exception ex) {
            return "Error";
        }           
    }
    
    /**
     * Create a SQL string using the WHERE clausule. 
     * 
     * Crea una cadena SQL usando la cláusula WHERE.
     * 
     * @param schema Schema of databse. Esquema de la base de datos.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT_WHERE(String schema, Class<?> classProp, ArrayList<WhereOperParam> operParam) {
        String pairsBrut = "";
        String pairsPure = "";
        for (WhereOperParam et : operParam) {
            if (operParam.size() == 1){
                return get_SELECT(schema, classProp)+ " WHERE " + et.getField() + " " + et.getOperator() + " " + et.getValueParam();
            } else {
                pairsBrut += et.getField() + " " + et.getOperator() + " " + et.getValueParam() + " AND ";
            }
        }
        pairsPure = pairsBrut.substring(0, pairsBrut.lastIndexOf(" AND ")).trim();                    
        return get_SELECT(schema, classProp)+ " WHERE " + pairsPure;        
    }
    
    /**
     * Create a SQL string using the WHERE clausule. 
     * 
     * Crea una cadena SQL usando la cláusula WHERE.
     * 
     * @param schema Schema of databse. Esquema de la base de datos.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param upperLowerNone Format as Upper or Lower case. Formatear como Mayúsculas o minúsculas.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */    
    public String get_SELECT_WHERE(String schema, Class<?> classProp, EUpperLower upperLowerNone, ArrayList<WhereOperParam> operParam) {
        return switch (upperLowerNone) {
            case UPPER -> get_SELECT_WHERE(schema, classProp, operParam).toUpperCase().trim();
            case LOWER -> get_SELECT_WHERE(schema, classProp, operParam).toLowerCase().trim();
            default -> get_SELECT_WHERE(schema, classProp, operParam);
        };                
    }    
    
    /**
     * Create a SQL string using WHERE and ORDER BY clausules. 
     * 
     * Crea una cadeda de SQL usando las clásulas WHERE y ORDER BY. 
     * 
     * @param schema Schema of databse. Esquema de la base de datos.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @param orderByArray Array format clausule for ORDER BY. Formato para la cláusula ORDER BY.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT_WHERE_ORDERBY(String schema, Class<?> classProp, ArrayList<WhereOperParam> operParam, ArrayList<OrderByEntity> orderByArray) {
        String pairsBrut = "";
        String pairsPure = "";
        String pairsBrut2 = "";
        String pairsPure2 = "";        
        for (WhereOperParam et : operParam) {
            if (operParam.size() == 1){                               
                pairsPure = et.getField() + " " + et.getOperator() + " " + et.getValueParam();
                break;
            } else {
                pairsBrut += et.getField() + " " + et.getOperator() + " " + et.getValueParam() + " AND ";
            }
        }
        if (operParam.size() > 1) {
            pairsPure = pairsBrut.substring(0, pairsBrut.lastIndexOf(" AND ")).trim();
        }        
        pairsBrut2 = "ORDER BY "; 
        for (OrderByEntity et2 : orderByArray) {
            if (orderByArray.size() == 1) {
                pairsBrut2 += et2.getField() + " " + et2.getOrder_type();
                break;
            } else {
                pairsBrut2 += et2.getField() + " " + et2.getOrder_type() + ", ";
            }
        }
        if (orderByArray.size() == 1) {
            pairsPure2 = pairsBrut2;
        } else {
            pairsPure2 = pairsBrut2.substring(0, pairsBrut2.lastIndexOf(",")).trim(); 
        }         
        return get_SELECT(schema, classProp) + " WHERE " + pairsPure + " " + pairsPure2;               
    }
    
    /**
     * Create a SQL string using WHERE and ORDER BY clausules in adittion using of Upper or Lower cases. 
     * 
     * Crea una cadeda de SQL usando las clásulas WHERE y ORDER BY además del uso de Mayúsculas y Minúsculas.
     * 
     * @param schema Schema of databse. Esquema de la base de datos.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param upperLowerNone Format as Upper or Lower case. Formatear como Mayúsculas o minúsculas.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @param orderByArray Array format clausule for ORDER BY. Formato para la cláusula ORDER BY.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT_WHERE_ORDERBY(String schema, Class<?> classProp, EUpperLower upperLowerNone, ArrayList<WhereOperParam> operParam, ArrayList<OrderByEntity> orderByArray) {
        return switch (upperLowerNone) {
            case UPPER -> get_SELECT_WHERE_ORDERBY(schema, classProp, operParam, orderByArray).toUpperCase().trim();
            case LOWER -> get_SELECT_WHERE_ORDERBY(schema, classProp, operParam, orderByArray).toLowerCase().trim();
            default -> get_SELECT_WHERE_ORDERBY(schema, classProp, operParam, orderByArray);
        };
    }
    
    /**
     * Create a sentence SELECT for SQL string using the ORDER BY clausule. 
     * 
     * Crea una cadena SQL con SELECT usando la cláusula ORDER BY.  
     * 
     * @param schema Schema of databse. Esquema de la base de datos.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param orderByArray Array format clausule for ORDER BY. Formato para la cláusula ORDER BY.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT_ORDERBY(String schema, Class<?> classProp, ArrayList<OrderByEntity> orderByArray) {        
        String pairsBrut = "";
        String pairsPure = "";
        for (OrderByEntity et : orderByArray) {
            if (orderByArray.size() == 1){
                return get_SELECT(schema, classProp) + " ORDER BY " + et.getField() + " " + et.getOrder_type();
            } else {
                pairsBrut += et.getField() + " " + et.getOrder_type() + ", ";
            }
        }
        pairsPure = pairsBrut.substring(0, pairsBrut.lastIndexOf(",")).trim();                    
        return get_SELECT(schema, classProp)+ " ORDER BY " + pairsPure;
    }
    
    /**
     * Create a sentence SELECT for SQL string using the ORDER BY clausule for SQL string in Upper or Lowear cases. 
     * 
     * Crea una cadena SQL con SELECT usando la cláusula ORDER BY en mayúsculas o minúsculas.
     * 
     * @param schema Schema of databse. Esquema de la base de datos.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param upperLowerNone Format as Upper or Lower case. Formatear como Mayúsculas o minúsculas.
     * @param orderByArray Array format clausule for ORDER BY. Formato para la cláusula ORDER BY.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT_ORDERBY(String schema, Class<?> classProp, EUpperLower upperLowerNone, ArrayList<OrderByEntity> orderByArray) {
        return switch (upperLowerNone) {
            case UPPER -> get_SELECT_ORDERBY(schema, classProp, orderByArray).toUpperCase().trim();
            case LOWER -> get_SELECT_ORDERBY(schema, classProp, orderByArray).toLowerCase().trim();
            default -> get_SELECT_ORDERBY(schema, classProp, orderByArray);
        };    
    }    
    
    /**
     * Create a sentence SELECT for SQL string in Upper or Lowear cases. 
     * 
     * Crea una cadena SQL con SELECT en mayúsculas o minúsculas.
     * 
     * @param schema Schema of databse. Esquema de la base de datos.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param upperLowerNone Format as Upper or Lower case. Formatear como Mayúsculas o minúsculas. 
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT(String schema, Class<?> classProp, EUpperLower upperLowerNone) {
        return switch (upperLowerNone) {
            case UPPER -> get_SELECT(schema, classProp).toUpperCase().trim();
            case LOWER -> get_SELECT(schema, classProp).toLowerCase().trim();
            default -> get_SELECT(schema, classProp);
        };
    }
    // *************************************************************************
    
    
    // *************************************************************************
    // *** INSERT INTO *********************************************************
    // *************************************************************************   
    
    /**
     * Create a SQL string using the INSERT INTO cláusule and allow excludes fields. 
     * 
     * Crea una cadenas SQL usando la cláusula INSERT INTO y permite excluir campos.
     * 
     * @param schema Schema of databse. Esquema de la base de datos.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param excludeFields Excludes fields in query. Excluye campos en la consulta.
     * @param param Mask as param for the query. Máscara como parámetro para la consulta. 
     * @param upperLowerNone Format as Upper or Lower case. Formatear como Mayúsculas o minúsculas.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_INSERT_INTO(String schema, Class<?> classProp, String[] excludeFields, String param, EUpperLower upperLowerNone) {
        return switch (upperLowerNone) {
            case UPPER -> get_INSERT_INTO(schema, classProp, excludeFields, param).toUpperCase().trim();
            case LOWER -> get_INSERT_INTO(schema, classProp, excludeFields, param).trim();
            default -> get_INSERT_INTO(schema, classProp, excludeFields, param);
        };    
    }
    
    /**
     * Create a SQL string using the INSERT INTO cláusule and allow excludes fields.  
     * 
     * Crea una cadenas SQL usando la cláusula INSERT INTO y permite excluir campos.
     * 
     * @param schema Schema of databse. Esquema de la base de datos.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param excludeFields Excludes fields in query. Excluye campos en la consulta.
     * @param param Mask as param for the query. Máscara como parámetro para la consulta. 
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_INSERT_INTO(String schema, Class<?> classProp, String[] excludeFields, String param) {
        try {
            String outSQL = "";
            Field[] fields = classProp.getDeclaredFields();            
            String fldBrut = "";
            String prmBrut = "";                      
            if (excludeFields == null) {
                for (Field field : fields) {
                    // Fields list name enumerate  
                    fldBrut += field.getName() + ", ";
                    prmBrut += param + ", ";
                }                           
                String fldPure = fldBrut.substring(0, fldBrut.lastIndexOf(",")).trim();
                String prmPure = prmBrut.substring(0, prmBrut.lastIndexOf(",")).trim();
                if (schema != null) {
                    outSQL = "INSERT INTO " + schema + "." + classProp.getSimpleName() + " (" + fldPure + ") VALUES (" + prmPure + ")"; 
                } else {
                    outSQL = "INSERT INTO " + classProp.getSimpleName() + " (" + fldPure + ") VALUES (" + prmPure + ")";                 
                }                           
                return outSQL;                
            } else {
                ArrayList<String> flds = new ArrayList<>();
                ArrayList<String> exFlds = new ArrayList<>();
                for (Field field : fields) {
                    flds.add(field.getName());
                }
                flds.removeAll(exFlds);
                fldBrut = "";
                prmBrut = "";
                for (String field : flds) {
                    // Fields list name enumerate  
                    fldBrut += field + ", ";
                    prmBrut += param + ", ";            
                } 
                String fldPure = fldBrut.substring(0, fldBrut.lastIndexOf(",")).trim();
                String prmPure = prmBrut.substring(0, prmBrut.lastIndexOf(",")).trim();
                if (schema != null) {
                    outSQL = "INSERT INTO " + schema + "." + classProp.getSimpleName() + " (" + fldPure + ") VALUES (" + prmPure + ")";
                } else {
                    outSQL = "INSERT INTO " + classProp.getSimpleName() + " (" + fldPure + ") VALUES (" + prmPure + ")";
                }                                                 
                return outSQL;
            }  
        } catch (Exception ex) {
            return "Error";
        }     
    }
    
    // *************************************************************************
    // *** DELETE **************************************************************
    // *************************************************************************     
    
    /**
     * Create a SQL string using DELETE clausule more set Upper or Lower case. 
     * 
     * Crea una cadena SQL usando la cláusula DELETE más ajusta en Mayúsculas o Minúsculas.
     * 
     * @param schema Schema of databse. Esquema de la base de datos.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @param upperLowerNone
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_DELETE(String schema, Class<?> classProp, ArrayList<WhereOperParam> operParam, EUpperLower upperLowerNone) {
        return switch (upperLowerNone) {
            case UPPER -> get_DELETE(schema, classProp, operParam).toUpperCase().trim();
            case LOWER -> get_DELETE(schema, classProp, operParam).trim();
            default -> get_DELETE(schema, classProp, operParam);
        };     
    }
    
    /**
     * Create a SQL string using DELETE clausule. 
     * 
     * Crea una cadena SQL usando la cláusula DELETE.
     * @param schema Schema of databse. Esquema de la base de datos.
     * 
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_DELETE(String schema, Class<?> classProp, ArrayList<WhereOperParam> operParam) {
        String pairsBrut = "";
        String pairsPure = "";
        for (WhereOperParam et : operParam) {
            if (operParam.size() == 1){
                if (schema != null) {
                    return "DELETE FROM " + schema + "." + classProp.getSimpleName() + " WHERE " + et.getField() + " " + et.getOperator() + " " + et.getValueParam();                
                } else {
                    return "DELETE FROM " + classProp.getSimpleName() + " WHERE " + et.getField() + " " + et.getOperator() + " " + et.getValueParam();                
                }                
            } else {
                pairsBrut += et.getField() + " " + et.getOperator() + " " + et.getValueParam() + " AND ";
            }
        }
        pairsPure = pairsBrut.substring(0, pairsBrut.lastIndexOf(" AND ")).trim();  
        if (schema != null) {
            return "DELETE FROM " + schema + "." + classProp.getSimpleName() + " WHERE " + pairsPure; 
        } else {
            return "DELETE FROM " + classProp.getSimpleName() + " WHERE " + pairsPure; 
        }            
    }
    
    // *************************************************************************
    // *** UPDATE **************************************************************
    // *************************************************************************       
    
    /**
     * Update data.
     * 
     * Actualiza datos.
     * @param schema Schema of databse. Esquema de la base de datos.
     * 
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @param upperLowerNone 
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_UPDATE(String schema, Class<?> classProp, ArrayList<WhereOperParam> operParam, EUpperLower upperLowerNone) {
        return switch (upperLowerNone) {
            case UPPER -> get_UPDATE(schema, classProp, operParam).toUpperCase().trim();
            case LOWER -> get_UPDATE(schema, classProp, operParam).trim();
            default -> get_UPDATE(schema, classProp, operParam);
        };    
    }
    
    /**
     * Update data.
     * 
     * Actualiza datos.
     * @param schema Schema of databse. Esquema de la base de datos.
     * 
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_UPDATE(String schema, Class<?> classProp, ArrayList<WhereOperParam> operParam) {
        String outSQL = "";
        Field[] fields = classProp.getDeclaredFields();            
        String fldBrut = "";
        String prmBrut = "";                              
        for (Field field : fields) {
            // Fields list name enumerate  
            fldBrut += field.getName() + " = ?, ";
            prmBrut += "";
        }   
        String fldPure = fldBrut.substring(0, fldBrut.lastIndexOf(",")).trim();                
        String pairsBrut = "";
        String pairsPure = "";
        for (WhereOperParam et : operParam) {
            if (operParam.size() == 1){
                if (schema != null) {
                    return "UPDATE " + schema + "." + classProp.getSimpleName() + " SET " + fldPure  + " WHERE " + et.getField() + " " + et.getOperator() + " " + et.getValueParam();
                } else {
                    return "UPDATE " + classProp.getSimpleName() + " SET " + fldPure  + " WHERE " + et.getField() + " " + et.getOperator() + " " + et.getValueParam();                
                }                
            } else {
                pairsBrut += et.getField() + " " + et.getOperator() + " " + et.getValueParam() + " AND ";
            }
        }                
        pairsPure = pairsBrut.substring(0, pairsBrut.lastIndexOf(" AND ")).trim(); 
        outSQL = "UPDATE " + classProp.getSimpleName() + " SET " + fldPure + " WHERE " + pairsPure;
        return outSQL;               
    }    
    // *************************************************************************
}