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
public class MoveObserver implements GameObserver {

    /**
     *
     * @param description
     * @param parserOutput
     * @return
     */
    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        if (parserOutput.getCommand().getType() == CommandType.NORD) {
            if (description.getCurrentRoom().getNorth() != null) {
                if(!description.getCurrentRoom().getNorth().isVisible()){
                    return "E' troppo buio per proseguire senza una torcia";    
                }else{
                    description.setCurrentRoom(description.getCurrentRoom().getNorth());
                }
            } else {
                return "Provaci pure, ma da quella parte c'e' solo un bel muro ammuffito";
            }
        } else if (parserOutput.getCommand().getType() == CommandType.SOUTH) {
            if (description.getCurrentRoom().getSouth() != null) {
                description.setCurrentRoom(description.getCurrentRoom().getSouth());
            } else {
                return "Provaci pure, ma da quella parte c'e' solo un bel muro ammuffito";
            }
        } else if (parserOutput.getCommand().getType() == CommandType.EAST) {
            if (description.getCurrentRoom().getEast() != null) {
                description.setCurrentRoom(description.getCurrentRoom().getEast());
            } else {
                return "Provaci pure, ma da quella parte c'e' solo un bel muro ammuffito";
            }
        } else if (parserOutput.getCommand().getType() == CommandType.WEST) {
            if (description.getCurrentRoom().getName().equalsIgnoreCase("Corridoio Oscuro")) {
                return "Provi ad aprire la porta, ma e' sbarrata.\nSembra non esserci modo di forzarla...";
            } else {
            }
            if (description.getCurrentRoom().getWest() != null) {
                     description.setCurrentRoom(description.getCurrentRoom().getWest());
            } else {
                return "Provaci pure, ma da quella parte c'e' solo un bel muro ammuffito";
            }
        }
        return "";
    }

}
