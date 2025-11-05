/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game.impl;

import Game.GameDescription;
import Game.parser.ParserOutput;
import Game.type.AdvObject;
import Game.type.CommandType;
import Game.GameObserver;

/**
 *
 * @author natre
 */
public class InventoryObserver implements GameObserver {

    /**
     *
     * @param description
     * @param parserOutput
     * @return
     */
    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        StringBuilder msg = new StringBuilder();
        if (parserOutput.getCommand().getType() == CommandType.INVENTORY) {
            if (description.getInventory().isEmpty()) {
                msg.append("Il tuo inventario è vuoto!");
            } else {
                msg.append("Nel tuo inventario ci sono:\n");
                for (AdvObject o : description.getInventory()) {
                    msg.append(o.getName()).append(": ").append(o.getDescription()).append("\n");
                }
            }
        }
        return msg.toString();
    }

}
