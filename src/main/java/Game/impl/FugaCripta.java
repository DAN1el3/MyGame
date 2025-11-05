/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.impl;

import Game.database.dbSetup;
import Game.GameDescription;
import Game.parser.ParserOutput;
import Game.type.AdvObject;
import Game.type.AdvObjectContainer;
import Game.type.Command;
import Game.type.CommandType;
import Game.type.Room;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import Game.GameObservable;
import Game.GameObserver;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


/**
 * ATTENZIONE: La descrizione del gioco è fatta in modo che qualsiasi gioco
 * debba estendere la classe GameDescription. L'Engine è fatto in modo che possa
 * eseguire qualsiasi gioco che estende GameDescription, in questo modo si
 * possono creare più gioci utilizzando lo stesso Engine.
 *
 * Diverse migliorie possono essere applicate: - la descrizione del gioco
 * potrebbe essere caricate da file o da DBMS in modo da non modificare il
 * codice sorgente - l'utilizzo di file e DBMS non è semplice poiché all'interno
 * del file o del DBMS dovrebbe anche essere codificata la logica del gioco
 * (nextMove) oltre alla descrizione di stanze, oggetti, ecc...
 *
 * @author natre
 */
public class FugaCripta extends GameDescription implements GameObservable {

    private final List<GameObserver> observer = new ArrayList<>();

    private ParserOutput parserOutput;

    private final List<String> messages = new ArrayList<>();

    /**
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        messages.clear();
        //Commands
        Command nord = new Command(CommandType.NORD, "nord");
        nord.setAlias(new String[]{"n", "N", "Nord", "NORD"});
        getCommands().add(nord);
        Command iventory = new Command(CommandType.INVENTORY, "inventario");
        iventory.setAlias(new String[]{"inv"});
        getCommands().add(iventory);
        Command sud = new Command(CommandType.SOUTH, "sud");
        sud.setAlias(new String[]{"s", "S", "Sud", "SUD"});
        getCommands().add(sud);
        Command est = new Command(CommandType.EAST, "est");
        est.setAlias(new String[]{"e", "E", "Est", "EST"});
        getCommands().add(est);
        Command ovest = new Command(CommandType.WEST, "ovest");
        ovest.setAlias(new String[]{"o", "O", "Ovest", "OVEST"});
        getCommands().add(ovest);
        Command end = new Command(CommandType.END, "end");
        end.setAlias(new String[]{"end", "fine", "esci", "muori", "ammazzati", "ucciditi", "suicidati", "exit", "basta"});
        getCommands().add(end);
        Command look = new Command(CommandType.LOOK_AT, "osserva");
        look.setAlias(new String[]{"guarda", "vedi", "trova", "cerca", "descrivi"});
        getCommands().add(look);
        Command pickup = new Command(CommandType.PICK_UP, "raccogli");
        pickup.setAlias(new String[]{"prendi"});
        getCommands().add(pickup);
        Command open = new Command(CommandType.OPEN, "apri");
        open.setAlias(new String[]{});
        getCommands().add(open);
        Command push = new Command(CommandType.PUSH, "premi");
        push.setAlias(new String[]{"spingi", "attiva"});
        getCommands().add(push);
        Command use = new Command(CommandType.USE, "usa");
        use.setAlias(new String[]{"utilizza", "combina"});
        getCommands().add(use);
        //Rooms
        dbSetup db = dbSetup.getInstance();
        db.inizializzaDb();
        Room startRoom = db.loadRoomsAndExits(getRooms());
        this.setCurrentRoom(startRoom);
        db.closeConnection();
        //obejcts
        AdvObject torcia = new AdvObject(1, "torcia", "Una torcia spenta, speriamo si accendi.");
        torcia.setAlias(new String[]{"fiaccola", "cero", "lume", "torcie"});
        torcia.setPickupable(true);
        getRooms().get(0).getObjects().add(torcia);
        
        AdvObjectContainer coppa = new AdvObjectContainer(2, "coppa d'argento", "Una coppa d'argento antica.");
        coppa.setAlias(new String[]{"coppa", "anfora"});
        coppa.setPickupable(true);
        getRooms().get(2).getObjects().add(coppa);
        
        AdvObject chiave = new AdvObject(3, "chiave dorata", "Una particolare chiave dorata.");
        chiave.setAlias(new String[]{"chiave", "key"});
        getRooms().get(3).getObjects().add(chiave);
        
        AdvObject sarcofago = new AdvObject(4, "sarcofago", "Un misterioso sarcofago.");
        sarcofago.setAlias(new String[]{"tomba"});
        sarcofago.setPickupable(false);
        sarcofago.setState("sporco");
        getRooms().get(4).getObjects().add(sarcofago);
        
        AdvObject acqua = new AdvObject(5, "acqua", "Dell'acqua che sembra essere magica.");
        acqua.setAlias(new String[]{"liquido","water"});
        acqua.setPickupable(false);
        getRooms().get(5).getObjects().add(acqua);
        //Observer
        GameObserver moveObserver = new MoveObserver();
        this.attach(moveObserver);
        GameObserver invObserver = new InventoryObserver();
        this.attach(invObserver);
        GameObserver lookatObserver = new LookAtObserver();
        this.attach(lookatObserver);
        GameObserver pickupObserver = new PickUpObserver();
        this.attach(pickupObserver);
        GameObserver openObserver = new OpenObserver();
        this.attach(openObserver);
        GameObserver useObserver = new UseObserver();
        this.attach(useObserver);
    }

    /**
     *
     * @param p
     * @param out
     */
    @Override
    public void nextMove(ParserOutput p, PrintStream out) {
        parserOutput = p;
        messages.clear();
        if (p.getCommand() == null) {
            out.println("Non ho capito cosa devo fare! Prova con un altro comando.");
        } else {
            Room cr = getCurrentRoom();
            notifyObservers();
            boolean move = !cr.equals(getCurrentRoom()) && getCurrentRoom() != null;
            if (!messages.isEmpty()) {
                for (String m : messages) {
                    if (m.length() > 0) {
                        out.println(m);
                    }
                }
            }
            if (move) {
                out.println(getCurrentRoom().getName());
                out.println("================================================");
                out.println(getCurrentRoom().getDescription());
            }
        }
    }

    /**
     *
     * @param o
     */
    @Override
    public void attach(GameObserver o) {
        if (!observer.contains(o)) {
            observer.add(o);
        }
    }

    /**
     *
     * @param o
     */
    @Override
    public void detach(GameObserver o) {
        observer.remove(o);
    }

    /**
     *
     */
    @Override
    public void notifyObservers() {
        for (GameObserver o : observer) {
            messages.add(o.update(this, parserOutput));
        }
    }

    /**
     *
     * @return Files.readString(Paths.get("src/main/resources/intro.txt"))
     */
    @Override
    public String getWelcomeMsg() {
        try {
            return Files.readString(Paths.get("./resources/intro.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            return "Errore nel caricamento del messaggio di benvenuto.";
        }
    }
}
