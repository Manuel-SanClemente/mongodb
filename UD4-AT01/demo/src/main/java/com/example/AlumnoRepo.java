package com.example;

import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.model.Filters;

public class AlumnoRepo {
    AlumnoRepo(){}

    public void engadirAlumno(String nombreAlum, Integer edadAlum, String cursoAlum){
        try (MongoProvider provider = new MongoProvider()) {
            Document doc = new Document();

            System.out.println("\nConexión exitosa.");


            doc.append("nombre", nombreAlum);
            doc.append("edad", edadAlum);
            doc.append("curso", cursoAlum);

            provider.alumnado().insertOne(doc);

            provider.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void borrarAlumno(String nombre){
        try (MongoProvider provider = new MongoProvider()) {
            provider.alumnado().deleteOne(Filters.eq("nombre", nombre));            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
