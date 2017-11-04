/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.jme3.asset.AssetManager;
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
        AmbientLight al = new AmbientLight();
        al.setColor(color);
        return al;
    }

    public Geometry crearPlano(Vector3f v) {
        Box b = new Box(18, 0.1f, 20);
        Geometry g = new Geometry("Piso", b);
        g.setLocalTranslation(v);
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setTexture("ColorMap", assetManager.loadTexture("Models/plano/tubeTexture.png"));
        g.setMaterial(m);
        return g;
    }

    public Geometry crearObjeto(Vector3f v, ColorRGBA color) {
        Box b = new Box(2, 2, 2);
        Geometry geometry = new Geometry("Enemigo", b);
        geometry.setLocalTranslation(v);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geometry.setMaterial(mat);
        return geometry;
    }
}
