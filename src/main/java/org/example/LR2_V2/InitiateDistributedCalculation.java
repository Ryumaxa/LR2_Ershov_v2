package org.example.LR2_V2;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class InitiateDistributedCalculation extends Behaviour {

    double x;
    double delta;
    double y1_fa1 = 0;
    double y_fa1 = 0;
    double y2_fa1 = 0;
    double y1_fa2 = 0;
    double y_fa2 = 0;
    double y2_fa2 = 0;
    double y1_fa3 = 0;
    double y_fa3 = 0;
    double y2_fa3 = 0;
    double y1 = 0;
    double y = 0;
    double y2 = 0;

    public InitiateDistributedCalculation(double x, double delta) {
        this.x = x;
        this.delta = delta;
    }


    @Override
    public void onStart() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM); //Отправка начальных значений x и delta
        msg.setConversationId("for_y_count");
        String content_of_msg = x + "," + delta;
        msg.setContent(content_of_msg);
        msg.addReceiver(new AID("FA1", false));
        msg.addReceiver(new AID("FA2", false));
        msg.addReceiver(new AID("FA3", false));
        getAgent().send(msg);
    }

    int i = 0;


    @Override
    public void action() {
        ACLMessage msg_y = getAgent().receive(MessageTemplate.MatchConversationId("counted_y"));
        if (msg_y != null) {
            System.out.println(getAgent().getLocalName() + " получил сообщение от " + msg_y.getSender().getLocalName() + " : " + msg_y.getContent());
            String content_x = msg_y.getContent(); // Получение строки со значениями y1, y, y2 и ее разбиение
            String[] values = content_x.split(",");

            if (msg_y.getSender().getLocalName().equals("FA1")) { // Получение значений y от всех агентов
                y1_fa1 = Double.parseDouble(values[0]);
                y_fa1 = Double.parseDouble(values[1]);
                y2_fa1 = Double.parseDouble(values[2]);
                i++;
            }
            if (msg_y.getSender().getLocalName().equals("FA2")) {
                y1_fa2 = Double.parseDouble(values[0]);
                y_fa2 = Double.parseDouble(values[1]);
                y2_fa2 = Double.parseDouble(values[2]);
                i++;
            }
            if (msg_y.getSender().getLocalName().equals("FA3")) {
                y1_fa3 = Double.parseDouble(values[0]);
                y_fa3 = Double.parseDouble(values[1]);
                y2_fa3 = Double.parseDouble(values[2]);
                i++;
            }

            y1 = y1_fa1 + y1_fa2 + y1_fa3;
            y = y_fa1 + y_fa2 + y_fa3;
            y2 = y2_fa1 + y2_fa2 + y2_fa3;

            double maximum = Math.max(y1, Math.max(y, y2));

            if (maximum == y1) {
                x -= delta;
            }
            if (maximum == y) {
                delta *= 0.5;
            }
            if (maximum == y2) {
                x += delta;
            }
            y = maximum;

        }



    }

    @Override
    public boolean done() {
        return i >= 3;
    }

    @Override
    public int onEnd() {
        if (delta <= 0.01) {
            System.out.println("delta достигла значения 0.01 при X = " + x + "; Y = " + y);
        } else {
            ACLMessage mes = new ACLMessage(ACLMessage.INFORM); // Создание сообщения для нового инициатора
            mes.setConversationId("next_init");
            AID receiver = new AID("", false);
            if (getAgent().getLocalName().equals("FA1")) {
                receiver = new AID("FA2", false);
            } else if (getAgent().getLocalName().equals("FA2")) {
                receiver = new AID("FA3", false);
            } else if (getAgent().getLocalName().equals("FA3")) {
                receiver = new AID("FA1", false);
            }
            mes.addReceiver(receiver);
            String content_y = x + "," + delta;
            mes.setContent(content_y);
            getAgent().send(mes);
        }
        return 0;
    }
}
