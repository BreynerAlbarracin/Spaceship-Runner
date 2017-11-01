/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Usaka Rokujou
 */
public class Phisycs {

    private BulletAppState bulletAppState;

    public Phisycs() {
        bulletAppState = new BulletAppState();
    }

    public Phisycs(Boolean debug) {
        bulletAppState = new BulletAppState();
        bulletAppState.setDebugEnabled(debug);
    }

    public void volverRigido(Spatial s, float mass) {
        CollisionShape mayaColision = CollisionShapeFactory.createDynamicMeshShape(s);
        RigidBodyControl cuerpoRigido = new RigidBodyControl(mayaColision, mass);

        s.addControl(cuerpoRigido);
        agregarElemento(cuerpoRigido);
    }

    public void agregarElemento(Object n) {
        bulletAppState.getPhysicsSpace().add(n);
    }

    public BulletAppState getBulletAppState() {
        return bulletAppState;
    }

    public void setBulletAppState(BulletAppState bulletAppState) {
        this.bulletAppState = bulletAppState;
    }

}
