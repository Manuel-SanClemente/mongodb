package com.example;

import org.bson.Document;

import com.mongodb.client.model.Filters;

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


    public void getTotalRecord(){
        try (MongoProvider provider = new MongoProvider()) {
            provider.record().find();
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void getBestMatch(){
        try (MongoProvider provider = new MongoProvider()) {
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void getShortestMatch(){
        try (MongoProvider provider = new MongoProvider()) {
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void getRanking(){
        try (MongoProvider provider = new MongoProvider()) {
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void getSimplified(){
        try (MongoProvider provider = new MongoProvider()) {
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void getMostScore(){
        try (MongoProvider provider = new MongoProvider()) {
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
