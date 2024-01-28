package com.mygdx.game.Logic;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Objects.Item;
import com.mygdx.game.RoleCast.Buffoon;
import com.mygdx.game.RoleCast.NPC;
import com.mygdx.game.RoleCast.King;

public class MyContactListener implements ContactListener {

    private Fixture fa;
    private Fixture fb;
    private Buffoon buffoon;
    private int transparencyContact;

    public MyContactListener(Buffoon buffoon) {
        this.buffoon = buffoon;
        transparencyContact = 0;
    }

    @Override
    public void beginContact(Contact contact) {
        handleFixtures(contact);

        if (fa.getUserData() instanceof NPC || fb.getUserData() instanceof NPC) {
            buffoon.setTargetnpc((NPC) (fa.getUserData() instanceof NPC ? fa.getUserData() : fb.getUserData()));
        } else if (fa.getUserData() instanceof King || fb.getUserData() instanceof King) {
            buffoon.setKing((King) (fa.getUserData() instanceof King ? fa.getUserData() : fb.getUserData()));
        }else if (fa.getUserData() instanceof Item || fa.getUserData() instanceof Item) {
            if (fa.getUserData() instanceof Item) {
                ((Item) fa.getUserData()).setGrabbable(true);
            } else {
                ((Item) fb.getUserData()).setGrabbable(true);
            }
        } else if (fa.getUserData().equals("transparency") || fb.getUserData().equals("transparency")) {
            transparencyContact++;
            buffoon.setCurrAlpha(0.5f);
        }

    }

    @Override
    public void endContact(Contact contact) {
        handleFixtures(contact);

        if (fa.getUserData() instanceof NPC || fb.getUserData() instanceof NPC) {
            buffoon.setTargetnpc(null);
        } else if (fa.getUserData() instanceof King || fb.getUserData() instanceof King) {
            buffoon.setKing(null);
        } else if (fa.getUserData() instanceof Item || fa.getUserData() instanceof Item) {
            if (fa.getUserData() instanceof Item) {
                ((Item) fa.getUserData()).setGrabbable(false);
            } else {
                ((Item) fb.getUserData()).setGrabbable(false);
            }
        } else if (fa.getUserData().equals("transparency") || fb.getUserData().equals("transparency")) {
            transparencyContact--;
            if (transparencyContact == 0) buffoon.setCurrAlpha(1f);
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
