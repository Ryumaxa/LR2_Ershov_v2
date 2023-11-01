package org.example.LR2_V2;


import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CathInitiative extends Behaviour {

    @Override
    public void onStart() {

    }

    @Override
    public void action() {
        ACLMessage msg = getAgent().receive(MessageTemplate.MatchConversationId("next_init"));
        if (msg != null) {
            String content_x = msg.getContent(); // Получение строки со значениями x, delta и ее разбиение
            String[] values = content_x.split(",");
            double x = Double.parseDouble(values[0]);
            double delta = Double.parseDouble(values[1]);

            getAgent().addBehaviour(new InitiateDistributedCalculation(x, delta));
        } else {
            block();
        }

    }

    @Override
    public boolean done() {
        return false;
    }
}
