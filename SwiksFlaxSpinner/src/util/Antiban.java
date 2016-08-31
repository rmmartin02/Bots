package util;

import org.tbot.methods.*;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Widget;
import org.tbot.wrappers.WidgetChild;

import java.util.Arrays;

/**
 * Created by Russell on 2/6/2016.
 */
public class Antiban{

    private int perFiveMouse;
    private int perFiveHover;
    private int perFiveExamine;
    private int perFiveCamera;
    private static long doMoveMouse;
    private static long doHoverSkill;
    private static long doExamineObject;
    private static long doCamera;
    private int which;
    private Timer timer;
    private String name;

    public Antiban(int moveMouse, int hoverSkill, int camera) {
        perFiveMouse = moveMouse;
        perFiveHover = hoverSkill;
        //perFiveExamine = examineObject;
        perFiveCamera = camera;
        doMoveMouse = Random.nextInt(1,(600000/perFiveMouse));
        doHoverSkill = Random.nextInt(1,(600000/perFiveHover));
        //doExamineObject = Random.nextInt(1,(300000/perFiveExamine));
        doCamera = Random.nextInt(1,(600000/perFiveCamera));
        timer = new Timer();
    }

    public static long[] getDoTimes(){
        long[] actionTimes = {doMoveMouse, doHoverSkill,doCamera};
        return actionTimes;
    }

    public void randomMouse(){
        boolean done = false;
        while(!done) {
            Mouse.moveRandomly();
            Time.sleep(500, 1500);
            doMoveMouse += Random.nextInt(1, (int) (600000 / perFiveMouse));
            done = true;
        }
    }

   public void hoverSkill(){
        Widget skillTab = Widgets.getWidget(320);
        WidgetChild skillBox = skillTab.getChild(13);
        boolean done = false;
        while(!done){
            if(skillBox.isVisible()){
                Mouse.move(skillBox.getLocation());
                Time.sleep(1500,3000);
                randomMouse();
                Time.sleep(500,1500);
                done = true;
            }
            else {
                Widgets.getWidget(548,46).click();
                Time.sleep(350,800);
            }
        }
        doHoverSkill+= Random.nextInt(1,(int)(600000/perFiveHover));
    }

   public void examineGameObject(){
        final Filter<GameObject> thing_filter = new Filter<GameObject>() {

            @Override
            public boolean accept(GameObject thing) {
                if (thing.isOnScreen() && Arrays.asList(thing.getActions()).contains("Examine")){
                    return true;
                }
                return false;
            }
        };
        boolean done = false;
        while(!done) {
            GameObject[] things = GameObjects.getLoaded();
            things[Random.nextInt(0, things.length - 1)].interact("Examine");
            Time.sleep(2500,5000);
            doExamineObject += Random.nextInt(1, (int) (600000 / perFiveExamine));
            done = true;
        }
    }

    public void moveCameraRandomly(){
        boolean done = false;
        while(!done) {
            Camera.rotateRandomly();
            Time.sleep(500, 1500);
            doCamera += Random.nextInt(1, (int) (600000 / perFiveCamera));
            done = true;
        }
    }

    public void mouseExit(int side){
        if(side==0){
            Mouse.move(-1,Random.nextInt(5,500));
        }
        if(side==1){
            Mouse.move(Random.nextInt(5,750),503);
        }
        if(side==2){
            Mouse.move(765,Random.nextInt(5,500));
        }
        if(side==3){
            Mouse.move(Random.nextInt(5,750),-1);
        }
    }



    public boolean contains(String[] list, String word){

        for(int i=0; i<list.length; i++){
            if(list[i].equals(word)){
                return true;
            }
        }
        return false;
    }

    public boolean validate() {
        for(int i = 0; i<getDoTimes().length; i++){
            if (timer.getTimeElapsed()>getDoTimes()[i]){
                return true;
            }
        }
        return false;
    }

    public void execute() {
        which = -1;
        for(int i = 0; i<getDoTimes().length; i++){
            if (timer.getTimeElapsed()>getDoTimes()[i]){
                which = i;
            }
        }
        if(which == 0){
            name = "Random Mouse";
            randomMouse();
        }
        if(which == 1){
            name = "Hover Skill";
            hoverSkill();
        }
        if(which==2){
            name = "Move Camera";
            moveCameraRandomly();
        }
    }

    public int priority() {
        return 10;
    }

    public String getName() {
        return "Antiban (" + which + ")";
    }
}