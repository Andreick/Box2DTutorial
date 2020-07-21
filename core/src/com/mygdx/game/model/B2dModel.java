package com.mygdx.game.model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.controller.B2dAssetManager;
import com.mygdx.game.controller.KeyboardController;

public class B2dModel {
    public static final int PING_SOUND = 0;
    public static final int BOING_SOUND = 1;

    public World world;
    private Body bodyD;
    private Body bodyS;
    private Body bodyK;

    private Viewport viewport;
    private KeyboardController controller;
    private B2dAssetManager astMng;

    public boolean isSwimming = false;
    public Body player;

    private Sound ping;
    private Sound boing;

    public B2dModel(KeyboardController controller, Viewport viewport, B2dAssetManager assetManager) {
        this.viewport = viewport;
        this.controller = controller;
        astMng = assetManager;

        Box2D.init();
        world = new World(new Vector2(0, -10f), true);
        world.setContactListener(new B2dContactListener(this));

        createFloor();
        //createObject();
        //createMovingObject();

        astMng.queueAddSounds();
        astMng.manager.finishLoading();

        ping = astMng.manager.get("sounds/ping.wav");
        boing = astMng.manager.get("sounds/boing.wav");

        BodyFactory bodyFactory = BodyFactory.getInstance(world);

        player = bodyFactory.makeRectangleBody(16, 13, 2, 2, BodyFactory.RUBBER, BodyType.DynamicBody);
        Body water = bodyFactory.makeRectangleBody(16, 7.5f, 33, 9, BodyFactory.RUBBER, BodyType.StaticBody);

        water.setUserData("IAMTHESEA");
        bodyFactory.makeAllFixturesSensors(water);
    }

    public void logicStep(float delta) {
        if (controller.up) {
            player.applyForceToCenter(0, 10, true);
        }
        if (controller.left) {
            player.applyForceToCenter(-10, 0, true);
        }
        if (controller.down) {
            player.applyForceToCenter(0, -10, true);
        }
        if (controller.right) {
            player.applyForceToCenter(10, 0, true);
        }

        if (controller.buttonLeft && pointIntersectsBody(player,controller.mouseLocation)) {
            System.out.println("Player was clicked");
        }

        /*if (isSwimming) {
            player.applyForceToCenter(0, 40, true);
        }*/

        world.step(delta, 3, 3);
    }

    public boolean pointIntersectsBody(Body body, Vector2 mouseLocation) {
        viewport.unproject(mouseLocation);

        for (Fixture fixture : body.getFixtureList()) {
            if (fixture.testPoint(mouseLocation.x, mouseLocation.y)) {
                return true;
            }
        }

        return false;
    }

    private void createObject() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(0, 0);

        bodyD = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10,10);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        bodyD.createFixture(shape, 0.0f);

        shape.dispose();
    }

    private void createFloor() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(16, 2);

        bodyS = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(17,1);

        bodyS.createFixture(shape, 0.0f);

        shape.dispose();
    }

    private void createMovingObject() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.KinematicBody;
        bodyDef.position.set(0, -12);

        bodyK = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        bodyK.createFixture(shape, 0.0f);

        shape.dispose();

        bodyK.setLinearVelocity(0, 0.75f);
    }

    public void playSound(int sound) {
        switch (sound) {
            case PING_SOUND:
                ping.play();
                break;
            case BOING_SOUND:
                boing.play();
                break;
        }
    }
}
