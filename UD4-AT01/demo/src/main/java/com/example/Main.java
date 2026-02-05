package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AlumnoRepo repo = new AlumnoRepo();

        Scanner sc = new Scanner(System.in);

        String option = sc.next();

        if (option == "1") {           

            System.out.print("\nNombre: ");
            String nombreAlum = sc.next();
            System.out.print("\nEdad: ");
            Integer edadAlum = sc.nextInt();
            System.out.print("\nCurso:");
            String cursoAlum = sc.next();

            repo.engadirAlumno(nombreAlum, edadAlum, cursoAlum);
        } else if (option == "2") {
            System.out.print("\nNombre: ");
            String nombre = sc.next();
            

            repo.borrarAlumno(nombre);
        }

        sc.close();
              
    }
}