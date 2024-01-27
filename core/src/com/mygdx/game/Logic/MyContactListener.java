package com.mygdx.game.Logic;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Objects.Item;

public class MyContactListener implements ContactListener {

    private Fixture fa;
    private Fixture fb;
    private Item item;
    private ArrayList<Item> itemList;

    public MyContactListener(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public void beginContact(Contact contact) {
        handleFixtures(contact);

        for(Item item : itemList) {

            if (fa.getUserData().equals("item" + item.getItemID()) || !fb.getUserData().equals("item" + item.getItemID())) {
                item.setGrabbable(true);
                System.out.println("Player on top of Item");
            }

        }
    }

    @Override
    public void endContact(Contact contact) {
        handleFixtures(contact);

        for(Item item : itemList) {

            if (fa.getUserData().equals("item" + item.getItemID()) || !fb.getUserData().equals("item" + item.getItemID())) {
                item.setGrabbable(false);
                System.out.println("Player not on range of Item anymore");
            }

        }
    }

    public boolean handleFixtures(Contact contact) {
        fa = contact.getFixtureA();
        fb = contact.getFixtureB();

        return fa == null || fb == null;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {}
}
