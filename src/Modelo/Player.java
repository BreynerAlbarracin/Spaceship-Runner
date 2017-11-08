package Modelo;

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
    private int vida;

    public Player() {
        CapsuleCollisionShape shape = new CapsuleCollisionShape(1.5f, 0);
        player = new CharacterControl(shape, 0f);
        player.setJumpSpeed(20);
        player.setFallSpeed(30);
        player.setPhysicsLocation(new Vector3f(0, -9f, 0));
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

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
}
