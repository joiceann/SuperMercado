package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import com.sun.org.apache.regexp.internal.RE;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<String> usuarios= new ArrayList<>();
        usuarios.add("Joice");
        usuarios.add("Pablo");
        usuarios.add("Sebas");
        usuarios.add("Mimi");


        ArrayList<Integer> categoria= new ArrayList<>();
        categoria.add(1);
        categoria.add(2);
        categoria.add(3);
        categoria.add(4);
        categoria.add(5);


        Producto producto1 = new Producto(1,"leche1", 10.00, 1,1);
        Producto producto2 = new Producto(2,"leche2", 20.00, 1,1);
        Producto producto3 = new Producto(3,"leche3", 10.00, 1,1);
        Producto producto4 = new Producto(4,"carne1", 15.00, 2,2);
        Producto producto5 = new Producto(5,"carne2", 23.00, 2,2);
        Producto producto6 = new Producto(6,"carne3", 19.00, 2,2);
        Producto producto4 = new Producto(4,"carne1", 15.00, 2,2);
        Producto producto5 = new Producto(5,"carne2", 23.00, 2,2);
        Producto producto6 = new Producto(6,"carne3", 19.00, 2,2);


        //String mensaje= jsons(usuarios,10,15);
        //System.out.println(mensaje);
    }

    public ArrayList<String> usuariosQuery () {

        ArrayList<String> lista = new ArrayList<>();
        String SQL = "SELECT nombre FROM cliente WHERE nombre is not null";

        BDConeccion bd = new BDConeccion();
        ResultSet rs = null;
        try (Connection conn = bd.connect();
             Statement stmt = conn.createStatement()) {
            System.out.println("llegó");
            System.out.println(SQL);
            rs = stmt.executeQuery(SQL);
            try {
                while (rs.next()) {

                    lista.add(
                            rs.getString("nombre")
                    );
                }
            } catch (SQLException e) {
                System.out.println(e);
            }

        } catch (SQLException ex) {
            System.out.print("ERROR: Hubo un error al conectar con la BD o ejecutar la query");
        }
        return lista;
    }

    public ArrayList<Integer> categoriasQuery () {

        ArrayList<Integer> lista = new ArrayList<>();
        String SQL = "SELECT id_categoria FROM categoria WHERE id_categoria is not null";

        BDConeccion bd = new BDConeccion();
        ResultSet rs = null;
        try (Connection conn = bd.connect();
             Statement stmt = conn.createStatement()) {
            System.out.println("llegó");
            System.out.println(SQL);
            rs = stmt.executeQuery(SQL);
            try {
                while (rs.next()) {

                    lista.add(
                            rs.getInt("id_categoria")
                    );
                }
            } catch (SQLException e) {
                System.out.println(e);
            }

        } catch (SQLException ex) {
            System.out.print("ERROR: Hubo un error al conectar con la BD o ejecutar la query");
        }
        return lista;
    }


    public ArrayList<Producto> productosQuery () {
        ArrayList<Producto> lista = new ArrayList<>();
        String SQL = "SELECT * FROM producto";

        BDConeccion bd = new BDConeccion();
        ResultSet rs = null;
        try (Connection conn = bd.connect();
             Statement stmt = conn.createStatement()) {
            System.out.println("llegó");
            System.out.println(SQL);
            rs = stmt.executeQuery(SQL);
            try {
                while (rs.next()) {
                    Producto producto=new Producto(rs.getInt("id_producto"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("id_marca"), rs.getInt("id_categoria"));
                    lista.add(
                            producto
                    );
                }
            } catch (SQLException e) {
                System.out.println(e);
            }

        } catch (SQLException ex) {
            System.out.print("ERROR: Hubo un error al conectar con la BD o ejecutar la query");
        }
        return lista;
    }

    public static void simulacion (ArrayList<String> usuarios, ArrayList<Integer> categoria,ArrayList<Producto> productos, int tiempoMax, int prodMax){
        Random rand = new Random();
        String resultado="";

        //Por cada usuario
        for (int i=0; i<usuarios.size();i++){
            ArrayList<String> compra = new ArrayList<>();
            resultado=resultado+"{ \n \"nombre\":\""+usuarios.get(i)+"\" \n";
            //tiempo que se tardara el usuario i en la sucursal
            int tiempo = rand.nextInt(tiempoMax)+1;
            int indiceCat= rand.nextInt(categoria.size());
            int pasilloInicial = categoria.get(indiceCat);
            //pasillo por minuto que visita el cliente
            int numPasillo = pasilloInicial;
            for (int j=0;j<tiempo;j++){
                resultado=resultado+"\""+j+"\" :"+numPasillo+"\n";
                ArrayList<Producto> mismaCat= new ArrayList<>();
                //Elije los que estan en el mismo pasillo para agregarlos a la compra
                for(int k=0; k<productos.size();k++){
                    if (productos.get(k).getIdCategoria()==numPasillo){
                        mismaCat.add(productos.get(k));
                    }
                }

                int cantProductos= rand.nextInt(prodMax);
                for (int h=0; h< cantProductos;h++){
                    int eleccion = rand.nextInt(mismaCat.size());
                    compra.add(mismaCat.get(eleccion).getNombre());
                }


                //Cambio de pasillo
                if (numPasillo==1){
                    numPasillo++;
                }
                else if (numPasillo==categoria.size()){
                    numPasillo--;
                }
                else{
                    //int cambio = rand.nextInt(1 + 1 -(-1)) + (-1);
                    int cambio = rand.nextInt(100 );
                    System.out.println("random entre 0 y 1 "+ cambio);
                    if (cambio<80){
                        cambio=1;
                    }
                    else{
                        cambio=0;
                    }
                    System.out.println(cambio);
                    numPasillo=numPasillo+cambio;
                }
            }
            resultado=resultado+"\"compra \" : { ";
            for (int g=0;g<compra.size();g++){
                resultado=resultado+compra.get(g)+", ";
            }
            resultado=resultado+"} \n";
            resultado=resultado+"}\n";
        }


    }

    /*public static String jsons (ArrayList<String> usuarios, int cantPasillos, int cantMaxTiempo, ArrayList<Integer>productos, int cantProducto ){
        Random rand = new Random();
        String resultado="";
        String compra="";
        //Por cada usuario
        for (int i=0;i<usuarios.size();i++ ){
            resultado=resultado+"{ \n \"nombre\":\""+usuarios.get(i)+"\" \n";
            int tiempo= (int)(Math.random()*cantMaxTiempo)+1;
            int pasilloInicial = ((int)(Math.random()*cantPasillos))+1;
            System.out.println("pasillo inicial "+ pasilloInicial);
            //pasillo por
            int numPasillo = pasilloInicial;
            for (int j=1; j<tiempo;j++){
                resultado=resultado+"\""+j+"\" :"+numPasillo+"\n";

                if (numPasillo==1){
                    numPasillo++;
                }
                else if (numPasillo==cantPasillos){
                    numPasillo--;
                }
                else{
                    //int cambio = rand.nextInt(1 + 1 -(-1)) + (-1);
                    int cambio = rand.nextInt(100 );
                    System.out.println("random entre 0 y 1 "+ cambio);
                    if (cambio<80){
                        cambio=1;
                    }
                    else{
                        cambio=0;
                    }
                    System.out.println(cambio);
                    numPasillo=numPasillo+cambio;
                }
                System.out.println("pasillo siguiente"+ numPasillo);

            }

        resultado=resultado+"}\n";
        }

        return resultado;
    }*/


}
