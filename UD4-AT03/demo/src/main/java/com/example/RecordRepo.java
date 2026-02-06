package com.example;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;

/**
 * Clase encargada do manexo dos Records
 */
public class RecordRepo {
    private static RecordRepo instance; 

    /**
     * Constructor da clase base. Aplica o patrón Singleton, polo que non ten nada
     */
    private RecordRepo() {}

    /**
     * Para obter unha instancia da clase 
     * @return unha instancia da clase
     */
    public static RecordRepo getInstance() {
        if (instance == null) {
            instance = new RecordRepo();
        }
        return instance;
    }

    /**
     * Para engadir un novo record a Base de Datos.
     * @param userName Nome do xogador co record
     * @param gameName Nome do xogo
     * @param score Puntuación total
     * @param duration Duración total
     * @param level Nivel no que se acabou o xogador
     */
    public void addRecord(String userName, String gameName, int score, int duration, int level) {
        try (MongoProvider provider = new MongoProvider()) {
            Document doc = new Document();

            doc.append("nome", userName);
            doc.append("xogo", gameName);
            doc.append("puntuacion", score);
            doc.append("duracion", duration);
            doc.append("nivel", level);
            
            provider.record().insertOne(doc);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Para buscar o record total de cada xogador.
     */
    public void getTotalRecord(){
        try (MongoProvider provider = new MongoProvider()) {
            List<Document> list = new ArrayList<>();
            List<Bson> pipeline = List.of(
                Aggregates.group("$nome", 
                Accumulators.sum("puntuacionTotal", "$puntuacion")),
                Aggregates.sort(Sorts.ascending("puntuacionTotal"))
            ); 
            
            provider.record().aggregate(pipeline).into(list);
            list.forEach(doc -> System.out.println(doc.toJson()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Para atopar a puntuación máxima de cada xogador
     */
    public void getBestMatch(){
        try (MongoProvider provider = new MongoProvider()) {
            List<Document> list = new ArrayList<>();
            List<Bson> pipeline = List.of(
                Aggregates.group("$nome", 
                    Accumulators.max("puntuacionMaxima", "$puntuacion")),
                    Aggregates.sort(Sorts.ascending("puntuacionMaxima"))
            );
            
            provider.record().aggregate(pipeline).into(list);
            list.forEach(doc -> System.out.println(doc));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Para obter a minima duración de partida por xogo
     */
    public void getShortestMatch(){
        try (MongoProvider provider = new MongoProvider()) {
            List<Document> list = new ArrayList<>();
            List<Bson> pipeline = List.of(
                Aggregates.group("$xogo", 
                Accumulators.min("duracion", "$duracion")
            ));
            provider.record().aggregate(pipeline).into(list);
            list.forEach(doc -> System.out.println(doc));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Para obter a clasificación dos xogadores. Obten a puntuación total, ordeada de maior a menor
     */
    public void getRanking(){
        try (MongoProvider provider = new MongoProvider()) {
            List<Document> list = new ArrayList<>();
            List<Bson> pipeline = List.of(
                Aggregates.group("$nome", 
                Accumulators.sum("puntuacionTotal", "$puntuacion")),
                Aggregates.sort(Sorts.descending("puntuacionTotal"))
            );
            provider.record().aggregate(pipeline).into(list);
            list.forEach(doc -> System.out.println(doc));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Para obter unha consulta simplificada. Busca soamente o nome, o xogo e a puntuación, excluindo o _id na sua proxección
     */
    public void getSimplified(){
        try (MongoProvider provider = new MongoProvider()) {
            Document projection = new Document();
            projection.append("_id", 0);
            projection.append("nome", 1);
            projection.append("xogo", 1);
            projection.append("puntuacion", 1);

            provider.record().find().projection(projection).forEach(doc -> System.out.println(doc));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Para obter a puntuación máxima que cada xogo tivo.
     */
    public void getMostScore(){
        try (MongoProvider provider = new MongoProvider()) {
            List<Document> list = new ArrayList<>();
            List<Bson> pipeline = List.of(
                Aggregates.group("$xogo",
                Accumulators.avg("puntuacionMedia", "$puntuacion")),
                Aggregates.sort(Sorts.descending("puntuacionMedia"))
            );
            provider.record().aggregate(pipeline).into(list);
            list.forEach(doc -> System.out.println(doc));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
