/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game.database;
import Game.type.Room;


import java.sql.*; 
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.h2.tools.RunScript;

/**
 *
 * @author natre
 */
public class dbSetup {
    private static final String URL = "jdbc:h2:./db/room";
    private static final String USERNAME = "NatrellaDani";
    private static final String PASSWORD = "Cripta14";
    private static final String SQL_SCRIPT = "./db/rooms.sql"; //percorso scrpt SQL
    private static Connection connection;
    private static dbSetup instance;

    private dbSetup() {} // Il costruttore non fa nulla, è privato solo per il pattern Singleton
    
    /**
     * Metodo che restituisce l'unica istanza di dbSetup
     * 
     * @return instance 
     */
    public static synchronized dbSetup getInstance(){
        if(instance == null){
            instance = new dbSetup();
        }
        return instance;
    }
    
    /**
     * Metodo per ottenere la connessione al db 
     * 
     * @return Connesione al DB
     * @throws SQLException in caso di errore
     */
    public static Connection connect() throws SQLException{
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }
        return connection;
    }
   
    
    public void inizializzaDb() {
        try (Connection connection = connect()) {
            RunScript.execute(connection, new FileReader(SQL_SCRIPT));
        } catch (SQLException | IOException e) {
            System.err.println("ERRORE durante l'inizializzazione " + e.getMessage());
    }
    }

 
    public Room loadRoomsAndExits(final List<Room> rooms)throws SQLException {
       Room startRoom = null;
       Map<Integer, Room> mapRooms = new HashMap<>();
       Connection connection = connect();
       
        System.out.println("=== Avvio caricamento stanze dal DB ===");
        try(Statement stm = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); ResultSet rs = stm.executeQuery("SELECT* FROM CRIPTA_ROOMS")) {
            while(rs.next()) { 
                int id = rs.getInt("id");
                Room room = new Room(id);
                room.setName(rs.getString("name"));
                room.setDescription(rs.getString("description"));
                room.setLook(rs.getString("setlook"));
                room.setVisible(rs.getBoolean("visible"));
                mapRooms.put(id,room);
                rooms.add(room);
                if(id == 0){
                    startRoom = room;
                }
            }
            rs.beforeFirst();
            while(rs.next()){
                int id = rs.getInt("id");
                Room room = mapRooms.get(id);
                
                int nord = rs.getInt("nord");
                room.setNorth(rs.wasNull()? null: mapRooms.get(nord));
                int sud = rs.getInt("sud");
                room.setSouth(rs.wasNull()? null: mapRooms.get(sud));
                int est = rs.getInt("est");
                room.setEast(rs.wasNull()? null: mapRooms.get(est));
                int ovest = rs.getInt("ovest");
                room.setWest(rs.wasNull()? null: mapRooms.get(ovest));
            }
            rs.close();
        } 
        System.out.println("=== Fine caricamento stanze ===");
        System.out.println("Stanza iniziale: " + (startRoom != null ? startRoom.getName() : "NULL"));
        return startRoom;
    }
     
    public void closeConnection() throws SQLException {
        connection.close();
    }
}





       

