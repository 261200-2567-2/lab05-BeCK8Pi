package MyRPG;

interface EquipmentInterface {
    public void seeStats();
    public void levelUp();

}
public class Equipment implements EquipmentInterface {
    protected String name;
    protected int level;
    protected double weight;
    protected int maxStatCount = 4;
    protected String[] statName = new String[maxStatCount];
    protected double[] baseStat = new double[maxStatCount];
    protected double[] stat = new double[maxStatCount];
    protected RPGCharacter master;
    //public Equipment(){}
    protected Equipment(String name, double[] stat, double weight){
        this.name = name;
        level = 0;
        int count = 0;
        for(double i: stat){ this.stat[count] = baseStat[count++] = i; }
        this.weight = weight;
    }
    public void seeStats(){
        System.out.println("--------------------\n"+
                name + " LEVEL " + level
        );
        for(int i=0;i<statName.length;i++){
            if(statName[i]!=null) {
                System.out.println(
                        statName[i] + ": " + stat[i]
                );
            }
        }
        System.out.println(
                "WEIGHT: " + weight + "\n"
        );
    }
    public void levelUp() {
        if(level==5){
            System.out.println("This equipment is at max level");
            return;
        }
        RPGCharacter wearer = master;
        if(wearer!=null) wearer.unequip(this);
        level++;
        for(int i=0;i<statName.length;i++){ stat[i] += baseStat[i]*0.2; }
        if(wearer!=null) wearer.equip(this);
    }
}

class Weapon extends Equipment{
    protected Weapon(String name, double[] stat,double weight){
        super(name, stat, weight);
        statName[0] = " ATTACK";
        statName[1] = " CRITICAL DAMAGE";
        statName[2] = " CRITICAL CHANCE";
    }
}

class PhysicalWeapon extends Weapon {
    public PhysicalWeapon(String name, double[] stats, double weight){
        super(name, stats, weight);
        statName[0] = "PHYSICAL" + statName[0];
        statName[1] = "PHYSICAL" + statName[1];
        statName[2] = "PHYSICAL" + statName[2];
    }
}

class Sword extends PhysicalWeapon {
    public Sword(String name, double attack, double criDmg,double criRate, double weight){
        super(name, new double[]{attack, criDmg, criRate}, weight);
    }
}

class Bow extends PhysicalWeapon {
    public Bow(String name, double attack, double criDmg,double criRate, double weight){
        super(name, new double[]{attack, criDmg, criRate}, weight);
    }
}

class MagicalWeapon extends Weapon{
    public MagicalWeapon(String name, double[] stats, double weight){
        super(name, stats, weight);
        statName[0] = "MAGICAL" + statName[0];
        statName[1] = "MAGICAL" + statName[1];
        statName[2] = "MAGICAL" + statName[2];
    }
}

class Staff extends MagicalWeapon{
    public Staff(String name, double attack, double criDmg, double criRate, double weight){
        super(name, new double[]{attack, criDmg, criRate}, weight);
    }
}

class Armor extends Equipment{
    public Armor(String name, double physicDefense, double magicDefense, double weight){
        super(name, new double[]{physicDefense, magicDefense}, weight);
        statName[0] = "PHYSICAL DEFENSE";
        statName[1] = "MAGICAL DEFENSE";
    }
}

class Accessory extends Equipment {
    public Accessory(String name, double manaRegen, double manaReturn, double spd) {
        super(name, new double[]{manaRegen, manaReturn, spd}, 0);
        statName[0] = "MANA REGEN";
        statName[1] = "MANA RETURN";
        statName[2] = "SPEED";
    }
}

