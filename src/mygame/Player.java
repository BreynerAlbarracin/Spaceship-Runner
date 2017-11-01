/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;

/**
 *
 * @author Usaka Rokujou
 */
public class Player {
    
    CharacterControl player;

    public Player() {
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(0.4f, 0.4f);
        player = new CharacterControl(capsuleShape, 0.05f);
        spaceship.addControl(player);
        player.setJumpSpeed(20);
        player.setFallSpeed(30);
        player.setPhysicsLocation(new Vector3f(0, -10f, 0));
        bulletAppState.getPhysicsSpace().add(player);
    }
}
