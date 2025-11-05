/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Game.swing;

import Game.Engine;
import Game.impl.FugaCripta;


import javax.swing.*;
import java.awt.*;



/**
 *
 * @author natre
 */
public class GameFrame extends javax.swing.JFrame {
    
    private final Engine engine;
    private long startTime;
    private final javax.swing.Timer swingTimer;
    private final Game.socket.LeaderboardClient client;
    
    
    // Metodo che gestisce l’esecuzione dei comandi del giocatore
    private void processCommand(String command) {
    if (engine == null) return;

    try {
        var p = engine.getParser().parse(
            command,
            engine.getGame().getCommands(),
            engine.getGame().getCurrentRoom().getObjects(),
            engine.getGame().getInventory()
        );

        if (p == null || p.getCommand() == null) {
            jTextArea1.append("Non capisco quello che mi vuoi dire.\n");
        } 
        else if (p.getCommand().getType() == Game.type.CommandType.END) {
            jTextArea1.append("Sei un fifone, addio!\n");
            swingTimer.stop();
            dispose();
            System.exit(0);
        } 
        else {
            // Esegue il comando e mostra il risultato nel textArea
            engine.getGame().nextMove(p, new java.io.PrintStream(new java.io.OutputStream() {
                @Override
                public void write(int b) throws java.io.IOException {
                    jTextArea1.append(String.valueOf((char) b));
                }
            }));

            // 🔽 Controlla se la partita è finita
            if (engine.getGame().getCurrentRoom() == null) {
                
                swingTimer.stop();
                

                // Calcola il tempo trascorso
                long ms = System.currentTimeMillis() - startTime;
                long minuti = ms / 60000;
                long secondi = (ms / 1000) % 60;
                String formatted = String.format("%02d:%02d", minuti, secondi);

                jTextArea1.append("\n Hai completato il gioco in " + formatted + "!\n");

                
                String username = JOptionPane.showInputDialog(this, "Inserisci il tuo username per la classifica:");

                if (username != null && !username.isBlank()) {
                    try {
                        // Invia punteggio al server
                        client.sendScore(username, ms);

                        // Recupera la classifica dal server
                        java.util.List<String[]> classifica = new java.util.ArrayList<>();

                        java.util.List<String> scores = client.getScores();

                        int posizione = 1;
                        for (String s : scores) {
                            try {
                                String[] parts = s.split(";");
                                if (parts.length < 2) continue; // evita righe non valide
                                String user = parts[0];
                                long tempo = Long.parseLong(parts[1]);
                                long min = tempo / 60000;
                                long sec = (tempo / 1000) % 60;
                                String tempoFmt = String.format("%02d:%02d", min, sec);
                                classifica.add(new String[]{String.valueOf(posizione++), user, tempoFmt});
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        // Mostra finestra classifica
                        LeaderBoardFrame leaderboard = new LeaderBoardFrame();
                        leaderboard.aggiornaClassifica(classifica);
                        leaderboard.setVisible(true);

                        // Chiude la finestra del gioco
                        dispose();
                    } catch (Exception ex) {
                        jTextArea1.append("Errore durante l'invio o il recupero della classifica.\n");
                        ex.printStackTrace();
                    }
                } else {
                    jTextArea1.append("Nessun username inserito. Classifica non aggiornata.\n");
                }
            }
        }
    } catch (Exception e) {
        jTextArea1.append("Errore durante l’esecuzione del comando.\n");
        e.printStackTrace();
    }
}


    /**
     * Creates new form GameFrame
     */
    public GameFrame() {
        initComponents();
        
        setLayout(new BorderLayout());
        
        add(headerPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        
        headerPanel.setBackground(new Color(85, 60, 45));
        gamePanel.setBackground(new Color(40, 30, 20));
        inputPanel.setBackground(new Color(60, 40, 25));
        new Thread(() -> {
            try { 
                Game.socket.LeaderboardServer server = new Game.socket.LeaderboardServer(5555);
                new Thread(server).start();
                System.out.println("Server classifica avviato sulla porta 5555!");
            } catch (Exception e) {
                    System.out.println("️Server classifica già attivo o errore: " + e.getMessage());
                }
        }).start();
        
        startTime = System.currentTimeMillis();
        
        engine = new Engine(new FugaCripta(), null);
        client = new Game.socket.LeaderboardClient("localhost", 5555);
        
        jTextArea1.append(engine.getGame().getWelcomeMsg() + "\n");
        
        // Timer Swing che aggiorna la label ogni secondo
        swingTimer = new javax.swing.Timer(1000, e -> {
            long elapsedMs = System.currentTimeMillis() - startTime;//timer.GetElapsedTimeMs();
            long minuti = elapsedMs / 60000;
            long secondi = (elapsedMs / 1000) % 60;
            String formatted = String.format("%02d:%02d", minuti, secondi);
            timerLabel.setText(formatted); 
        });
        
        timerLabel.setFont(new Font("Serif", Font.BOLD, 18));
        timerLabel.setForeground(new Color(255, 230, 180));
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        swingTimer.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        timerLabel = new javax.swing.JLabel();
        gamePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        inputPanel = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        headerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 204));
        jLabel1.setText("LA CRIPTA ANTICA");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(275, 275, 275)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(timerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        timerLabel.getAccessibleContext().setAccessibleName("timerLabel");

        gamePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(30, 30, 30));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        inputPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setBackground(new java.awt.Color(100, 50, 20));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("INVIA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputPanelLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gamePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(headerPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String command = jTextField1.getText().trim();
        if(!command.isEmpty()){
            processCommand(command);
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel gamePanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel timerLabel;
    // End of variables declaration//GEN-END:variables
}
