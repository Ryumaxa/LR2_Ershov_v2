package org.example.LR2_V2;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class StartBehaviour extends WakerBehaviour {
    public StartBehaviour(Agent a, long wakeupDate) {
        super(a, wakeupDate);
    }

    @Override
    protected void onWake() {

        ACLMessage first_message = new ACLMessage(ACLMessage.INFORM);
        first_message.setConversationId("next_init");


        Random random = new Random(); // Определение начальных значений X и delta
        double min_x = 0;
        double max_x = 100;
        double x = random.nextDouble() * (max_x - min_x) + min_x;
        double delta = 1;

        first_message.addReceiver(new AID(getAgent().getLocalName(),false));
        String first_content = x + "," + delta;
        first_message.setContent(first_content);
        getAgent().send(first_message);
    }
}
