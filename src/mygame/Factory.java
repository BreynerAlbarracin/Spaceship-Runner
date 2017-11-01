/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

/**
 *
 * @author Usaka Rokujou
 */
public class Factory {

    AssetManager assetManager;

    public Factory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Spatial createSky() {
        Texture west, east, north, south, up, down;
        west = assetManager.loadTexture("Textures/skytex/west.png");
        east = assetManager.loadTexture("Textures/skytex/east.png");
        north = assetManager.loadTexture("Textures/skytex/north.png");
        south = assetManager.loadTexture("Textures/skytex/south.png");
        up = assetManager.loadTexture("Textures/skytex/up.png");
        down = assetManager.loadTexture("Textures/skytex/down.png");
        return SkyFactory.createSky(assetManager, west, east, north, south, up, down);
    }

    public Spatial cargarSpaceShip() {
        Spatial spaceship = assetManager.loadModel("Models/lpSpaceShip/lpSpaceShip.j3o");
        spaceship.setName("nave");

        spaceship.setLocalTranslation(0f, -9f, 0f);
        spaceship.setLocalScale(1);

        return spaceship;
    }

    public AmbientLight crearLuz(ColorRGBA color) {
        return new AmbientLight(color);
    }

    public Spatial crearPlano(Vector3f v) {
        Spatial plano = assetManager.loadModel("Models/plano/plano.j3o");
        plano.setLocalTranslation(v);
        plano.setLocalScale(2f);
        return plano;
    }

    public Geometry crearObjeto(Vector3f v, ColorRGBA color) {
        Box b = new Box(2, 2, 2);
        Geometry geometry = new Geometry("Box", b);
        geometry.setLocalTranslation(v);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);  
        geometry.setMaterial(mat);
        return geometry;
    }
}
