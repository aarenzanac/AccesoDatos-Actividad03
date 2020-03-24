/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad03accesodatos;


import PideDatos.PideDatos;
import clasesPojo.Empleado;
import clasesPojo.Historial;
import java.util.ArrayList;
import dao.GestionDao;
import enums.TipoEvento;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 *
 * @author USUARIO
 */
public class HistorialBDOR {
    
    public void insertarEvento(TipoEvento tipo, Empleado e, FuncionesVariadas fv, GestionDao gDao){
        
        Historial nuevoAsiento = new Historial();
        nuevoAsiento.setIdevento(obtenerSiguienteId(gDao));
        nuevoAsiento.setEmpleado(e);
        nuevoAsiento.setTipo(tipo);
        nuevoAsiento.setFechahora(fv.obtenerFechaHora());
        gDao.Insertar((Historial) nuevoAsiento);
        System.out.println("Nuevo asiento de historial creado.\n");
    
    }
    
    public int obtenerSiguienteId(GestionDao gDao){
        int contadorId = 0;
        ArrayList<Historial> listaEventos = gDao.seleccionarTodosEventos();
        for(int i = 0; i < listaEventos.size(); i++){
            contadorId ++;
        }
        return contadorId;
    }
    
    public void obtenerFechaHoraUltimaConexionPorEmpleado(GestionDao gDao, IncidenciasBDOR iBDOR){
        Empleado empleadoUltimaConexion = iBDOR.crearEmpleadoComprobaciones();
        
        boolean existe = iBDOR.comprobarExistenciaEmpleado(empleadoUltimaConexion, gDao);
        
        if(!existe){
            System.out.println("El usuario introducido no existe. \n");
        }else{
            Historial ultimaConexion =  seleccionarUltimaConexion(gDao, empleadoUltimaConexion);
            if(ultimaConexion == null){
                System.out.println("No hay conexiones para el empleado indicado.\n");
            }else{
                System.out.println("Última Conexión: \n");
                System.out.println(ultimaConexion.toString());
            }
        }
    }
    
    public Map<Empleado, Integer> obtenerRankingCantidadIncidenciasUrgentesEmpleadoOrigen(GestionDao gDao){
        ArrayList<Historial> listaTodosEventos = gDao.seleccionarTodosEventos();
        ArrayList<Empleado> listaEmpleados = gDao.seleccionarTodosEmpleados();
        ArrayList<Historial> listaEventosUrgentes = new ArrayList<Historial>();
        ArrayList<Historial> eventosPorEmpleado = new ArrayList<Historial>();
        
        ArrayList<Empleado> empleados = new ArrayList<Empleado>();
        ArrayList<Integer> numeroIncidencias = new ArrayList<Integer>();
        
        HashMap <Empleado, Integer> sumaPorEmpleado = new HashMap<Empleado, Integer>();
        
        for(int i = 0; i < listaTodosEventos.size(); i++){
            if(listaTodosEventos.get(i).getTipo().equals(TipoEvento.U)){
                listaEventosUrgentes.add(listaTodosEventos.get(i));
            }
        }
        for(int i = 0; i < listaEmpleados.size(); i++){
            eventosPorEmpleado.clear();
            for(int e = 0; e < listaEventosUrgentes.size(); e++){
                if(listaEventosUrgentes.get(e).getEmpleado().equals(listaEmpleados.get(i))){
                    eventosPorEmpleado.add(listaEventosUrgentes.get(e));
                }
            }sumaPorEmpleado.put(listaEmpleados.get(i), eventosPorEmpleado.size()); 
        }
        
        //sumaPorEmpleado.forEach((k,v) -> System.out.println("Empleado: " + k.getNombrecompleto() + " ---- Cantidad: " + v));
        //System.out.println("********************************************");      
        
        Map<Empleado, Integer> rankingOrdenado = ordenarHashMap(sumaPorEmpleado);
        
        return rankingOrdenado;        
    }

    public void imprimirMapOrdenado(Map<Empleado, Integer> rankingOrdenado){
        
        rankingOrdenado.forEach((k,v) -> System.out.println("Empleado: " + k.getNombrecompleto() + " --- Nº Incidencias creadas: " + v + "\n"));
    }
    
    public static Map<Empleado, Integer> ordenarHashMap(final Map<Empleado, Integer> sumaPorEmpleado) {
        return sumaPorEmpleado.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
        
    public void obtenerPosicionRankingPorEmpleado(IncidenciasBDOR iBDOR, GestionDao gDao){
        Empleado e = new Empleado();
        int posicion = 1;
        try {
            e.setNombreusuario(PideDatos.pideString("Introduzca el nombre de usuario del que desea obtener la posición: \n"));
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        boolean existe = iBDOR.comprobarExistenciaEmpleado(e, gDao);
                 
        if(!existe){
            System.out.println("Nombre de usuario inexistente. Introduzca un nombre de usuario existente.\n");
        }else{
            Map<Empleado, Integer> rankingOrdenado = obtenerRankingCantidadIncidenciasUrgentesEmpleadoOrigen(gDao);
            System.out.println("**************************");
            for (Map.Entry<Empleado, Integer> entry : rankingOrdenado.entrySet()) {
                Empleado em = entry.getKey();
                if(em.getNombreusuario().equals(e.getNombreusuario())){
                    System.out.println("La posición en el ranking de empleados creadores de incidencias urgentes es: " + posicion + "\n");
                }else{
                    posicion ++;   
                }
            }           
        }
        System.out.println("\n");
    }
   
    public Historial seleccionarUltimaConexion(GestionDao gDao, Empleado e){
        Historial ultimaConexion = null;
        ArrayList<Historial> listaTodosEventos = gDao.seleccionarTodosEventos();
        ArrayList<Historial> listaEventosEmpleado = new ArrayList<Historial>();
        for(int i = 0; i < listaTodosEventos.size(); i++){
            if(listaTodosEventos.get(i).getEmpleado().getNombreusuario().equals(e.getNombreusuario()) && listaTodosEventos.get(i).getTipo().equals(TipoEvento.I)){
                listaEventosEmpleado.add(listaTodosEventos.get(i));
            }
        }
        if(listaEventosEmpleado.size() > 0){
            ultimaConexion = listaEventosEmpleado.get(listaEventosEmpleado.size() - 1);
            return ultimaConexion;
        }
        return ultimaConexion;
    }
}