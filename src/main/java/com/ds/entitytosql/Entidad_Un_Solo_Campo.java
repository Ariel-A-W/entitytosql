package com.ds.entitytosql;

public class Entidad_Un_Solo_Campo {
    public String nombre;
    
    public Entidad_Un_Solo_Campo() {}
    
    public Entidad_Un_Solo_Campo(String _nombre) {
        this.nombre = _nombre;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    
}
