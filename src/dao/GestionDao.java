/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import actividad03accesodatos.IncidenciasBDOR;
import clasesPojo.Empleado;
import clasesPojo.Historial;
import clasesPojo.Incidencia;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.io.File;
import java.util.ArrayList;


/**
 *
 * @author alex
 */
public class GestionDao {
    
    private final ObjectContainer db;
    
    public GestionDao(){
         
        db = Db4oEmbedded.openFile("GestionIncidencias");
     }
    
    public void cerrarDB4O(){
         db.close();
         
    }
    
    public void borrarDB(){
        
         File f = new File("GestionIncidencias");
         f.delete();
    }
        
    public void Insertar(Object objeto){
    
        db.store(objeto);
    }
    
    public void EliminarEmpleado(GestionDao gDao){
        IncidenciasBDOR iBDOR = new IncidenciasBDOR();
        Empleado empleadoEliminar = iBDOR.crearEmpleadoComprobaciones();
        boolean existe = iBDOR.comprobarExistenciaEmpleado(empleadoEliminar, gDao);
        if(existe){
            Empleado empleadoSeleccionado = seleccionarEmpleado(empleadoEliminar);
            db.delete(empleadoSeleccionado);
            System.out.println("Empleado eliminado con éxito.\n");
        }else{
            System.out.println("El empleado que desea eliminar no existe.\n");
        }
  
    }
    
    public Empleado seleccionarEmpleado(Empleado empleado){
        
        ObjectSet resultado = db.queryByExample(empleado);
        Empleado empleadoSeleccionado = (Empleado) resultado.next();
        return empleadoSeleccionado;
    }
    
    public void modificarPerfilEmpleado(GestionDao gDao){
        IncidenciasBDOR iBDOR = new IncidenciasBDOR();
        Empleado empleadoModificar = iBDOR.crearEmpleadoComprobaciones();
        boolean existe = iBDOR.comprobarExistenciaEmpleado(empleadoModificar, gDao);
        if(existe){
            Empleado empleadoModificado = iBDOR.crearEmpleadoParaModificarPerfil();
            Empleado empleadoSeleccionado = seleccionarEmpleado(empleadoModificar);
            empleadoSeleccionado.setNombrecompleto(empleadoModificado.getNombrecompleto());
            empleadoSeleccionado.setTelefono(empleadoModificado.getTelefono());
            db.store(empleadoSeleccionado);
            System.out.println("Empleado modificado con éxito.\n");
        }else{
            System.out.println("El empleado que desea modificar no existe.\n");
        }
        
    }
    
    
    public void modificarContraseñaEmpleado(GestionDao gDao){
        IncidenciasBDOR iBDOR = new IncidenciasBDOR();
        Empleado empleadoModificar = iBDOR.crearEmpleadoComprobaciones();
        boolean existe = iBDOR.comprobarExistenciaEmpleado(empleadoModificar, gDao);
        if(existe){
            Empleado empleadoModificado = iBDOR.crearEmpleadoParaModificarContraseña();
            Empleado empleadoSeleccionado = seleccionarEmpleado(empleadoModificar);
            empleadoSeleccionado.setPassword(empleadoModificado.getPassword());
            db.store(empleadoSeleccionado);
            System.out.println("Empleado modificado con éxito.\n");
        }else{
            System.out.println("El empleado que desea modificar no existe.\n");
        }
    }
    
    public ArrayList<Empleado> seleccionarTodosEmpleados(){
        ArrayList<Empleado> listaEmpleado = new ArrayList<Empleado>();
        Query q = db.query(); //Creamos la sentencia query
        q.constrain(Empleado.class); //Asignamos la condición
        ObjectSet resultado = q.execute(); //Ejecutamos la sentencia y recuperamos el resultado

        while(resultado.hasNext()){
            Empleado e = (Empleado) resultado.next();
            listaEmpleado.add(e);
        }
        return listaEmpleado;   
    }
    
    public Incidencia seleccionarIncidencia(Incidencia incidencia){
        ObjectSet resultado = db.queryByExample(incidencia);
        Incidencia incidenciaSeleccionada = (Incidencia) resultado.next();
        return incidenciaSeleccionada;
    }
    
    public ArrayList<Incidencia> seleccionarTodasIncidencias(){
        ArrayList<Incidencia> listaIncidencias = new ArrayList<Incidencia>();
        Query q = db.query(); //Creamos la sentencia query
        q.constrain(Incidencia.class); //Asignamos la condición
        ObjectSet resultado = q.execute(); //Ejecutamos la sentencia y recuperamos el resultado

        while(resultado.hasNext()){
            Incidencia i = (Incidencia) resultado.next();
            listaIncidencias.add(i);
        }
        return listaIncidencias;
        
    }
    
    public ArrayList<Historial> seleccionarTodosEventos(){
        ArrayList<Historial> listaEventos = new ArrayList<Historial>();
        Query q = db.query(); //Creamos la sentencia query
        q.constrain(Historial.class); //Asignamos la condición
        ObjectSet resultado = q.execute(); //Ejecutamos la sentencia y recuperamos el resultado

        while(resultado.hasNext()){
            Historial h = (Historial) resultado.next();
            listaEventos.add(h);
        }
        return listaEventos;
    }     
}