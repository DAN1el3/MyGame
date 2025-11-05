/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author natre
 */
public class LeaderboardServer implements Runnable{
    private static final String SCORE_FILE = "scores.txt";
    private final int port;
    private boolean running = true;
    
    public LeaderboardServer(int port) {
        this.port = port;
    }
    
    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Leaderboard Server avviato su porta " + port);

            while (running) {
                try (Socket socket = server.accept()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    String command = in.readLine();

                    if (command.startsWith("ADD")) {
                        String[] parts = command.split(";");
                        String username = parts[1];
                        long time = Long.parseLong(parts[2]);
                        saveScore(username, time);
                        out.println("OK");
                    } else if (command.equals("GET")) {
                        List<String> scores = loadScores();
                        for (String s : scores) {
                            out.println(s);
                        }
                        out.println("END");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Errore Server Classifica: " + e.getMessage());
        }
    }
    
    private synchronized void saveScore(String username, long time) throws IOException {
        List<String> scores = loadScores();
        scores.add(username + ";" + time);

        scores.sort(Comparator.comparingLong(s -> Long.parseLong(s.split(";")[1])));

        if (scores.size() > 10) {
            scores = scores.subList(0, 10);
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORE_FILE))) {
            for (String s : scores) {
                writer.println(s);
            }
        }
    }
    
    private synchronized List<String> loadScores() throws IOException {
        List<String> scores = new ArrayList<>();
        File file = new File(SCORE_FILE);
        if (!file.exists()) return scores;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                scores.add(line);
            }
        }
        return scores;
    }
}
