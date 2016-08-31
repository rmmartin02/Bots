package barbarianfisher;

import org.tbot.methods.*;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Widget;
import org.tbot.wrappers.WidgetChild;
/**
 * Created by Russell on 2/6/2016.
 */
public class Antiban {

    private int perFiveMouse;
    private int perFiveHover;
    private int perFiveExamine;
    private long doMoveMouse;
    private long doHoverSkill;
    private long doExamineObject;

    public Antiban(int moveMouse, int hoverSkill, int examineObject) {
        perFiveMouse = moveMouse;
        perFiveHover = hoverSkill;
        perFiveExamine = examineObject;
        doMoveMouse = Random.nextInt(1,(300000/perFiveMouse));
        doHoverSkill = Random.nextInt(1,(300000/perFiveHover));
        doExamineObject = Random.nextInt(1,(300000/perFiveExamine));
    }

    public long[] getDoTimes(){
        long[] actionTimes = {doMoveMouse, doHoverSkill, doExamineObject};
        return actionTimes;
    }

    public void randomMouse(){
        boolean done = true;
        while(done) {
            Mouse.moveRandomly();
            Time.sleep(500, 1500);
            doMoveMouse += Random.nextInt(1, (int) (300000 / perFiveMouse));
            done = false;
        }
    }

    public void hoverSkill(){
        Widget skillTab = Widgets.getWidget(320);
        WidgetChild skillBox = skillTab.getChild(Random.nextInt(1,23));
        boolean done = true;
        while(done){
            if(skillBox.isVisible()){
                Mouse.move(skillBox.getLocation());
                Time.sleep(1500,3000);
                randomMouse();
                Time.sleep(500,1500);
                done = false;
            }
            else {
                Widgets.getWidget(548,46).click();
                Time.sleep(350,800);
            }
        }
        doHoverSkill+= Random.nextInt(1,(int)(300000/perFiveHover));
    }

    public void examineGameObject(){
        final Filter<GameObject> thing_filter = new Filter<GameObject>() {

            @Override
            public boolean accept(GameObject thing) {
                if (thing.isOnScreen() && contains(thing.getActions(), "Examine")){
                    return true;
                }
                return false;
            }
        };
        boolean done = true;
        while(done) {
            GameObject[] things = GameObjects.getLoaded();
            things[Random.nextInt(0, things.length - 1)].interact("Examine");
            Time.sleep(2500,5000);
            doExamineObject += Random.nextInt(1, (int) (300000 / perFiveExamine));
            done = false;
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

}