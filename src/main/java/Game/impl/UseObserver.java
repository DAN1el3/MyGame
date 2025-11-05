/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game.impl;

import Game.GameDescription;
import Game.GameObserver;
import Game.GameUtils;
import Game.parser.ParserOutput;
import Game.type.CommandType;
import Game.type.AdvObjectContainer;
import Game.type.AdvObject;

/**
 *
 * @author natre
 */
public class UseObserver implements GameObserver {

    /**
     *
     * @param description
     * @param parserOutput
     * @return
     */
    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        StringBuilder msg = new StringBuilder();
        if (parserOutput.getCommand().getType() == CommandType.USE) {
            boolean interact = false;
            boolean torcia = parserOutput.getInvObject() != null && parserOutput.getInvObject().getId() == 1;
            torcia = torcia || parserOutput.getObject() != null && parserOutput.getObject().getId() == 1;
            if (torcia) {
                if(description.getCurrentRoom().getNorth().isVisible()){
                    msg.append("Stai gia utilizzando la torcia,la stanza e' visibile");
                }else{                  
                    description.getCurrentRoom().getNorth().setVisible(true);                  
                    msg.append("Hai utilizzato la torcia, riesci a vedere il corridoio");                                       
                }
                interact = true;
            }
            boolean acqua = parserOutput.getInvObject() != null && parserOutput.getInvObject().getId() == 5;
            acqua = acqua || parserOutput.getObject() != null && parserOutput.getObject().getId() == 5;
            
            AdvObjectContainer coppa = (AdvObjectContainer)GameUtils.getObjectFromInventory(description.getInventory(), 2);
            
            
            if (acqua && coppa != null){
                if(coppa.getList().isEmpty()){
                    AdvObject acquaDaRiempire = description.getCurrentRoom().getObject(5);
                    if(acquaDaRiempire != null){
                        coppa.add(acquaDaRiempire);
                        msg.append("Versi l'acqua nella coppa d'argento. Ora e' piena. "); 
                    }
                }else{
                    msg.append("La coppa e' piena, non puoi riempirla.");
                }
                interact = true;
            }else if (acqua && coppa == null) {
                msg.append("Non hai nessun contenitore adatto per raccogliere l'acqua.");
                interact = true;
            }
             
            AdvObject sarcofago = description.getCurrentRoom().getObject(4);
            boolean sarcofagoPresente = sarcofago != null;
            
            if (parserOutput.getInvObject() != null && parserOutput.getInvObject().getId() == 2 && coppa != null && sarcofagoPresente) {
                if(sarcofago.getState()!= null && sarcofago.getState().equals("pulito")){
                    msg.append("Il sarcofago e' già pulito. Ora devi solo trovare la chiave giusta.");
                    interact = true;
                }else if(!coppa.getList().isEmpty()){
                    msg.append("Versi l'acqua sul sarcofago\n"
                            + "Le antiche incisioni cominciano a brillare, liberandosi dalla polvere dei secoli.\n"
                            + "Ora puoi inserire la chiave dorata nella fessura al centro del coperchio.\n"); 
                    sarcofago.setState("pulito");
                    coppa.getList().clear();
                    interact = true;
                }else{
                    msg.append("La coppa e' vuota. Non puoi purificare il sarcofago senza l'acqua.");
                    interact = true;
                }
            }
            
            boolean chiave = parserOutput.getInvObject() != null && parserOutput.getInvObject().getId() == 3;
            chiave = chiave || parserOutput.getObject() != null && parserOutput.getObject().getId() == 3;
            
            if(chiave && sarcofagoPresente){
                if(sarcofago.getState() != null && sarcofago.getState().equals("pulito")){
                    msg.append("La chiave dorata entra nella serratura,\n hai sentito uno scatto nel meccanismo,\n il sarcofago e' aperto basta solo aprirlo e scappare!!!");
                    sarcofago.setState("sbloccato");
                }else{
                    msg.append("Hai provato ad inserire la chiave,ma non riesci a trovare la fessura"
                            + "\nforse dovresti pulire il sarcofago");
                }
                interact = true;
            }
            
           
            if (!interact) {
                msg.append("Non ci sono oggetti utilizzabili qui.");
            }
        }
        return msg.toString();
    }
}   
