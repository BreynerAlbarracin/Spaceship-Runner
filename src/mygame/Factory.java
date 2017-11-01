/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;
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

        spaceship.setLocalTranslation(0f, -9f, 0f);
        spaceship.setLocalScale(1);

        return spaceship;
    }

    public AmbientLight crearLuz(ColorRGBA color) {
        return new AmbientLight(color);
    }
}
