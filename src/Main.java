import MyRPG.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Mage a = new Mage("a");
        Mage b1 = new Mage("b");
        Equipment d = new Ring();
        //a.equip(d);
        Equipment b = new BasicStaff();
        Equipment c = new LightArmor();
        d.seeStats();
        d.levelUp();
        d.seeStats();
        b.seeStats();
        c.seeStats();
        //Equipment[] d = new Equipment[6];
        //for(int i=0;i<6;i++){
        //    d[i] = new Ring();
        //}
        //for(int i=0;i<6;i++){a.equip(d[i]);}
        //a.equip(b);
        //a.equip(c);
        a.seeStats();
        //a.unequip(b);
        a.seeStats();
        a.attack(b1);
        b.seeStats();
    }
}