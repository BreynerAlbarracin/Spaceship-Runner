package Modelo;

import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Spatial;

/**
 *
 * @author Usaka Rokujou
 */
public class Phisycs {
    
    private BulletAppState bulletAppState;
    
    public Phisycs(AppStateManager asm) {
        bulletAppState = new BulletAppState();
        asm.attach(bulletAppState);
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
    
    public void isDebug(Boolean bl) {
        bulletAppState.setDebugEnabled(bl);
    }
}
