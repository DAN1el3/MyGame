/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Game.thread.timer;
import Game.impl.FugaCripta;
import Game.parser.Parser;
import Game.parser.ParserOutput;
import Game.type.CommandType;
import Game.socket.LeaderboardClient;
import Game.socket.LeaderboardServer;

import java.io.*;
import java.util.Scanner;
import java.util.Set;



/**
 * ATTENZIONE: l'Engine è molto spartano, in realtà demanda la logica alla
 * classe che implementa GameDescription e si occupa di gestire I/O sul
 * terminale.
 *
 * @author natre
 */


public class Engine {
    private final GameDescription game;
    private Parser parser;
    private final timer timer;   //
    private LeaderboardClient client; 
  
    /**
     *
     * @param game
     */
    public Engine(GameDescription game, timer timer) {
        this.game = game;
        this.timer = timer;
        try {
            this.game.init();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        try {
            Set<String> stopwords = Utils.loadFileListInSet(new File("./resources/stopwords"));
            parser = new Parser(stopwords);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        client = new LeaderboardClient("localhost",5555);
    }
    
    public GameDescription getGame() {    
        return game;
    }
    
    public Game.parser.Parser getParser() {
        return parser;
    }
    
    /**
     *
     */ 
    public void execute() {
        System.out.println(game.getWelcomeMsg());
        System.out.println();
        System.out.println("Ti trovi qui: " + game.getCurrentRoom().getName());
        System.out.println();
        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println();
        System.out.print("?> ");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            ParserOutput p = parser.parse(command, game.getCommands(), game.getCurrentRoom().getObjects(), game.getInventory());
            if (p == null || p.getCommand() == null) {
                System.out.println("Non capisco quello che mi vuoi dire.");                     
            } else if (p.getCommand() != null && p.getCommand().getType() == CommandType.END) {     
                System.out.println("Sei un fifone, addio!");
                System.exit(0);
                break;
            } else {
                game.nextMove(p, System.out);
                if (game.getCurrentRoom() == null) {
                    System.out.println("La tua avventura termina qui! Complimenti!");
                    timer.stop();
                    System.out.println("Hai completato il gioco in: " + timer.GetElapsedTime());
                    Scanner scan = new Scanner(System.in);
                    System.out.print("Inserisci il tuo username: ");
                    String username = scan.nextLine();
                    client.sendScore(username, timer.GetElapsedTimeMs());
                    System.out.println("\n=== CLASSIFICA TOP 10 ===");
                    client.getScores().forEach(s -> {   
                        String[] parts = s.split(";");
                        String user = parts[0];
                        long ms = Long.parseLong(parts[1]);

                        // converto i millisecondi in minuti e secondi
                        long minuti = ms / 60000;
                        long secondi = (ms / 1000) % 60;
                        String formatted = String.format("%02d:%02d", minuti, secondi);

                        System.out.println(user + " - " + formatted);  
                    });
                System.exit(0);
                }
            System.out.print("?> ");
            }
        }
    }
    
  

    /**
     *@param args the command line arguments 
     */
    public static void main(String[] args) {
        LeaderboardServer server = new LeaderboardServer(5555);
        new Thread(server).start();
        
        timer timer = new timer();
        timer.start();
        System.out.println(" Il timer e' partito!");
        
        Engine engine = new Engine(new FugaCripta(),timer);
        engine.execute();
    }
}

