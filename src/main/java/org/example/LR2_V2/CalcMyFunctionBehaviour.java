package org.example.LR2_V2;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CalcMyFunctionBehaviour extends Behaviour {

    public void onStart() {

    }

    @Override
    public void action() {
        ACLMessage message_x = getAgent().receive(MessageTemplate.MatchConversationId("for_y_count"));
        if (message_x != null) {
            String content_x = message_x.getContent(); // Получение строки со значениями x, delta и ее разбиение
            String[] values = content_x.split(",");
            double x = Double.parseDouble(values[0]);
            double delta = Double.parseDouble(values[1]);

            double y1 = 0;
            double y = 0;
            double y2 = 0;

            if (getAgent().getLocalName().equals("FA1")) { // Вычисление значений Y каждым агентом в зависимости от его функции
                y1 = FAMeth.FA1Count(x-delta);
                y = FAMeth.FA1Count(x);
                y2 = FAMeth.FA1Count(x+delta);
            }
            else if (getAgent().getLocalName().equals("FA2")) {
                y1 = FAMeth.FA2Count(x-delta);
                y = FAMeth.FA2Count(x);
                y2 = FAMeth.FA2Count(x+delta);
            }
            else if (getAgent().getLocalName().equals("FA3")) {
                y1 = FAMeth.FA3Count(x-delta);
                y = FAMeth.FA3Count(x);
                y2 = FAMeth.FA3Count(x+delta);
            }
            ACLMessage message_y = new ACLMessage(ACLMessage.INFORM); // Ответное сообщение со значениями y1, y, y2
            message_y.setConversationId("counted_y");
            AID receiver = new AID(message_x.getSender().getLocalName(), false);
            message_y.addReceiver(receiver);
            String content_y = y1 + "," + y + "," + y2;
            message_y.setContent(content_y);
            getAgent().send(message_y);

        } else {
            block();
        }


    }

    @Override
    public boolean done() {
        return false; // поведение никогда не заканчивается
    }
}
