/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game.socket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author natre
 */
public class LeaderboardClient {
    
    private final String host;
    private final int port;
    
    public LeaderboardClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public void sendScore(String username, long time) {
        try (Socket socket = new Socket(host, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("ADD;" + username + ";" + time);
        } catch (IOException e) {
            System.out.println("Errore invio punteggio: " + e.getMessage());
        }
    }
    
    public List<String> getScores() {
        List<String> scores = new ArrayList<>();
        try (Socket socket = new Socket(host, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("GET");
            String line;
            while (!(line = in.readLine()).equals("END")) {
                scores.add(line);
            }
        } catch (IOException e) {
            System.out.println("Errore ricezione classifica: " + e.getMessage());
        }
        return scores;
    }
}
