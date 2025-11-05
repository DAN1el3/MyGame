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
public class LookAtObserver implements GameObserver {

    /**
     *
     * @param description
     * @param parserOutput
     * @return
     */
    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        StringBuilder msg = new StringBuilder();
        if (parserOutput.getCommand().getType() == CommandType.LOOK_AT) {
            if (description.getCurrentRoom().getLook() != null) {
                msg.append(description.getCurrentRoom().getLook());
            } else {
                msg.append("Non c'è niente di interessante qui.");
            }
        }
        return msg.toString();
    }

}
