package com.ds.entitytosql;

import com.ds.entitytosql.helpers.EUpperLower;
import com.ds.entitytosql.Entidad_Ejemplo;
import com.ds.entitytosql.helpers.OrderByEntity;
import com.ds.entitytosql.helpers.ReflectorEntityToSQL;
import com.ds.entitytosql.helpers.WhereOperParam;
import java.util.ArrayList;

/**
 *
 * @author Ariel
 */
public class Entitytosql {

    public static void main(String[] args) {
        System.out.println("Preubas con Reflexiones\n");
        
        var rf = new ReflectorEntityToSQL(); 

        // Pruebas con SELECT
        String sql = rf.get_SELECT(Entidad_Ejemplo.class);        
        System.out.println(sql);       
        // Forzar todo a mayúsculas.
        sql = rf.get_SELECT(Entidad_Ejemplo.class, EUpperLower.UPPER);        
        System.out.println(sql);
        
        // Pruebas coi SELECT ORDER BY 
        // Un Orden
        var arrOB1 = new ArrayList<OrderByEntity>(); 
        arrOB1.add(new OrderByEntity("nombre", "ASC"));
        sql = rf.get_SELECT_ORDERBY(Entidad_Ejemplo.class, arrOB1); 
        System.out.println(sql);       
        // Doble Orden
        var arrOB2 = new ArrayList<OrderByEntity>(); 
        arrOB2.add(new OrderByEntity("email", "ASC"));
        arrOB2.add(new OrderByEntity("nombre", "DESC"));
        sql = rf.get_SELECT_ORDERBY(Entidad_Ejemplo.class, arrOB2); 
        System.out.println(sql);  
        // Un Orden forzado a Maypúsculas. 
        var arrOB3 = new ArrayList<OrderByEntity>(); 
        arrOB3.add(new OrderByEntity("nombre", "ASC"));
        sql = rf.get_SELECT_ORDERBY(Entidad_Ejemplo.class, EUpperLower.UPPER, arrOB3); 
        System.out.println(sql);         
        // Doble puesto en Mayúsculas.
        var arrOB4 = new ArrayList<OrderByEntity>(); 
        arrOB4.add(new OrderByEntity("email", "ASC"));
        arrOB4.add(new OrderByEntity("nombre", "DESC"));
        sql = rf.get_SELECT_ORDERBY(Entidad_Ejemplo.class, EUpperLower.UPPER, arrOB4); 
        System.out.println(sql);  
        
        // Pruebas con SELECT WHERE 
        // Un WHERE
        var arrWH1 = new ArrayList<WhereOperParam>(); 
        arrWH1.add(new WhereOperParam("id", "LIKE", "?"));
        sql = rf.get_SELECT_WHERE(Entidad_Ejemplo.class, arrWH1); 
        System.out.println(sql);
        // Doble WHERE 
        var arrWH2 = new ArrayList<WhereOperParam>(); 
        arrWH2.add(new WhereOperParam("id", "==", "?"));
        arrWH2.add(new WhereOperParam("nombre", "LIKE", "'%?'"));
        sql = rf.get_SELECT_WHERE(Entidad_Ejemplo.class, arrWH2); 
        System.out.println(sql);   
        var arrWH3 = new ArrayList<WhereOperParam>(); 
        arrWH3.add(new WhereOperParam("id", "==", "?"));
        arrWH3.add(new WhereOperParam("nombre", "LIKE", "'%?'"));
        sql = rf.get_SELECT_WHERE(Entidad_Ejemplo.class, EUpperLower.UPPER, arrWH3); 
        System.out.println(sql);         
        // WHERE y ORDER BY 
        var arrWH4 = new ArrayList<WhereOperParam>(); 
        var arrOB44 = new ArrayList<OrderByEntity>(); 
        arrWH4.add(new WhereOperParam("nombre", "LIKE", "'%?'"));
        arrOB44.add(new OrderByEntity("nombre", "ASC"));        
        sql = rf.get_SELECT_WHERE_ORDERBY(Entidad_Ejemplo.class, arrWH4, arrOB44); 
        System.out.println(sql);    
        // Más de un WHERE y ORDER BY
        var arrWH5 = new ArrayList<WhereOperParam>(); 
        var arrOB54 = new ArrayList<OrderByEntity>(); 
        arrWH5.add(new WhereOperParam("email", "LIKE", "'%?'"));
        arrOB54.add(new OrderByEntity("nombre", "ASC"));   
        arrOB54.add(new OrderByEntity("email", "DESC"));
        sql = rf.get_SELECT_WHERE_ORDERBY(Entidad_Ejemplo.class, arrWH5, arrOB54); 
        System.out.println(sql);    
        // Más de un WHERE y ORDER BY pasado a Mayúsculas
        var arrWH6 = new ArrayList<WhereOperParam>(); 
        var arrOB64 = new ArrayList<OrderByEntity>(); 
        arrWH6.add(new WhereOperParam("email", "LIKE", "'%?'"));
        arrOB64.add(new OrderByEntity("nombre", "ASC"));   
        arrOB64.add(new OrderByEntity("email", "DESC"));
        sql = rf.get_SELECT_WHERE_ORDERBY(Entidad_Ejemplo.class, EUpperLower.UPPER, arrWH6, arrOB64); 
        System.out.println(sql);         
    }
}
