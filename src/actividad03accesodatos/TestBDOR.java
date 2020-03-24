/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad03accesodatos;

import dao.GestionDao;

/**
 *
 * @author alex
 */
public class TestBDOR {
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        GestionDao gDao = new GestionDao();
        HistorialBDOR hBDOR = new HistorialBDOR();
        IncidenciasBDOR iBDOR = new IncidenciasBDOR();
        FuncionesVariadas fv = new FuncionesVariadas();
                
        try{  
            fv.crearDatos(gDao, iBDOR);

            FuncionesVariadas.mostrarTodosEmpleados(gDao);
            FuncionesVariadas.mostrarTodasIncidencias(gDao);
            FuncionesVariadas.mostrarTodosEventos(gDao);

            Menus menu = new Menus();
            menu.menuPrincipal(gDao, iBDOR, hBDOR, fv);

            gDao.cerrarDB4O();
            gDao.borrarDB();
            System.exit(0);
            
        }catch(Exception e){
            System.out.println("Error general.\n");
        }finally{
            gDao.cerrarDB4O();
            gDao.borrarDB();
        }
    }    
}