package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {
    private final float DEGTORAD = 0.0174533f;
    public static final int STEEL = 0;
    public static final int WOOD = 1;
    public static final int RUBBER = 2;
    public static final int STONE = 3;

    private static BodyFactory thisInstance = null;
    private World world;

    private BodyFactory(World world) {
        this.world = world;
    }

    public static BodyFactory getInstance(World world) {
        if (thisInstance == null) thisInstance = new BodyFactory(world);
        return thisInstance;
    }

    static public FixtureDef makeFixture(int material, Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        switch (material) {
            case 0:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.3f;
                fixtureDef.restitution = 0.1f;
                break;
            case 1:
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0.7f;
                fixtureDef.restitution = 0.3f;
                break;
            case 2:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0f;
                fixtureDef.restitution = 1f;
                break;
            case 3:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.9f;
                fixtureDef.restitution = 0.01f;
            default:
                fixtureDef.density = 7f;
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0.3f;
        }
        return fixtureDef;
    }

     static public BodyDef makeBody(float posX, float posY, BodyType bodyType, boolean fixedRotation) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.x = posX;
        bodyDef.position.y = posY;
        bodyDef.fixedRotation = fixedRotation;

        return bodyDef;
    }

    public Body makeRectangleBody(float posX, float posY, float width, float height, int material, BodyType bodyType) {
        return makeRectangleBody(posX, posY, width, height, material, bodyType, false);
    }

    public Body makeRectangleBody(float posX, float posY, float width, float height, int material, BodyType bodyType, boolean fixedRotation) {
        Body body = world.createBody(makeBody(posX, posY, bodyType, fixedRotation));

        PolygonShape polygon = new PolygonShape();
        polygon.setAsBox(width /2, height /2);

        body.createFixture(makeFixture(material, polygon));

        polygon.dispose();

        return body;
    }

    public Body makeCircleBody(float posX, float posY, float radius, int material, BodyType bodyType) {
        return makeCircleBody(posX, posY, radius, material, bodyType, false);
    }

    public Body makeCircleBody(float posX, float posY, float radius, int material, BodyType bodyType, boolean fixedRotation) {
        Body body = world.createBody(makeBody(posX, posY, bodyType, fixedRotation));

        CircleShape circle = new CircleShape();
        circle.setRadius(radius /2);

        body.createFixture(makeFixture(material, circle));

        circle.dispose();

        return body;
    }

    public Body makePolygonBody(Vector2[] vertices, float posX, float posY, int material, BodyType bodyType, boolean fixedRotation) {
        Body body = world.createBody(makeBody(posX, posY, bodyType, fixedRotation));

        PolygonShape polygon = new PolygonShape();
        polygon.set(vertices);

        body.createFixture(makeFixture(material, polygon));

        polygon.dispose();

        return body;
    }

    public void makeConeSensor(Body body, float size) {
        FixtureDef fixtureDef = new FixtureDef();

        PolygonShape polygon = new PolygonShape();

        float radius = size;
        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(0,0);
        for (int i=2; i<6; i++) {
            float angle = (float)(i / 6.0 * 145 * DEGTORAD);
            vertices[i-1] = new Vector2( radius * ((float)Math.cos(angle)), radius * ((float)Math.sin(angle)));
            polygon.set(vertices);
            fixtureDef.shape = polygon;

            body.createFixture(fixtureDef);

            polygon.dispose();
        }
    }

    public void makeAllFixturesSensors(Body body) {
        for (Fixture fixture : body.getFixtureList()) {
            fixture.setSensor(true);
        }
    }
}
