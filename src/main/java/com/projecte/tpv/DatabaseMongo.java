package com.projecte.tpv;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.projecte.tpv.model.Producte;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.projecte.tpv.Generals.*;
import static com.projecte.tpv.PagarController.data;

/**
 * Controlador dels metodes de MongoDB
 * Recull la informacio generada durant l'execucio del programa
 * @author Aida Carbonell Niubo
 */
public class DatabaseMongo {
    final static String uri = "mongodb://localhost";
    public static MongoDatabase database;
    public static MongoCollection<Document> collection;
    static ArrayList<Producte> prdStock = new ArrayList<>();
    static double totalZ2;

    /**
     * Conectar a la base de dades de MongoDB
     * @throws CannotConnectException
     */
    public void connect() throws CannotConnectException {
        try{
            MongoClient mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase("projecteTpv");
        } catch (Exception e) {
            throw new CannotConnectException();
        }
    }

    /**
     * Crea el tiquet d'una venda (Es genera uqna es finalitza la venda)
     * @param obs llista de productes venuts
     * @param total suma total dels producte venuts
     */
    public void crearTicket(ObservableList<Producte> obs, double total){
        List<Document> pr = new ArrayList<>();
        for(Producte p: obs) pr.add(new Document().append("id_prod", p.getId_prod()).append("nom", p.getNom()).append("cant", p.getCant()).append("preu", p.getPreu_venda()));
        collection = database.getCollection("ticket");
        collection.insertOne(new Document()
                .append("id_ticket", idticket)
                .append("num_ticket", numTicket)
                .append("productes", pr)
                .append("IVA", total/10)
                .append("total-iva", total-(total/10))
                .append("total", total)
                .append("data", dtf.format(data))
                .append("treballdor", treballador));
    }

    /**
     * Finalitzar l'activitat de venda<br/>
     * Veure: {@link #nextZ()}
     */
    public static void operacionZ(){
        numZ = nextZ();
        collection = database.getCollection("ticket");
        List<Document> docs = new ArrayList<>();
        Iterator it = collection.find().iterator();
        double r = 0;
        while (it.hasNext()){
            Document d = (Document) it.next();
            docs.add(d);
            r = r + Double.parseDouble(d.getString("total").replace(",", "."));
        }
        collection.drop();
        collection = database.getCollection("recopilacio");
        collection.insertOne(new Document()
                .append("numZ", numZ)
                .append("data_inici", dateInicial)
                .append("data_final", dateFinal)
                .append("recaptacioZ", df.format(r))
                .append("tickets", docs));
        totalZ2 = (double) r;
    }

    /**
     * Calcula el numero de Z seguent
     * @return numero de Z seguent
     */
    public static int nextZ(){
        collection = database.getCollection("recopilacio");
        if (collection.find().sort(descending("numZ")).first() == null) return 1;
        return collection.find().sort(descending("numZ")).first().getInteger("numZ")+1;
    }

    /**
     * Calcula el numero de tiquet seguent
     * @return numero de tiquet seguent
     */
    public static int nextIdTicket(){
        collection = database.getCollection("ticket");
        if (collection.find().sort(descending("id_ticket")).first() == null) return 1;
        return collection.find().sort(descending("id_ticket")).first().getInteger("id_ticket")+1;
    }

    /**
     * Calcula el numero de tiquet seguent
     */
    public static void nextIdTicket2(){
        collection = database.getCollection("recopilacio");
        if (collection.find().sort(descending("numZ")).first() == null) idticket = nextIdTicket();
        else {
            ArrayList<Document> doc = (ArrayList<Document>) collection.find().sort(descending("numZ")).first().get("tickets");
            doc.forEach(d -> idticket = Math.max(idticket, d.getInteger("id_ticket")));
        }
    }

    /**
     * Realitza el calcul de l'estoc que hi hauria d'haver sogons el que 'sha venut.
     * Es resta la quantitat de cada producte venut i es resta al estoc guardat a la base de dades
     */
    public static void stockGastat(){
        ArrayList<Document> d = new ArrayList<>();
        ArrayList<Document> d2 = new ArrayList<>();
        ArrayList<Producte> p = new ArrayList<>();
        collection = database.getCollection("ticket");
        collection.find(Filters.empty())
                .projection(fields(include("productes.id_prod", "productes.cant"), exclude("_id")))
                .forEach(d::add);
        d.forEach(f -> d2.addAll((Collection<? extends Document>) f.get("productes")));
        d2.forEach(l -> p.add(new Producte(l.getInteger("id_prod"), l.getInteger("cant"))));
        p.forEach(r -> {
            if (!prdStock.contains(r)) prdStock.add(r);
            else prdStock.forEach(q -> {if (q.getId_prod() == r.getId_prod()) q.setCant(q.getCant() + r.getCant());});
        });
        prdStock.forEach(System.out::println);
        prdStock.forEach(q -> comandaProductes(q.getId_prod(), q.getCant()));
    }

    /**
     * Crea un document dels productes gastats durant una venda
     * @param id id del producte
     * @param cant quantitat del producte a demanar
     */
    public static void comandaProductes(int id, int cant){
        collection = database.getCollection("comanda");
        collection.insertOne(new Document()
                .append("id_comanda", 1)
                .append("data", dtf.format(LocalDateTime.now()))
                .append("id_prod", id)
                .append("quantitat", cant));
    }
}