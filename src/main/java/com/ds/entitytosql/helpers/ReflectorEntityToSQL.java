package com.ds.entitytosql.helpers;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ReflectorEntityToSQL {
    // *************************************************************************
    // *** SELECT **************************************************************
    // *************************************************************************    
    
    /**
     * Create a sentence SELECT for SQL string. 
     * 
     * Crea una cadena SQL con SELECT.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT(Class<?> classProp) {
        try {
            String outSQL = "";
            Field[] fields = classProp.getDeclaredFields();
            
            String fldBrut = "";
            for (Field field : fields) {
                // Fields list name enumerate              
                fldBrut += field.getName() + ", ";
            }
            String fldPure = fldBrut.substring(0, fldBrut.lastIndexOf(",")).trim();
            outSQL = "SELECT " + fldPure + " FROM " + classProp.getSimpleName();            
            return outSQL;        
        } catch (Exception ex) {
            return "Error";
        }           
    }
    
    /**
     * Create a SQL string using the WHERE clausule. 
     * 
     * Crea una cadena SQL usando la cláusula WHERE.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT_WHERE(Class<?> classProp, ArrayList<WhereOperParam> operParam) {
        String pairsBrut = "";
        String pairsPure = "";
        for (WhereOperParam et : operParam) {
            if (operParam.size() == 1){
                return get_SELECT(classProp)+ " WHERE " + et.getField() + " " + et.getOperator() + " " + et.getValueParam();
            } else {
                pairsBrut += et.getField() + " " + et.getOperator() + " " + et.getValueParam() + " AND ";
            }
        }
        pairsPure = pairsBrut.substring(0, pairsBrut.lastIndexOf(" AND ")).trim();                    
        return get_SELECT(classProp)+ " WHERE " + pairsPure;        
    }
    
    /**
     * Create a SQL string using the WHERE clausule. 
     * 
     * Crea una cadena SQL usando la cláusula WHERE.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param upperLowerNone Format as Upper or Lower case. Formatear como Mayúsculas o minúsculas.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */    
    public String get_SELECT_WHERE(Class<?> classProp, EUpperLower upperLowerNone, ArrayList<WhereOperParam> operParam) {
        return switch (upperLowerNone) {
            case UPPER -> get_SELECT_WHERE(classProp, operParam).toUpperCase().trim();
            case LOWER -> get_SELECT_WHERE(classProp, operParam).toLowerCase().trim();
            default -> get_SELECT_WHERE(classProp, operParam);
        };                
    }    
    
    /**
     * Create a SQL string using WHERE and ORDER BY clausules. 
     * 
     * Crea una cadeda de SQL usando las clásulas WHERE y ORDER BY. 
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @param orderByArray Array format clausule for ORDER BY. Formato para la cláusula ORDER BY.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT_WHERE_ORDERBY(Class<?> classProp, ArrayList<WhereOperParam> operParam, ArrayList<OrderByEntity> orderByArray) {
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
        return get_SELECT(classProp) + " WHERE " + pairsPure + " " + pairsPure2;               
    }
    
    /**
     * Create a SQL string using WHERE and ORDER BY clausules in adittion using of Upper or Lower cases. 
     * 
     * Crea una cadeda de SQL usando las clásulas WHERE y ORDER BY además del uso de Mayúsculas y Minúsculas.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param upperLowerNone Format as Upper or Lower case. Formatear como Mayúsculas o minúsculas.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @param orderByArray Array format clausule for ORDER BY. Formato para la cláusula ORDER BY.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT_WHERE_ORDERBY(Class<?> classProp, EUpperLower upperLowerNone, ArrayList<WhereOperParam> operParam, ArrayList<OrderByEntity> orderByArray) {
        return switch (upperLowerNone) {
            case UPPER -> get_SELECT_WHERE_ORDERBY(classProp, operParam, orderByArray).toUpperCase().trim();
            case LOWER -> get_SELECT_WHERE_ORDERBY(classProp, operParam, orderByArray).toLowerCase().trim();
            default -> get_SELECT_WHERE_ORDERBY(classProp, operParam, orderByArray);
        };
    }
    
    /**
     * Create a sentence SELECT for SQL string using the ORDER BY clausule. 
     * 
     * Crea una cadena SQL con SELECT usando la cláusula ORDER BY.  
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param orderByArray Array format clausule for ORDER BY. Formato para la cláusula ORDER BY.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT_ORDERBY(Class<?> classProp, ArrayList<OrderByEntity> orderByArray) {        
        String pairsBrut = "";
        String pairsPure = "";
        for (OrderByEntity et : orderByArray) {
            if (orderByArray.size() == 1){
                return get_SELECT(classProp) + " ORDER BY " + et.getField() + " " + et.getOrder_type();
            } else {
                pairsBrut += et.getField() + " " + et.getOrder_type() + ", ";
            }
        }
        pairsPure = pairsBrut.substring(0, pairsBrut.lastIndexOf(",")).trim();                    
        return get_SELECT(classProp)+ " ORDER BY " + pairsPure;
    }
    
    /**
     * Create a sentence SELECT for SQL string using the ORDER BY clausule for SQL string in Upper or Lowear cases. 
     * 
     * Crea una cadena SQL con SELECT usando la cláusula ORDER BY en mayúsculas o minúsculas.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param upperLowerNone Format as Upper or Lower case. Formatear como Mayúsculas o minúsculas.
     * @param orderByArray Array format clausule for ORDER BY. Formato para la cláusula ORDER BY.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT_ORDERBY(Class<?> classProp, EUpperLower upperLowerNone, ArrayList<OrderByEntity> orderByArray) {
        return switch (upperLowerNone) {
            case UPPER -> get_SELECT_ORDERBY(classProp, orderByArray).toUpperCase().trim();
            case LOWER -> get_SELECT_ORDERBY(classProp, orderByArray).toLowerCase().trim();
            default -> get_SELECT_ORDERBY(classProp, orderByArray);
        };    
    }    
    
    /**
     * Create a sentence SELECT for SQL string in Upper or Lowear cases. 
     * 
     * Crea una cadena SQL con SELECT en mayúsculas o minúsculas.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param upperLowerNone Format as Upper or Lower case. Formatear como Mayúsculas o minúsculas. 
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_SELECT(Class<?> classProp, EUpperLower upperLowerNone) {
        return switch (upperLowerNone) {
            case UPPER -> get_SELECT(classProp).toUpperCase().trim();
            case LOWER -> get_SELECT(classProp).toLowerCase().trim();
            default -> get_SELECT(classProp);
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
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param excludeFields Excludes fields in query. Excluye campos en la consulta.
     * @param param Mask as param for the query. Máscara como parámetro para la consulta. 
     * @param upperLowerNone Format as Upper or Lower case. Formatear como Mayúsculas o minúsculas.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_INSERT_INTO(Class<?> classProp, String[] excludeFields, String param, EUpperLower upperLowerNone) {
        return switch (upperLowerNone) {
            case UPPER -> get_INSERT_INTO(classProp, excludeFields, param).toUpperCase().trim();
            case LOWER -> get_INSERT_INTO(classProp, excludeFields, param).trim();
            default -> get_INSERT_INTO(classProp, excludeFields, param);
        };    
    }
    
    /**
     * Create a SQL string using the INSERT INTO cláusule and allow excludes fields.  
     * 
     * Crea una cadenas SQL usando la cláusula INSERT INTO y permite excluir campos.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param excludeFields Excludes fields in query. Excluye campos en la consulta.
     * @param param Mask as param for the query. Máscara como parámetro para la consulta. 
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_INSERT_INTO(Class<?> classProp, String[] excludeFields, String param) {
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
                outSQL = "INSERT INTO " + classProp.getSimpleName() + " (" + fldPure + ") VALUES (" + prmPure + ")";            
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
                outSQL = "INSERT INTO " + classProp.getSimpleName() + " (" + fldPure + ") VALUES (" + prmPure + ")";                                 
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
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @param upperLowerNone
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_DELETE(Class<?> classProp, ArrayList<WhereOperParam> operParam, EUpperLower upperLowerNone) {
        return switch (upperLowerNone) {
            case UPPER -> get_DELETE(classProp, operParam).toUpperCase().trim();
            case LOWER -> get_DELETE(classProp, operParam).trim();
            default -> get_DELETE(classProp, operParam);
        };     
    }
    
    /**
     * Create a SQL string using DELETE clausule. 
     * 
     * Crea una cadena SQL usando la cláusula DELETE.
     * @param classProp Property argument for a class. Propiedad para el argumento de clase.
     * @param operParam Array of fields, operations and values or values more masked. Arreglo 
     * de campos, operaciones y valores o valores más comodín.
     * @return Return a compose string in SQL. Retorna una cadena compuesta SQL.
     */
    public String get_DELETE(Class<?> classProp, ArrayList<WhereOperParam> operParam) {
        String pairsBrut = "";
        String pairsPure = "";
        for (WhereOperParam et : operParam) {
            if (operParam.size() == 1){
                return "DELETE FROM " + classProp.getSimpleName() + " WHERE " + et.getField() + " " + et.getOperator() + " " + et.getValueParam();
            } else {
                pairsBrut += et.getField() + " " + et.getOperator() + " " + et.getValueParam() + " AND ";
            }
        }
        pairsPure = pairsBrut.substring(0, pairsBrut.lastIndexOf(" AND ")).trim();                    
        return "DELETE FROM " + classProp.getSimpleName() + " WHERE " + pairsPure;     
    }
    
    // *************************************************************************
    // *** UPDATE **************************************************************
    // *************************************************************************       
    
    public String get_UPDATE(Class<?> classProp, ArrayList<WhereOperParam> operParam, EUpperLower upperLowerNone) {
        return switch (upperLowerNone) {
            case UPPER -> get_UPDATE(classProp, operParam).toUpperCase().trim();
            case LOWER -> get_UPDATE(classProp, operParam).trim();
            default -> get_UPDATE(classProp, operParam);
        };    
    }
    
    public String get_UPDATE(Class<?> classProp, ArrayList<WhereOperParam> operParam) {
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
                return "UPDATE " + classProp.getSimpleName() + " SET " + fldPure  + " WHERE " + et.getField() + " " + et.getOperator() + " " + et.getValueParam();
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