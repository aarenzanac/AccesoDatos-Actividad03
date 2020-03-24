/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad03accesodatos;

import PideDatos.PideDatos;
import clasesPojo.Empleado;
import dao.GestionDao;

/**
 *
 * @author alex
 */
public class Menus {
    
    //IncidenciasBDOR iBDOR = new IncidenciasBDOR();
    //FuncionesVariadas fv = new FuncionesVariadas();
    //HistorialBDOR hORM = new HistorialBDOR();
   
    
        
    public void menuPrincipal(GestionDao gDao, IncidenciasBDOR iBDOR, HistorialBDOR hBDOR, FuncionesVariadas fv){
        System.out.println("Bienvenido al programa de Gestión de Incidencias.\n");
        System.out.println("A continuación,elija una de las siguientes opciones:\n");
        
                    
        int opcion = 7;
        do{System.out.println("Opciones: \n"
                + "1 - Insertar un nuevo empleado.\n"
                + "2 - Loguear empleado.\n"
                + "3 - Modificar un empleado.\n"
                + "4 - Cambiar contraseña a un empleado.\n"
                + "5 - Eliminar un empleado.\n"
                + "6 - Salir.\n");
             
                opcion = PideDatos.pideEntero();
                
               
                switch (opcion){
                    case 1:
                        System.out.println("Ha elegio insertar un nuevo empleado.\n");
                        iBDOR.insertarEmpleado(gDao);
                        continue;
                        
                    case 2:
                        System.out.println("Ha elegido loguear un empleado.\n");
                        iBDOR.validarEmpleado(gDao, hBDOR, fv );
                        continue;
                        
                    case 3:
                        System.out.println("Ha elegido modificar un empleado.\n");
                        gDao.modificarPerfilEmpleado(gDao);
                        FuncionesVariadas.mostrarTodosEmpleados(gDao);
                        continue;
                        
                    case 4:
                        System.out.println("Ha elegido cambiar contraseña a un empleado.\n");
                        gDao.modificarContraseñaEmpleado(gDao);
                        FuncionesVariadas.mostrarTodosEmpleados(gDao);
                        continue;
                        
                    case 5:
                        System.out.println("Ha elegido eliminar un empleado.\n");
                        gDao.EliminarEmpleado(gDao);
                        FuncionesVariadas.mostrarTodosEmpleados(gDao);
                        continue;
                                           
                    case 6:
                        System.out.println("Ha elegido salir de la aplicación. Hasta pronto.\n");
                        break;
                        
                    default:
                    System.out.println("La opción introducida no es correcta. Elija una opción del 1 al 6, por favor.");
                    System.out.println("-----------------------------------------------------------------------");
                }

        }while(opcion != 6);
        
    }
    
    
    public void menuLogueado(Empleado empleadoLogin, IncidenciasBDOR iBDOR, GestionDao gDao, HistorialBDOR hBDOR, FuncionesVariadas fv){
        System.out.println("Bienvenido a la intranet.\n");
        System.out.println("A continuación,elija una de las siguientes opciones:\n");
        
                    
        int opcion = 8;
        do{System.out.println("Opciones: \n"
                + "1 - Obtener incidencia a partir del id.\n"
                + "2 - Obtener listado de incidencias.\n"
                + "3 - Insetar nueva incidencia.\n"
                + "4 - Obtener incidencias creadas para un empleado.\n"
                + "5 - Obtener incidencias creadas por un empleado.\n"
                + "6 - Acceder al menú Historial.\n"
                + "7 - Desloguearse y salir al menú anterior.\n");
             
                opcion = PideDatos.pideEntero();
                
               
                switch (opcion){
                    case 1:
                        System.out.println("Ha elegio obtener incidencia a partir de un id.\n");
                        iBDOR.obtenerIncidenciaPorId(gDao);
                        continue;
                        
                    case 2:
                        System.out.println("Ha elegido obtener listado de incidencias.\n");
                        iBDOR.obtenerListadoIncidencias(gDao);
                        continue;
                        
                    case 3:
                        System.out.println("Ha elegido insertar una nueva incidencia.\n");
                        iBDOR.insertarIncidencia(empleadoLogin, gDao, hBDOR, fv);
                        continue;
                        
                    case 4:
                        System.out.println("Ha elegido obtener incidencias creadas para un empleado.\n");
                        iBDOR.seleccionarIncidenciasPorEmpleadoDestino(empleadoLogin, gDao, hBDOR, fv);
                        continue;
                        
                    case 5:
                        System.out.println("Ha elegido Obtener incidencias creadas por un empleado.\n");
                        iBDOR.seleccionarIncidenciasPorEmpleadoOrigen(gDao);
                        continue;
                    
                    case 6:
                        System.out.println("Ha elegido acceder al menú Historial.\n");
                        HistorialBDOR hORM = new HistorialBDOR();
                        menuHistorial(empleadoLogin, iBDOR, gDao, hBDOR, fv);
                        continue;    
                    case 7:
                        System.out.println("Ha elegido desloguearse y retornar al menú anterior.\n");
                        return;
                        
                        
                    default:
                    System.out.println("La opción introducida no es correcta. Elija una opción del 1 al 6, por favor.");
                    System.out.println("-----------------------------------------------------------------------");
                }

        }while(opcion != 6);
        
    }
    
    public void menuHistorial(Empleado empleadoLogin, IncidenciasBDOR iBDOR, GestionDao gDao, HistorialBDOR hBDOR, FuncionesVariadas fv){
        System.out.println("Menú Historial.\n");
        System.out.println("A continuación,elija una de las siguientes opciones:\n");
        
                    
        int opcion = 5;
        do{System.out.println("Opciones: \n"
                + "1 - Obtener fecha y hora del último inicio de sesión para un empleado.\n"
                + "2 - Obtener ranking de empleados por cantidad de incidencias urgentes creadas.\n"
                + "3 - Obtener posición deltro del ranking para un empleado concreto.\n"
                + "4 - Retornar al menú anterior.\n");
             
                opcion = PideDatos.pideEntero();
                
               
                switch (opcion){
                    case 1:
                        System.out.println("Ha elegio obtener fecha y hora del último inicio de sesión para un empleado.\n");
                        hBDOR.obtenerFechaHoraUltimaConexionPorEmpleado(gDao, iBDOR);
                        continue;
                        
                    case 2:
                        System.out.println("Ha elegido obtener ranking de empleados por cantidad de incidencias urgentes creadas.\n");
                        hBDOR.imprimirMapOrdenado(hBDOR.obtenerRankingCantidadIncidenciasUrgentesEmpleadoOrigen(gDao));
                        continue;
                        
                    case 3:
                        System.out.println("Ha elegido obtener posición deltro del ranking para un empleado concreto.\n");
                        hBDOR.obtenerPosicionRankingPorEmpleado(iBDOR, gDao);
                        continue;
                                                                  
                    case 4:
                        System.out.println("Ha elegido retornar al menú anteior.\n");
                        menuLogueado(empleadoLogin, iBDOR, gDao, hBDOR, fv);
                        return;
                        
                        
                    default:
                    System.out.println("La opción introducida no es correcta. Elija una opción del 1 al 6, por favor.");
                    System.out.println("-----------------------------------------------------------------------");
                }

        }while(opcion != 6);
        
    }
}