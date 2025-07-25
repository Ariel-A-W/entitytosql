package com.ds.entitytosql.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ReflectorEntityToSQL {
//    public void ConvertEntitySQL(Class<?> clazz) {
//        System.out.println("Nombre de la clase: " + clazz.getSimpleName());
//        System.out.println("\n--- Campos declarados ---");
//
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            System.out.println("- " + field.getType().getSimpleName() + " " + field.getName());
//        }
//
//        System.out.println("\n--- Métodos Getters y Setters ---");
//
//        Method[] methods = clazz.getDeclaredMethods();
//        for (Method method : methods) {
//            if (Modifier.isPublic(method.getModifiers())) {
//                String name = method.getName();
//                if ((name.startsWith("get") || name.startsWith("set")) && method.getParameterCount() <= 1) {
//                    System.out.println("- " + name + "()");
//                }
//            }
//        }
//    }
    
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
        switch(upperLowerNone) {
            case UPPER: 
                return get_SELECT_WHERE(classProp, operParam).toUpperCase().trim();
            case LOWER:                 
                return get_SELECT_WHERE(classProp, operParam).toLowerCase().trim();
            default: 
                return get_SELECT_WHERE(classProp, operParam);
        }                
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
        switch(upperLowerNone) {
            case UPPER: 
                return get_SELECT_WHERE_ORDERBY(classProp, operParam, orderByArray).toUpperCase().trim();
            case LOWER: 
                return get_SELECT_WHERE_ORDERBY(classProp, operParam, orderByArray).toLowerCase().trim();
            default: 
                return get_SELECT_WHERE_ORDERBY(classProp, operParam, orderByArray);                          
        }
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
        switch(upperLowerNone) {
            case UPPER: 
                return get_SELECT_ORDERBY(classProp, orderByArray).toUpperCase().trim();
            case LOWER:                 
                return get_SELECT_ORDERBY(classProp, orderByArray).toLowerCase().trim();
            default: 
                return get_SELECT_ORDERBY(classProp, orderByArray);
        }    
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
        switch(upperLowerNone) {
            case UPPER: 
                return get_SELECT(classProp).toUpperCase().trim();
            case LOWER:                 
                return get_SELECT(classProp).toLowerCase().trim();
            default: 
                return get_SELECT(classProp);
        }
    }

    
    
    
    // *************************************************************************
}
