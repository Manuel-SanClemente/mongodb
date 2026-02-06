package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class EmpleadoRepo {

    public void crearEmpleado(int empNum, String nombre, Integer numDep, Integer salario, String fecha, String oficio,
            Integer comision) {
        try (MongoProvider provider = new MongoProvider()) {
            System.out.println("Conexión exitosa");
            Document doc = new Document();

            doc.append("numEmpleado", empNum);
            doc.append("Nombre", nombre);
            doc.append("numDepartamento", numDep);
            doc.append("Salario", salario);
            doc.append("Fecha", fecha);

            if (oficio != null) {
                doc.append("Oficio", oficio);
            }

            if (comision != null) {
                doc.append("Comisión", comision);
            }

            provider.empleado().insertOne(doc);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void borrarEmpleado(Integer empNum) {
        try (MongoProvider provider = new MongoProvider()) {
            System.out.println("Conexión exitosa");
            provider.empleado().deleteOne(Filters.eq("numEmpleado", empNum));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void buscarEmpleado(ArrayList<Integer> departamento) {
        try (MongoProvider provider = new MongoProvider()) {
            System.out.println("Conexión Exitosa");
            Document document = new Document();

            for (int i = 0; i < departamento.size(); i++) {
                document.append("numDepartamento", departamento.get(i));
            }

            provider.empleado().find(document).forEach(doc -> System.out.println(doc.toJson()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void modificarCampoPorOtro(String searchFilterName1, String searchFilterName2, String applyFilter1,
            String applyFilter2) {
        try (MongoProvider provider = new MongoProvider()) {
            System.out.println("Conexión Exitosa");
            Document doc1 = new Document();
            Document doc2 = new Document();

            if (searchFilterName1 == "numEmpleado" || searchFilterName1 == "numDepartamento"
                    || searchFilterName1 == "Salario" || searchFilterName1 == "Comisión") {
                Integer newTerm = Integer.parseInt(searchFilterName2);
                doc1.append(searchFilterName1, newTerm);
            } else {
                doc1.append(searchFilterName1, searchFilterName2);
            }

            if (applyFilter1 == "numEmpleado" || applyFilter1 == "numDepartamento" || applyFilter1 == "Salario"
                    || applyFilter1 == "Comisión") {
                Integer newTerm = Integer.parseInt(searchFilterName2);
                doc2.append(applyFilter1, newTerm);
            } else {
                doc2.append(applyFilter1, applyFilter2);
            }

            provider.empleado().updateMany(doc1, doc2);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void buscarPorOficioYSalario(String oficio, Integer salario) {
        try (MongoProvider provider = new MongoProvider()) {
            System.out.println("Conexión Exitosa");

            provider.empleado().find(Filters.and(Filters.eq("Oficio", oficio), Filters.gt("Salario", salario)))
                    .forEach(doc -> System.out.println(doc.toJson()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void buscarPorMedia() {
        try (MongoProvider provider = new MongoProvider()) {
            System.out.println("Conexión Exitosa");

            AggregateIterable<Document> filter = provider.empleado().aggregate(
                Arrays.asList(Aggregates.group("_id", new BsonField("avgSal", new BsonDocument("$avg", new BsonString("$salario"))))));

            Document result = filter.first();

            double media = (double) result.get("avgSal");

            System.out.println(media);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public void buscaTotal(){
        try (MongoProvider provider = new MongoProvider()) {
            List<Document> lista = new ArrayList<>();
            List<Bson> pipeline = List.of(
                Aggregates.group("$numDepartamento",
                    Accumulators.sum("numEmpleados", 1),
                    Accumulators.avg("salarioMedio", "$Salario"),
                    Accumulators.max("numEmpleado", "$Salario")
                ),
                Aggregates.sort(Sorts.ascending("numDepartamento"))
            );

            provider.empleado().aggregate(pipeline).into(lista);

            lista.forEach(doc -> System.out.println(doc.toJson()));
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void buscarSalarioMaximo() {
        try (MongoProvider provider = new MongoProvider()) {
            List<Document> lista = new ArrayList<>();
            List<Bson> pipeline = List.of(
                Aggregates.group("$numEmpleado", Accumulators.max("SalarioMaximo", "$Salario")),
                Aggregates.sort(Sorts.descending("SalarioMaximo"))
            );

            provider.empleado().aggregate(pipeline).into(lista);
            lista.forEach(doc -> System.out.println(doc));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

}
