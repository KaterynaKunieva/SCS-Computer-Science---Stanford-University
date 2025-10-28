package com.shpp.p2p.cs.kkunieva.assignment1;

public class Reactor extends SuperKarel {
    @Override
    public void run() throws Exception{
        pause();
        cleanReactor();
        System.out.println("End");
    }

    public void cleanReactor() throws Exception{
        cleanColumn();
        while(frontIsClear()){
            move();
            cleanReactor();
        }
    }

    public void cleanCell() throws Exception{
        move();
        pickAllBeepers();
        turnAround();
        move();
    }

    public void cleanColumn() throws Exception{
        if(noBeepersPresent()){
            if(leftIsClear()){
                turnLeft();
                cleanCell();
                turnLeft();
            }
            if(rightIsClear()){
                turnRight();
                cleanCell();
                turnRight();
            }
        }
    }

}