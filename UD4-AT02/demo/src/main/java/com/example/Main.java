package com.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        EmpleadoRepo repo = new EmpleadoRepo();

        /*
        repo.CrearEmpleado(1, "Juan", 10, 100, "10/10/1999", null, null);
        repo.CrearEmpleado(2, "Alicia", 10, 1400, "07/08/2000", "Profesora", null);
        repo.CrearEmpleado(3, "María Jesus", 20, 1500, "05/01/2005", "Analista",
        100);
        repo.CrearEmpleado(4, "Alberto", 20, 1100, "15/11/2001", null, null);
        repo.CrearEmpleado(5, "Fernando", 30, 1400, "20/11/1999", "Analista", 200);
        */

        // Visualiza los empleados del departamento 10.

        ArrayList<Integer> deps = new ArrayList<Integer>();
        deps.add(10);
        repo.buscarEmpleado(deps);

        // Visualiza los empleados del departamento 10 y 20 .

        deps.add(20);
        repo.buscarEmpleado(deps);

        // Obtén los empleados con salario > 1300 y oficio Profesora.

        repo.buscarPorOficioYSalario("profesora", 1300);

        // Sube el salario de todos los analistas en 100€.

        // Decrementa la comisión existente en 20€.

        // Visualiza la media de salario.

        repo.buscarPorMedia();

        // Visualiza por departamento el número de empleados, el salario medio y el máximo salario.

        repo.buscaTotal();

        // Visualiza el nombre del empleado que tiene el máximo salario.

        repo.buscarSalarioMaximo();
    }
}