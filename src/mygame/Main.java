package mygame;

//import com.bulletphysics.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.controls.ActionListener;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    private Spatial spaceship;
    private Spatial plano;
    private Spatial tube;
    private Spatial tube2;
    private Spatial tube3;
    private Spatial tube4;
    private Geometry Gb1;
    private Box b;
    private Geometry Gb2;
    private Box b2;
    private RigidBodyControl landscape;
    private RigidBodyControl landscapeT;
    private RigidBodyControl landscapeT2;
    private RigidBodyControl landscapeT3;
    private RigidBodyControl landscapeT4;
    private RigidBodyControl landScape2;
    private RigidBodyControl landScape3;
    private BulletAppState bulletAppState;
    private CharacterControl player;
    private Vector3f walkDirection = new Vector3f();
    private Vector3f PlaneDirection = new Vector3f();
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    
    Factory factory;
    Controller controller;
    Phisycs phisycs;
    
    Node sceneNode;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.setDisplayStatView(false);
        app.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        settings.setResolution(800, 600);
        app.setSettings(settings);
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        factory = new Factory(assetManager);
        controller = new Controller(inputManager);
        phisycs = new Phisycs();
        
        agregarelemento(factory.createSky());
        controller.setUpKeys();
        
        flyCam.setMoveSpeed(100f);
        
        stateManager.attach(phisycs.getBulletAppState());
        
        agregarelemento(factory.cargarSpaceShip());
        agregarLuz(factory.crearLuz(ColorRGBA.White));
        crearPlayer();
        crearPlanos();
        crearObjetos();
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        MoverTerreno(tube, tube2, tube3, tube4);
        MoverObstaculo(Gb1, Gb2);
        
        camLeft = new Vector3f(1, 0, 0);
        camLeft.y = 0;
        walkDirection.set(0, 0, 0);
        
        if (controller.isLeft()) {
            walkDirection.addLocal(camLeft);
        }
        if (controller.isRight()) {
            walkDirection.addLocal(camLeft.negate());
        }
        
        Quaternion q = new Quaternion();
        q.fromAngles(0.1f, 0, 0);
        cam.setLocation(new Vector3f(player.getPhysicsLocation().x, player.getPhysicsLocation().y + 2.5f, player.getPhysicsLocation().z - 6f));
        cam.setRotation(q);
        player.setWalkDirection(walkDirection);
        
        if (spaceship.getLocalTranslation().z > tube2.getLocalTranslation().z) {
            
            tube.setLocalTranslation(0, -10, tube4.getLocalTranslation().getZ() + 35);
        }
        
        if (spaceship.getLocalTranslation().z > tube3.getLocalTranslation().z) {
            tube2.setLocalTranslation(0, -10, tube.getLocalTranslation().getZ() + 35);
        }
        
        if (spaceship.getLocalTranslation().z > tube4.getLocalTranslation().z) {
            tube3.setLocalTranslation(0, -10, tube2.getLocalTranslation().getZ() + 35);
        }
        
        if (spaceship.getLocalTranslation().z > tube.getLocalTranslation().z) {
            tube4.setLocalTranslation(0, -10, tube3.getLocalTranslation().getZ() + 35);
        }
        
        if (spaceship.getLocalTranslation().z > Gb1.getLocalTranslation().z) {
            Gb1.setLocalTranslation(-17 + (int) (Math.random() * ((17 + 17) + 1)), -8f, 100);
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.randomColor());
            Gb1.setMaterial(mat);
        }
        
        if (spaceship.getLocalTranslation().z > Gb2.getLocalTranslation().z) {
            Gb2.setLocalTranslation(-7 + (int) (Math.random() * ((7 + 7) + 1)), -8f, 100);
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.randomColor());
            Gb2.setMaterial(mat);
        }
        
        if (Gb1.getLocalTranslation().distance(Gb2.getLocalTranslation()) <= 3) {
            Gb1.setLocalTranslation(-17 + (int) (Math.random() * ((17 + 17) + 1)), Gb1.getLocalTranslation().y, Gb1.getLocalTranslation().z);
            Gb2.setLocalTranslation(-7 + (int) (Math.random() * ((7 + 7) + 1)), Gb2.getLocalTranslation().y, Gb2.getLocalTranslation().z);
        }
        
    }
    
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    private void MoverTerreno(Spatial terreno1, Spatial terreno2, Spatial terreno3, Spatial terreno4) {
        
        terreno1.setLocalTranslation(terreno1.getLocalTranslation().x, terreno1.getLocalTranslation().y, terreno1.getLocalTranslation().z - 0.2f);
        terreno2.setLocalTranslation(terreno2.getLocalTranslation().x, terreno2.getLocalTranslation().y, terreno2.getLocalTranslation().z - 0.2f);
        terreno3.setLocalTranslation(terreno3.getLocalTranslation().x, terreno3.getLocalTranslation().y, terreno3.getLocalTranslation().z - 0.2f);
        terreno4.setLocalTranslation(terreno4.getLocalTranslation().x, terreno4.getLocalTranslation().y, terreno4.getLocalTranslation().z - 0.2f);
    }
    
    private void MoverObstaculo(Geometry Geom, Geometry Geom2) {
        Geom.setLocalTranslation(Geom.getLocalTranslation().x, Geom.getLocalTranslation().y, Geom.getLocalTranslation().z - 0.1f);
        Geom2.setLocalTranslation(Geom2.getLocalTranslation().x, Geom2.getLocalTranslation().y, Geom2.getLocalTranslation().z - 0.1f);
    }
    
    private void crearPlanos() {
        tube = assetManager.loadModel("Models/plano/plano.j3o");
        tube.setLocalTranslation(0f, -10f, 0);
        tube.setLocalScale(2f);
        CollisionShape sceneShapeT = CollisionShapeFactory.createDynamicMeshShape(tube);
        landscapeT = new RigidBodyControl(sceneShapeT, 0f);
        tube.addControl(landscapeT);
        bulletAppState.getPhysicsSpace().add(landscapeT);
        rootNode.attachChild(tube);
        
        tube2 = assetManager.loadModel("Models/plano/plano.j3o");
        tube2.setLocalTranslation(0f, -10f, 35);
        tube2.setLocalScale(2f);
        CollisionShape sceneShapeT2 = CollisionShapeFactory.createDynamicMeshShape(tube2);
        landscapeT2 = new RigidBodyControl(sceneShapeT2, 0f);
        tube2.addControl(landscapeT2);
        bulletAppState.getPhysicsSpace().add(landscapeT2);
        rootNode.attachChild(tube2);
        
        tube3 = assetManager.loadModel("Models/plano/plano.j3o");
        tube3.setLocalTranslation(0f, -10f, 70);
        tube3.setLocalScale(2f);
        CollisionShape sceneShapeT3 = CollisionShapeFactory.createDynamicMeshShape(tube3);
        landscapeT3 = new RigidBodyControl(sceneShapeT3, 0f);
        tube3.addControl(landscapeT3);
        bulletAppState.getPhysicsSpace().add(landscapeT3);
        rootNode.attachChild(tube3);
        
        tube4 = assetManager.loadModel("Models/plano/plano.j3o");
        tube4.setLocalTranslation(0f, -10f, 105);
        tube4.setLocalScale(2f);
        CollisionShape sceneShapeT4 = CollisionShapeFactory.createDynamicMeshShape(tube4);
        landscapeT4 = new RigidBodyControl(sceneShapeT4, 0f);
        tube4.addControl(landscapeT4);
        bulletAppState.getPhysicsSpace().add(landscapeT4);
        rootNode.attachChild(tube4);
        
    }
    
    private void crearObjetos() {
        b = new Box(2, 2, 2);
        Gb1 = new Geometry("Box", b);
        Gb1.setLocalTranslation(0, -8f, 100);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        Gb1.setMaterial(mat);
        CollisionShape sceneShape1 = CollisionShapeFactory.createBoxShape(Gb1); //aca se hace colisionable
        landScape2 = new RigidBodyControl(sceneShape1, 0f); // aca le añadimos peso
        Gb1.addControl(landScape2);
        bulletAppState.getPhysicsSpace().add(landScape2);
        rootNode.attachChild(Gb1);
        
        b2 = new Box(2, 2, 2);
        Gb2 = new Geometry("Box", b2);
        Gb2.setLocalTranslation(5, -8f, 100);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Blue);
        Gb2.setMaterial(mat2);
        CollisionShape sceneShape2 = CollisionShapeFactory.createBoxShape(Gb2); //aca se hace colisionable
        landScape3 = new RigidBodyControl(sceneShape2, 0f); // aca le añadimos peso
        Gb2.addControl(landScape3);
        bulletAppState.getPhysicsSpace().add(landScape3);
        rootNode.attachChild(Gb2);
    }
    
    private void crearPlayer() {
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(0.4f, 0.4f);
        player = new CharacterControl(capsuleShape, 0.05f);
        spaceship.addControl(player);
        player.setJumpSpeed(20);
        player.setFallSpeed(30);
        player.setPhysicsLocation(new Vector3f(0, -10f, 0));
        bulletAppState.getPhysicsSpace().add(player);
    }
    
    private void agregarelemento(Spatial n) {
        rootNode.attachChild(n);
    }
    
    private void agregarLuz(AmbientLight am) {
        rootNode.addLight(am);
    }
}
