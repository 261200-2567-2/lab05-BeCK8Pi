import MyRPG.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Mage a = new Mage("a");
        Mage b = new Mage("b");
        Equipment a1 = new Ring();
        Equipment a2 = new BasicStaff();
        Equipment a3 = new LightArmor();
        a3.seeStats();
        a3.levelUp();
        a3.seeStats();
        a.equip(a1);
        a.equip(a2);
        a.equip(a3);
        a.seeStats();
        Equipment b1 = new BasicStaff();
        Equipment b2 = new Necklace();
        Equipment b3 = new LightArmor();
        b.equip(b1);
        b.equip(b2);
        b.equip(b3);
        b.seeStats();
        //Equipment[] d = new Equipment[6];
        //for(int i=0;i<6;i++){
        //    d[i] = new Ring();
        //}
        //for(int i=0;i<6;i++){a.equip(d[i]);}
        //a.equip(b);
        //a.equip(c);
        //a.seeStats();
        //a.unequip(b);
       a.fightTillDeath(b);
    }
}