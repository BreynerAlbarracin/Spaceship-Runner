package Modelo;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication implements ScreenController {

    //<editor-fold defaultstate="collapsed" desc="Variables del juego">
    private Spatial terreno1;
    private Spatial terreno2;
    private Spatial terreno3;
    private Spatial terreno4;
    private Geometry Gb1;
    private Geometry Gb2;
    private CharacterControl player;
    private Vector3f walkDirection = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private float speed;
    private Nifty nifty;
    Factory factory;
    Controller controller;
    Phisycs phisycs;
    Node sceneNode;
    long sysTime;
    Player jugador;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="El main">
    public static void main(String[] args) {
        Main app = new Main();
        app.setDisplayStatView(false);
        app.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        settings.setResolution(800, 600);
        app.setSettings(settings);
        app.start();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Init">
    @Override
    public void simpleInitApp() {

        speed = 0.1f;
        sysTime = System.currentTimeMillis();

        factory = new Factory(assetManager);
        controller = new Controller(inputManager);
        phisycs = new Phisycs(stateManager);
        //phisycs.isDebug(true);

        controller.setUpKeys();
        flyCam.setMoveSpeed(100f);
        agregarelemento(factory.cargarSpaceShip());
        agregarLuz(factory.crearLuz(ColorRGBA.White));
        agregarelemento(factory.createSky());

        crearPlayer();
        crearPlanos();
        crearCubos();
        setGUI();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void simpleUpdate(float tpf) {
        /**
         * Corregir forma del movimiento del terreno y del personaje El
         * personaje puede ser enviado a la cordenada 0,0,0 cuando llegue a un
         * punto Al ser un terreno infinito el jugador no se percatara de lo
         * ocurrido El terreno pueden ser solo dos planos muuy largos, no
         * necesariamente 4
         */
        long currentTime = System.currentTimeMillis();

        if (currentTime - sysTime > 1000) {
            sysTime = currentTime;
            speed += 0.001f;
        }

        TransladarTerreno();
        MoverTerreno(speed);
        MoverObstaculo(speed);
        ConfigurarCamara();
        MoverPersonaje(1f);
        detectarColision();
        lifeUI();

        if (player.getPhysicsLocation().z > Gb1.getLocalTranslation().z) {
            Vector3f vector = new Vector3f(-15 + (int) (Math.random() * ((15 + 15) + 1)), -8f, 100);
            Gb1.setLocalTranslation(vector);
            Gb1.getControl(RigidBodyControl.class).setPhysicsLocation(vector);
            Gb1.getMaterial().setColor("Color", ColorRGBA.randomColor());
        }

        if (player.getPhysicsLocation().z > Gb2.getLocalTranslation().z) {
            Vector3f vector2 = new Vector3f(-15 + (int) (Math.random() * ((15 + 15) + 1)), -8f, 100);
            Gb2.setLocalTranslation(vector2);
            Gb2.getControl(RigidBodyControl.class).setPhysicsLocation(vector2);
            Gb2.getMaterial().setColor("Color", ColorRGBA.randomColor());
        }

        if (Gb1.getLocalTranslation().distance(Gb2.getLocalTranslation()) <= 3) {
            Gb1.setLocalTranslation(Gb1.getLocalTranslation().x - 17 + (int) (Math.random() * ((17 + 17) + 1)), Gb1.getLocalTranslation().y, Gb1.getLocalTranslation().z);
            Gb2.setLocalTranslation(Gb2.getLocalTranslation().x - 7 + (int) (Math.random() * ((7 + 7) + 1)), Gb2.getLocalTranslation().y, Gb2.getLocalTranslation().z);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Mover escenario">
    private void MoverTerreno(float speed) {
        terreno1.setLocalTranslation(terreno1.getLocalTranslation().x, terreno1.getLocalTranslation().y, terreno1.getLocalTranslation().z - speed);
        terreno2.setLocalTranslation(terreno2.getLocalTranslation().x, terreno2.getLocalTranslation().y, terreno2.getLocalTranslation().z - speed);
        terreno3.setLocalTranslation(terreno3.getLocalTranslation().x, terreno3.getLocalTranslation().y, terreno3.getLocalTranslation().z - speed);
        terreno4.setLocalTranslation(terreno4.getLocalTranslation().x, terreno4.getLocalTranslation().y, terreno4.getLocalTranslation().z - speed);
    }

    private void MoverObstaculo(float speed) {
        Gb1.setLocalTranslation(Gb1.getLocalTranslation().x, Gb1.getLocalTranslation().y, Gb1.getLocalTranslation().z - speed);
        Gb1.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(Gb1.getLocalTranslation().x, Gb1.getLocalTranslation().y, Gb1.getLocalTranslation().z - speed));

        Gb2.setLocalTranslation(Gb2.getLocalTranslation().x, Gb2.getLocalTranslation().y, Gb2.getLocalTranslation().z - speed);
        Gb2.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(Gb2.getLocalTranslation().x, Gb2.getLocalTranslation().y, Gb2.getLocalTranslation().z - speed));
    }

    private void ConfigurarCamara() {
        Quaternion q = new Quaternion();
        q.fromAngles(0.1f, 0, 0);
        cam.setLocation(new Vector3f(player.getPhysicsLocation().x, player.getPhysicsLocation().y + 2.5f, player.getPhysicsLocation().z - 6f));
        cam.setRotation(q);
    }

    private void TransladarTerreno() {
        if (player.getPhysicsLocation().z > terreno2.getLocalTranslation().z) {
            terreno1.setLocalTranslation(0, -10, terreno4.getLocalTranslation().getZ() + 35);
        }

        if (player.getPhysicsLocation().z > terreno3.getLocalTranslation().z) {
            terreno2.setLocalTranslation(0, -10, terreno1.getLocalTranslation().getZ() + 35);
        }

        if (player.getPhysicsLocation().z > terreno4.getLocalTranslation().z) {
            terreno3.setLocalTranslation(0, -10, terreno2.getLocalTranslation().getZ() + 35);
        }

        if (player.getPhysicsLocation().z > terreno1.getLocalTranslation().z) {
            terreno4.setLocalTranslation(0, -10, terreno3.getLocalTranslation().getZ() + 35);
        }
    }

    private void MoverPersonaje(float speed) {
        camLeft = new Vector3f(speed, 0, 0);
        walkDirection.set(0, 0, 0);

        if (controller.isLeft() && player.getPhysicsLocation().x < 15) {
            walkDirection.addLocal(camLeft);
        }
        if (controller.isRight() && player.getPhysicsLocation().x > -15) {
            walkDirection.addLocal(camLeft.negate());
        }
        player.setWalkDirection(walkDirection);
        Vector3f v = player.getPhysicsLocation();

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Colisiones">
    private void detectarColision() {
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        CollisionResults cs = new CollisionResults();
        CollisionResults cs2 = new CollisionResults();
        Gb1.collideWith(ray, cs);
        Gb2.collideWith(ray, cs2);
        if (cs.size() > 0) {
            if (jugador.getPlayer().getPhysicsLocation().distance(Gb1.getLocalTranslation()) <= 2) {
                Gb1.setLocalTranslation(-15 + (int) (Math.random() * ((15 + 15) + 1)), -8, 100);
                Gb1.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(Gb1.getLocalTranslation().x, -8, 100));
                jugador.setVida(jugador.getVida() - 1);
            }
        } else if (cs2.size() > 0) {
            if (jugador.getPlayer().getPhysicsLocation().distance(Gb2.getLocalTranslation()) <= 2) {
                Gb2.setLocalTranslation(-15 + (int) (Math.random() * ((15 + 15) + 1)), -8, 100);
                Gb2.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(Gb2.getLocalTranslation().x, -8, 100));
                jugador.setVida(jugador.getVida() - 1);
                System.out.println(jugador.getVida());
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Crear Objetos">
    private void crearPlanos() {
        Vector3f[] posiciones = {new Vector3f(0f, -10f, 0),
            new Vector3f(0f, -10f, 35),
            new Vector3f(0f, -10f, 70),
            new Vector3f(0f, -10f, 105)};

        terreno1 = factory.crearPlano(posiciones[0]);
        phisycs.volverRigido(terreno1, 0f);
        agregarelemento(terreno1);

        terreno2 = factory.crearPlano(posiciones[1]);
        phisycs.volverRigido(terreno2, 0f);
        agregarelemento(terreno2);

        terreno3 = factory.crearPlano(posiciones[2]);
        phisycs.volverRigido(terreno3, 0f);
        agregarelemento(terreno3);

        terreno4 = factory.crearPlano(posiciones[3]);
        phisycs.volverRigido(terreno4, 0f);
        agregarelemento(terreno4);
    }

    private void crearCubos() {
        Gb1 = factory.crearObjeto(new Vector3f(0, -8f, 100), ColorRGBA.randomColor());
        phisycs.volverRigido(Gb1, 0f);
        agregarelemento(Gb1);

        Gb2 = factory.crearObjeto(new Vector3f(5, -8f, 100), ColorRGBA.randomColor());
        phisycs.volverRigido(Gb2, 0f);
        agregarelemento(Gb2);
    }

    private void crearPlayer() {
        jugador = new Player();
        jugador.agregarSpatial(obtenerSpatial("nave"));
        phisycs.agregarElemento(jugador.getPlayer());
        player = jugador.getPlayer();
        jugador.setVida(4);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos del entorno">
    private void agregarelemento(Spatial n) {
        rootNode.attachChild(n);
    }

    private void agregarLuz(AmbientLight am) {
        rootNode.addLight(am);
    }

    private Spatial obtenerSpatial(String name) {
        return rootNode.getChild(name);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Interfaz">
    private void setGUI() {
        flyCam.setEnabled(false);
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, viewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/GUI.xml", "GScreen0", this);
        guiViewPort.addProcessor(niftyDisplay);
        speed = 0;
    }

    private void lifeUI() {
        if (jugador.getVida() == 3) {
            nifty.gotoScreen("GScreen2");
        } else if (jugador.getVida() == 2) {
            nifty.gotoScreen("GScreen3");
        } else if (jugador.getVida() == 1) {
            nifty.gotoScreen("GScreen4");
        } else if (jugador.getVida() == 0) {
            nifty.gotoScreen("GScreen5");
            mouseInput.setCursorVisible(true);
            speed = 0;
        } else if (nifty.getCurrentScreen().equals("GScreen0")) {
            speed = 0;
        }
    }

    @NiftyEventSubscriber(id = "GButton3")
    public void Iniciar(final String id, final ButtonClickedEvent event) {
        nifty.gotoScreen("GScreen1");
        mouseInput.setCursorVisible(false);
        speed = 0.1f;
    }

    @NiftyEventSubscriber(id = "GButton2")
    public void Salir(final String id, final ButtonClickedEvent event) {
        System.exit(0);
    }

    @NiftyEventSubscriber(id = "GButton4")
    public void Reiniciar(final String id, final ButtonClickedEvent event) {
        nifty.gotoScreen("GScreen0");
        mouseInput.setCursorVisible(true);
        jugador.setVida(4);
        Gb1.setLocalTranslation(-15 + (int) (Math.random() * ((15 + 15) + 1)), -8, 100);
        Gb1.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(Gb1.getLocalTranslation().x, -8, 100));
        Gb2.setLocalTranslation(-15 + (int) (Math.random() * ((15 + 15) + 1)), -8, 100);
        Gb2.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(Gb2.getLocalTranslation().x, -8, 100));
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Abstractos">
    @Override
    public void bind(Nifty nifty, Screen screen) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onStartScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onEndScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //</editor-fold>    
}
