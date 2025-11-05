/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game.impl;

import Game.GameDescription;
import Game.parser.ParserOutput;
import Game.type.CommandType;
import Game.GameObserver;

/**
 *
 * @author natre
 */
public class OpenObserver implements GameObserver {

    /**
     *
     * @param description
     * @param parserOutput
     * @return
     */
    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        StringBuilder msg = new StringBuilder();
        if (parserOutput.getCommand().getType() == CommandType.OPEN) {
            /*ATTENZIONE: quando un oggetto contenitore viene aperto, tutti gli oggetti contenuti
                * vengongo inseriti nella stanza o nell'inventario a seconda di dove si trova l'oggetto contenitore.
                * Potrebbe non esssere la soluzione ottimale.
             */
            if (parserOutput.getObject() == null && parserOutput.getInvObject() == null) {
                msg.append("Non c'e' niente da aprire qui.");
            } else {
                if (parserOutput.getObject() != null && parserOutput.getObject().getId() == 4) {
                    if (parserOutput.getObject().getState() != null && parserOutput.getObject().getState().equals("sbloccato")) {
                            msg.append("\n=============================================\n");
                            msg.append("Congratulazioni! Il coperchio scorre via con un gemito metallico.\n ");
                            msg.append("All'interno trovi una botola che conduce fuori dalla Tana del Custode!");
                            msg.append("\n\n*** FINE DEL GIOCO ***");
                            msg.append("\n=============================================");
                            description.setCurrentRoom(null);
                        }else{
                        msg.append("Il meccanismo di chiusura e' ancora attivo. Devi usare la chiave per sbloccarlo.");
                    }
            }
        }
        
        }
        return msg.toString();
    }
    
}
