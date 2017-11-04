package Modelo;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
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
        BoxCollisionShape shape = new BoxCollisionShape(new Vector3f(1,1,1));
        player = new CharacterControl(shape, 0f);

        player.setJumpSpeed(20);
        player.setFallSpeed(30);
        player.setPhysicsLocation(new Vector3f(0, 0f, 0));
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
