package org.example.LR2_V2;

import jade.core.Agent;


public class FunctionAgent extends Agent {

    protected void setup() {
        System.out.println(getLocalName() + " : Я успешно родился");

        this.addBehaviour(new CalcMyFunctionBehaviour());
        this.addBehaviour(new CathInitiative());

        if (this.getLocalName().equals("FA1")) {
            this.addBehaviour(new StartBehaviour(this, 3000));
        }

    }
    public static void main(String[] args) {

    }
}
