/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game.thread;

/**
 *
 * @author natre
 */
public class timer /*implements Runnable*/{
    private volatile boolean running = false;
    private int seconds = 0;
    private Thread thread;
    
    public void start(){
        if(thread == null || !thread.isAlive()){
            running = true;
            seconds = 0; //aggiunta
            thread = new Thread(()->{
               try{
                   while(running){
                       Thread.sleep(1000);
                       seconds++;
                   }
               }catch(InterruptedException e){
                   Thread.currentThread().interrupt();
            } 
            });
            thread.start();
        }
    }
    
    
    public void stop(){
        running = false;
    }
    
    public String GetElapsedTime(){
        int minutes = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d",minutes,sec);
    }
    
    public long GetElapsedTimeMs() { //aggiunta
        // Convert the seconds counter to milliseconds
        return (long) seconds * 1000;
    }
    
}
