package banking;

import org.tbot.methods.Bank;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Equipment;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.Item;
import slayer.Main;
import util.Monster;
import util.Task;
import walking.WebSingelton;

/**
 * Created by Russell on 2/26/2016.
 */
public class EquipmentBanking extends Task {

    @Override
    public boolean validate() {
        Item curWeapon = Equipment.getItemInSlot(Equipment.SLOTS_WEAPON);
        Item weapon = Main.getStartWeapon();
        Item curShield = Equipment.getItemInSlot(Equipment.SLOTS_SHIELD);
        Item shield = Main.getStartShield();
        if(getMissingEquipmentId()!=-1){
            return true;
        }
        if((curWeapon==null && weapon!=null) || (curShield==null && shield!=null)){
            return true;
        }
        if(getMissingEquipmentId()==-1 && ((curWeapon!=null && weapon.getID()!= weapon.getID())
                || (curShield!=null && shield.getID()!=shield.getID()))){
            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        int misEquipmentID = getMissingEquipmentId();
        Item curWeapon = Equipment.getItemInSlot(Equipment.SLOTS_WEAPON);
        Item weapon = Main.getStartWeapon();
        Item curShield = Equipment.getItemInSlot(Equipment.SLOTS_SHIELD);
        Item shield = Main.getStartShield();
        if(Bank.isOpen()){
            if(misEquipmentID!=-1) {
                bankAndEquip(misEquipmentID);
            }
            else if(curWeapon==null || (curWeapon!=null && curWeapon.getID()!=weapon.getID())){
                bankAndEquip(weapon.getID());
            }
            else if(curShield==null || (curShield!=null && curShield.getID()!=shield.getID())){
                bankAndEquip(shield.getID());
            }
        }
        else{
            WebSingelton.getInstance().openBank();
        }
    }

    @Override
    public int priority() {
        return 60;
    }

    @Override
    public String getName() {
        return "Equipment Banking";
    }

    private int getMissingEquipmentId() {
        for (int i = 0; i < Monster.getCurrentMonster().getEquipmentIds().length; i++) {
            if (!Equipment.contains(Monster.getCurrentMonster().getEquipmentIds()[i])) {
                return Monster.getCurrentMonster().getEquipmentIds()[i];
            }
        }
        return -1;
    }

    private void bankAndEquip(int id){
        if (Inventory.contains(id)) {
            if(Bank.isOpen()) {
                Bank.close();
            }
            Inventory.getFirst(id).click();
            Time.sleep(600, 1200);
        } else {
            if (Inventory.isFull()) {
                Bank.depositAllExcept("Enchanted gem");
            } else {
                if (Bank.contains(id)) {
                    Bank.withdrawAll(id);
                }
            }
        }
    }
}
