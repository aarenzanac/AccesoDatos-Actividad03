/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad03accesodatos;

import clasesPojo.Empleado;
import clasesPojo.Incidencia;
import PideDatos.PideDatos;
import clasesPojo.Historial;
import dao.GestionDao;
import enums.Prioridad;
import enums.TipoEvento;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author USUARIO
 */
public class IncidenciasBDOR {
           
    public void insertarEmpleado(GestionDao gDao){
        Empleado empleadoInsertar = crearEmpleado();
        
        boolean existe = comprobarExistenciaEmpleado(empleadoInsertar, gDao);
        
        if(!existe){
            
            System.out.println(empleadoInsertar.toString());
            gDao.Insertar((Empleado) empleadoInsertar);
            System.out.println("Empleado insertado correctamente.");
            
        }else{
            System.out.println("El empleado indicado ya existe, no se introducirá.\n");
        }
                
            
    }
    
    public void validarEmpleado(GestionDao gDao, HistorialBDOR hBDOR, FuncionesVariadas fv){
        IncidenciasBDOR iBDOR = new IncidenciasBDOR();
        Menus m = new Menus();
        Empleado login = new Empleado();
        Empleado empleadoLogin = null;
        try {
            System.out.println("A continuación introduzca los datos de login: \n");
            String nombreUsuario = PideDatos.pideString("Nombre de usuario: \n");
            String password = PideDatos.pideString("Password: \n");
            login.setNombreusuario(nombreUsuario);
            login.setPassword(password);
            //LE PASO EL OBJETO EMPLEADO CON LOS DATOS DEL USUARIO Y SI EXISTE ESE USUARIO CON ESE PASSWORD DEVUELVE TRUE         
            empleadoLogin = comprobarLoginEmpleado(login, gDao);
        } catch (IOException ex) {
            Logger.getLogger(IncidenciasBDOR.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(empleadoLogin != null){
            hBDOR.insertarEvento(TipoEvento.I, empleadoLogin, fv, gDao);
            m.menuLogueado(empleadoLogin, iBDOR, gDao, hBDOR, fv);
            
        }else{
            System.out.println("Nombre de usuario o contraseña incorrectos. \n");
        }
        
    }
    
    public Empleado crearEmpleadoParaModificarPerfil(){
        Empleado empleadoModificadoPerfil = new Empleado();
        try {
            empleadoModificadoPerfil.setNombrecompleto(PideDatos.pideString("Introduzca el nuevo nombre completo del empleado: \n"));
            empleadoModificadoPerfil.setTelefono(PideDatos.pideString("Introduzca el nuevo número de teléfono del empleado: \n"));
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
            Logger.getLogger(IncidenciasBDOR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleadoModificadoPerfil;
    }
    
    public Empleado crearEmpleadoParaModificarContraseña(){
        Empleado empleadoModificarContraseña = new Empleado();

        try {
            empleadoModificarContraseña.setPassword(PideDatos.pideString("Introduzca la nueva contraseña: \n"));

        } catch (IOException ex) {
            Logger.getLogger(IncidenciasBDOR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleadoModificarContraseña; 
    }
    
    public Empleado crearEmpleado(){
        Empleado empleado = new Empleado();
        try {//CREO EL EMPLEADO PARA AÑADIR
            empleado.setNombreusuario(PideDatos.pideString("Introduzca un nombre de usuario: \n"));
            empleado.setNombrecompleto(PideDatos.pideString("Introduzca el nombre completo: \n"));
            empleado.setTelefono(PideDatos.pideString("Introduzca el número de teléfono: \n"));
            empleado.setPassword(PideDatos.pideString("Introduzca un password"));
        } catch (IOException ex) {
            System.out.println("Eror creando empleado " + ex.getMessage());
            Logger.getLogger(IncidenciasBDOR.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return empleado;
    }
    
    public Empleado crearEmpleadoComprobaciones(){
        Empleado empleadoComprobaciones = new Empleado();
        try {String nombreUsuario = PideDatos.pideString("Introduzca el nombre de usuario del empleado: \n");
            empleadoComprobaciones.setNombreusuario(nombreUsuario);
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
            Logger.getLogger(IncidenciasBDOR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleadoComprobaciones;
    }
    
    public boolean comprobarExistenciaEmpleado(Empleado e, GestionDao gDao){
        boolean existe = false;
        ArrayList<Empleado> listaCompletaEmpleados = gDao.seleccionarTodosEmpleados();
        for(int i = 0; i < listaCompletaEmpleados.size(); i++){
            if(listaCompletaEmpleados.get(i).getNombreusuario().equals(e.getNombreusuario())){
                return true;
            }
        }
        return existe;
    }
    
    public Empleado comprobarLoginEmpleado(Empleado e, GestionDao gDao){
        Empleado empleadoLogin = null;
        ArrayList<Empleado> listaCompletaEmpleados = gDao.seleccionarTodosEmpleados();
        for(int i = 0; i < listaCompletaEmpleados.size(); i++){
            if(listaCompletaEmpleados.get(i).getNombreusuario().equals(e.getNombreusuario()) && listaCompletaEmpleados.get(i).getPassword().equals(e.getPassword())){
                empleadoLogin = listaCompletaEmpleados.get(i);
                return empleadoLogin;
            }
        }
        return empleadoLogin;
    }
        
    public void obtenerIncidenciaPorId(GestionDao gDao){
        Incidencia incidenciaSeleccionar = new Incidencia();
        incidenciaSeleccionar.setIdincidencia(PideDatos.pideEntero("Introduzca el id de la incidencia que quiere mostrar: \n"));
        boolean existe = comprobarExistenciaIncidencia(incidenciaSeleccionar, gDao);
        if(existe){
            Incidencia incidenciaSeleccionada = gDao.seleccionarIncidencia(incidenciaSeleccionar);
            System.out.println(incidenciaSeleccionada.toString());
    
        }else{
            System.out.println("No hay incidencias para mostrar con el id indicado.\n");
        }
        
    }
    
    public void obtenerListadoIncidencias(GestionDao gDao){
        FuncionesVariadas.mostrarTodasIncidencias(gDao);
  
    }
    
    public void insertarIncidencia(Empleado empleadoLogin, GestionDao gDao, HistorialBDOR hBDOR, FuncionesVariadas fv){
        Incidencia nuevaIncidencia = crearIncidencia(gDao, empleadoLogin);
        gDao.Insertar((Incidencia) nuevaIncidencia);
        
        if(nuevaIncidencia.getTipo().equals(Prioridad.URGENTE)){
           hBDOR.insertarEvento(TipoEvento.U, empleadoLogin, fv, gDao); 
        }
        
        System.out.println("Incidencia insertada con éxito.\n");
    }
    
    public void seleccionarIncidenciasPorEmpleadoDestino(Empleado empleadoLogin, GestionDao gDao, HistorialBDOR hBDOR, FuncionesVariadas fv){
        Empleado empleadoDestino = crearEmpleadoComprobaciones();
        ArrayList<Incidencia> results = new ArrayList<Incidencia>();
        ArrayList<Incidencia> listaTotalIncidencias = gDao.seleccionarTodasIncidencias();
        boolean existe = comprobarExistenciaEmpleado(empleadoDestino, gDao);
        if(!existe){
            System.out.println("El empleado indicado no existe.");
        }else{
            for(int i = 0; i < listaTotalIncidencias.size(); i++){
                if(listaTotalIncidencias.get(i).getEmpleadoDestino().getNombreusuario().equals(empleadoDestino.getNombreusuario())){
                    results.add(listaTotalIncidencias.get(i));
                }
            }
        }
        if(results.size() == 0){
            System.out.println("Listado vacio. No existe incidencias para el empleado seleccionado.\n");
        }else{
            FuncionesVariadas.mostrarIncidencias(results, gDao);
        }
    
        hBDOR.insertarEvento(TipoEvento.C, empleadoLogin, fv, gDao);
    }
    
    public void seleccionarIncidenciasPorEmpleadoOrigen(GestionDao gDao){
        Empleado empleadoOrigen = crearEmpleadoComprobaciones();
        ArrayList<Incidencia> results = new ArrayList<Incidencia>();
        ArrayList<Incidencia> listaTotalIncidencias = gDao.seleccionarTodasIncidencias();
        boolean existe = comprobarExistenciaEmpleado(empleadoOrigen, gDao);
        if(!existe){
            System.out.println("El empleado indicado no existe.");
        }else{
            for(int i = 0; i < listaTotalIncidencias.size(); i++){
                if(listaTotalIncidencias.get(i).getEmpleadoOrigen().getNombreusuario().equals(empleadoOrigen.getNombreusuario())){
                    results.add(listaTotalIncidencias.get(i));
                }
            }
        }
        if(results.size() == 0){
            System.out.println("Listado vacio. No existe incidencias para el empleado seleccionado.\n");
        }else{
            FuncionesVariadas.mostrarIncidencias(results, gDao);
        }
        
    }
    
    public boolean comprobarExistenciaIncidencia(Incidencia i, GestionDao gDao){
        boolean existe = false;
        ArrayList<Incidencia> listaCompletaIncidencias = gDao.seleccionarTodasIncidencias();
        for(int o = 0; o < listaCompletaIncidencias.size(); o++){
            if(listaCompletaIncidencias.get(o).getIdincidencia() == i.getIdincidencia()){
                return true;
            }
        }
        return existe; 
    }
    
    public boolean comprobarExistenciaEvento(Historial h, GestionDao gDao){
        boolean existe = false;
        ArrayList<Historial> listaCompletaEventos = gDao.seleccionarTodosEventos();
        for(int i = 0; i < listaCompletaEventos.size(); i++){
            if(listaCompletaEventos.get(i).getIdevento() == h.getIdevento()){
                return true;
            }
        }
        return existe; 
    }
    
    public Incidencia crearIncidencia(GestionDao gDao, Empleado empleadoLogin){
        Incidencia nuevaIncidencia = new Incidencia();
        Empleado empleadoDestino = new Empleado();
        FuncionesVariadas fv = new FuncionesVariadas();
        
        boolean existe1 = false;
        
        try {nuevaIncidencia.setIdincidencia(obtenerSiguienteId(gDao));
            nuevaIncidencia.setEmpleadoOrigen(empleadoLogin);
            do{empleadoDestino.setNombreusuario(PideDatos.pideString("Introduzca el nombre de usuario del empleado destino; \n"));
                existe1 = comprobarExistenciaEmpleado(empleadoDestino, gDao);
                if(existe1){
                    empleadoDestino = gDao.seleccionarEmpleado(empleadoDestino);
                    nuevaIncidencia.setEmpleadoDestino(empleadoDestino);
                }else{
                    System.out.println("El empleado introducido no existe");
                }
            }while(!existe1);
            nuevaIncidencia.setFechahora(fv.obtenerFechaHora());
            nuevaIncidencia.setDetalle(PideDatos.pideString("Introduzca la descripción de la incidencia: \n"));
            nuevaIncidencia.setTipo(fv.solicitarPrioridad());
        } catch (IOException ex) {
            System.out.println("Error insertando incidencia " + ex.getMessage());
            Logger.getLogger(IncidenciasBDOR.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nuevaIncidencia;
    }
    
    public static int obtenerSiguienteId(GestionDao gDao){
        int contadorId = 0;
        ArrayList<Incidencia> listaIncidencias = gDao.seleccionarTodasIncidencias();
        for(int i = 0; i < listaIncidencias.size(); i++){
            contadorId ++;
        }
        return contadorId;
    }
}