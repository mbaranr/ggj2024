package com.mygdx.game.Logic;

import com.badlogic.gdx.physics.box2d.*;

public class MyContactListener implements ContactListener {

    private Fixture fa;
    private Fixture fb;

    public MyContactListener() {
    }

    @Override
    public void beginContact(Contact contact) {
        handleFixtures(contact);
        if ( fa.getUserData().equals("item") || fb.getUserData().equals("item")) {
            System.out.println("here");
        }
    }

    @Override
    public void endContact(Contact contact) {
        handleFixtures(contact);
        if ( fa.getUserData().equals("item") || fb.getUserData().equals("item")) {
            System.out.println("here");
        }
    }

    public boolean handleFixtures(Contact contact) {
        fa = contact.getFixtureA();
        fb = contact.getFixtureB();

        return fa == null || fb == null;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
