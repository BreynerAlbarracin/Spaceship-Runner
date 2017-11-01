/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Usaka Rokujou
 */
public class Player {

    private CharacterControl player;

    public Player() {
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(0.4f, 0.4f);
        player = new CharacterControl(capsuleShape, 0.05f);

        player.setJumpSpeed(20);
        player.setFallSpeed(30);
        player.setPhysicsLocation(new Vector3f(0, -10f, 0));
    }

    public void agregarSpatial(Spatial s) {
        s.addControl(player);
    }

    public CharacterControl getPlayer() {
        return player;
    }

    public void setPlayer(CharacterControl player) {
        this.player = player;
    }

}
