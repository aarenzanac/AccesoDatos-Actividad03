/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad03accesodatos;

import PideDatos.PideDatos;
import enums.Prioridad;
import clasesPojo.Empleado;
import clasesPojo.Historial;
import clasesPojo.Incidencia;
import dao.GestionDao;
import static enums.Prioridad.NORMAL;
import static enums.Prioridad.URGENTE;
import static enums.TipoEvento.C;
import static enums.TipoEvento.I;
import static enums.TipoEvento.U;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author USUARIO
 */
public class FuncionesVariadas {
             
    public void crearDatos(GestionDao gDao, IncidenciasBDOR iBDOR){
        boolean existe = false;
        System.out.println("Añadiendo empleados...\n");
        ArrayList<Empleado> listaEmpleados = new ArrayList<Empleado>();        
        Empleado e = new Empleado("lara", "1234", "Lara Bastida García", "111111111");
        listaEmpleados.add(e);
        Empleado e1 = new Empleado("emma", "2222", "Emma Arenzana Bastida", "444444444");
        listaEmpleados.add(e1);
        Empleado e2 = new Empleado("sofia", "5555", "Sofía Arenzana Bastida", "999999999");
        listaEmpleados.add(e2);
        Empleado e3 = new Empleado("alex", "1234", "Alejandro Arenzana Casis", "000000000");
        listaEmpleados.add(e3);
        
        for(int i = 0; i < listaEmpleados.size(); i++){
            existe = iBDOR.comprobarExistenciaEmpleado(listaEmpleados.get(i), gDao);
            if(existe){
                System.out.println("Empleado existente, no se añadirá.\n");
            }else{
                gDao.Insertar((Empleado) listaEmpleados.get(i));
            }
        }
        System.out.println("Empleados añadidos.\n");
        
        System.out.println("Añadiendo incidencias...\n");
        ArrayList<Incidencia> listaIncidencias = new ArrayList<Incidencia>();        
        Incidencia i = new Incidencia(0, e, e1,"2020/01/01 08:00:00", "No funciona la calefacción",URGENTE);
        listaIncidencias.add(i);
        Incidencia i1 = new Incidencia(1, e2, e3, "2019/12/05 09:37:00", "No arranca el coche", NORMAL);
        listaIncidencias.add(i1);
        Incidencia i2 = new Incidencia(2, e1, e3, "2019/11/12 11:26:00", "Error en el sistema de arranque", URGENTE);
        listaIncidencias.add(i2);
        Incidencia i3 = new Incidencia(3, e, e3, "2019/06/22 18:55:00", "Golpe en la aleta delantera derecha", NORMAL);
        listaIncidencias.add(i3);
        
        for(int o = 0; o < listaIncidencias.size(); o++){
            existe = iBDOR.comprobarExistenciaIncidencia(listaIncidencias.get(o), gDao);
            if(existe){
                System.out.println("Incidencia existente, no se añadirá.\n");
            }else{
                gDao.Insertar((Incidencia) listaIncidencias.get(o));
            }
        }
        System.out.println("Incidencias añadidas.\n");
        
        System.out.println("Añadiendo eventos del historial...\n");
        ArrayList<Historial> listaEventos = new ArrayList<Historial>();
        Historial h = new Historial(0, e, I, "2019/01/25 08:12:00");
        listaEventos.add(h);
        Historial h1 = new Historial(1, e1, C, "2019/05/14 10:42:00");
        listaEventos.add(h1);
        Historial h2 = new Historial(2, e2, U,"2019/12/25 11:59:00");
        listaEventos.add(h2);
        Historial h3 = new Historial(3, e3, I, "2020/02/28 07:04:00");
        listaEventos.add(h3);
        
        for(int p = 0; p < listaIncidencias.size(); p++){
            existe = iBDOR.comprobarExistenciaEvento(listaEventos.get(p), gDao);
            if(existe){
                System.out.println("Evento existente, no se añadirá.\n");
            }else{
                gDao.Insertar((Historial) listaEventos.get(p));
            }
        }   
        System.out.println("Eventos del historial añadidos.\n");
    }
    
    public static Prioridad crearPrioridad(String prioridadString){
        
        if(prioridadString.equals("URGENTE")){
        return Prioridad.URGENTE;
        }else{
        return Prioridad.NORMAL;
        }
    }
    
    public Prioridad solicitarPrioridad() throws IOException{
        Prioridad prioridad = null;
        String prioridadString = null;
        do{
            prioridadString = PideDatos.pideString("Introduzca la prioridad (NORMAL O URGENTE): \n").toUpperCase();
            if(!prioridadString.equals("URGENTE") && !prioridadString.equals("NORMAL")){
                System.out.println("Valor incorrecto. Debe ser Normal o Urgente.\n");
            }
        }while(!prioridadString.equals("URGENTE") && !prioridadString.equals("NORMAL"));
                 
               
        switch(prioridadString){
            case("URGENTE"):
                return Prioridad.URGENTE;
                
            case("NORMAL"):
                return Prioridad.NORMAL;
        }
        
        return prioridad;
    }
    
    public String obtenerFechaHora(){
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String fechaHora = hourdateFormat.format(date).toString();
        //System.out.println(fechaHora);
        return fechaHora;
    }
    
    public static void mostrarTodosEmpleados(GestionDao gDao){
        System.out.println("Se muestran los empleados introducidos: \n");
        ArrayList<Empleado> listaEmpleados = gDao.seleccionarTodosEmpleados();

        for(int i = 0; i < listaEmpleados.size(); i++){
            System.out.println(listaEmpleados.get(i).toString());
        }
    }
    
    public static void mostrarTodasIncidencias(GestionDao gDao){
        System.out.println("Se muestran las incidencias introducidas: \n");
        ArrayList<Incidencia> listaIncidencias = gDao.seleccionarTodasIncidencias();
        
        for(int i = 0; i < listaIncidencias.size(); i++){
            System.out.println(listaIncidencias.get(i).toString());
        }
    }
    
    public static void mostrarTodosEventos(GestionDao gDao){
        System.out.println("Se muestran los eventos introducidos: \n");
        ArrayList<Historial> listaEventos = gDao.seleccionarTodosEventos();
        
        for(int i = 0; i < listaEventos.size(); i++){
            System.out.println(listaEventos.get(i).toString());
        }
    }
    
    public static void mostrarIncidencias(ArrayList<Incidencia> listaIncidencias, GestionDao gDao){
        System.out.println("Se muestran las incidencias introducidas: \n");
        for(int i = 0; i < listaIncidencias.size(); i++){
            System.out.println(listaIncidencias.get(i).toString());
        }
    }
    
}