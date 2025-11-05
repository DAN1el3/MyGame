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
public class PickUpObserver implements GameObserver {

    /**
     *
     * @param description
     * @param parserOutput
     * @return
     */
    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        StringBuilder msg = new StringBuilder();
        if (parserOutput.getCommand().getType() == CommandType.PICK_UP) {
            if (parserOutput.getObject() != null) {
                if(parserOutput.getObject().getName().equalsIgnoreCase("acqua")){
                    return("Le mani non sono adatte ad essere un contenitore");
                }
                if (parserOutput.getObject().isPickupable()) {
                    description.getInventory().add(parserOutput.getObject());
                    description.getCurrentRoom().getObjects().remove(parserOutput.getObject());
                    msg.append("Hai raccolto: ").append(parserOutput.getObject().getDescription());
                    switch (description.getCurrentRoom().getId()) {
                        case 0:
                            description.getCurrentRoom().setLook("Adesso la stanza e vuota.");
                            break;
                        case 2:
                            description.getCurrentRoom().setLook("E' solo un altare vuoto.");
                            break;
                        case 3:
                            description.getCurrentRoom().setLook("Ci sono solo dei libri impolverati e illegibili.");
                            break;
                        default:
                            break;
                    }
                } else {
                    msg.append("Non puoi raccogliere questo oggetto.");
                }
            } else {
                msg.append("Non c'e' niente da raccogliere qui.");
            }
        }
        return msg.toString();
    }

}
