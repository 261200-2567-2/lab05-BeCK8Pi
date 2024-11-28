package MyRPG;

import java.util.*;

interface RPGCharacterInterface {
    public void seeStats();
    public void levelUp();
    public void attack(RPGCharacter target);
    public void block();
    public void firstAid();
    public void giveFirstAid(RPGCharacter target);
    public void equip(Equipment equipment);
    public void unequip(Equipment equipment);

    public void fightTillDeath(RPGCharacter opponent);//joke method don't use
}

public abstract class RPGCharacter implements RPGCharacterInterface {
    protected String name;
    protected int level, baseCd1, cd1, baseCd2, cd2;
    protected double hpGrowth, pAtkGrowth, mAtkGrowth;
    protected double maxHp, maxMana, basePAtk, baseMAtk, basePDef, baseMDef, baseSpd, basePCriDmg, baseMCriDmg, baseManaRegen, baseManaReturn, blockDamage;
    protected double hp, mana, pAtk, mAtk, pDef, mDef, spd, pCriDmg, mCriDmg, manaRegen, manaReturn;
    protected int baseCriChance, baseDodgeChance, criChance, dodgeChance;
    protected boolean isBlocking = false, isAlive = true;
    private Weapon weapon;
    private Armor armor;
    private int accessoriesCount  = 0, accessoryMax = 5;
    private Accessory[] accessories = new Accessory[accessoryMax];
    protected RPGCharacter(String name, double hp, double mana, double pAtk, double mAtk, double pDef, double mDef, double spd, double hpGrowth, double pAtkGrowth, double mAtkGrowth, double blockDamage, int cd1, int cd2) {
        this.name = name;
        level = 0;
        this.hp = maxHp = hp;
        maxMana = mana;
        this.mana = 0;
        this.pAtk = basePAtk =  pAtk;
        this.mAtk = baseMAtk = mAtk;
        this.pDef = basePDef = pDef;
        this.mDef = baseMDef = mDef;
        this.spd = baseSpd = spd;
        this.hpGrowth = hpGrowth;
        this.pAtkGrowth = pAtkGrowth;
        this.mAtkGrowth = mAtkGrowth;
        this.blockDamage = blockDamage;
        manaRegen = baseManaRegen = 10;
        manaReturn = baseManaReturn = 0;
        criChance = baseCriChance = 5;
        dodgeChance = baseDodgeChance = 5;
        baseCd1 = cd1;
        baseCd2 = cd2;
        this.cd1 = 0;
        this.cd2 = 0;
    }
    public void seeStats(){
        checkAlive();
        System.out.println("--------------------\n" +
                        this.name + "'s status\n" +
                        "\nLEVEL: " + level +
                        "\nHEALTH: " + hp + "/" + maxHp +
                        "\nMANA: " + mana + "/" + maxMana +
                        "\nPHYSICAL ATTACK: " + pAtk +
                        "\nPHYSICAL CRI: " + pCriDmg +
                        "\nMAGIC ATTACK: " + mAtk +
                        "\nMAGIC CRI: " + mCriDmg +
                        "\nPHYSICAL DEFENSE: " + pDef +
                        "\nMAGICAL DEFENSE " + mDef +
                        "\nSPEED: " + spd +
                        "\n--------------------"
        );
    }
    public void levelUp(){
        if(!checkAlive()) return;
        System.out.println("--------------------\n"+ name + " leveled up!");
        level++;
        hp = maxHp = maxHp+hpGrowth;
        basePAtk = basePAtk+pAtkGrowth;
        baseMAtk = baseMAtk+mAtkGrowth;
        resetBuff();
    }
    protected void resetBuff(){
        pAtk = basePAtk;
        mAtk = baseMAtk;
        pDef = basePDef;
        mDef = baseMDef;
        spd = baseSpd;
        pCriDmg = basePCriDmg;
        mCriDmg = baseMCriDmg;
        criChance = baseCriChance;
        dodgeChance = baseDodgeChance;
    }
    public void attack(RPGCharacter target){
        if(!checkAlive()) return;
        if(isInvalidTarget(target)) return;
        isBlocking = false;
        useSkill(0);
    }
    protected boolean criAttack(){
        boolean result = new Random().nextInt(99) < criChance;
        if(result) System.out.println("CRITICAL!!!");
        return result;
    }
    protected boolean dodged(int chance){
        if(isBlocking) return false;
        boolean result = new Random().nextInt(99) < chance;
        if(result) System.out.println(name + " dodged!!!");
        return result;
    }
    protected void pAttack(RPGCharacter target, double damage){
        if(!checkAlive()) return;
        if(isInvalidTarget(target)) return;
        isBlocking = false;
        if(criAttack()) pAtk *= 1+pCriDmg/100;
        System.out.println(name + " physically attacks " + target.name + " for " + pAtk + " damage.");
        mana = Math.min(maxMana, mana+manaRegen);
        target.receivePDamage(damage);
    }
    protected void mAttack(RPGCharacter target, double damage){
        if(!checkAlive()) return;
        if(isInvalidTarget(target)) return;
        isBlocking = false;
        if(criAttack()) mAtk *= 1+mCriDmg/100;
        System.out.println(name + " magically attacks " + target.name + " for " + mAtk + " damage.");
        mana = Math.min(maxMana, mana+manaRegen);
        target.receiveMDamage(damage);
    }
    protected void regenMana(double multiplier){
        mana = Math.min(maxMana, mana*(1+multiplier));
    }
    protected void manaBack(){
        this.mana = maxMana*manaReturn/1000;
    }
    public void block(){ if(checkAlive()) isBlocking = true; }
    protected void receivePDamage(double damage){
        if(dodged(dodgeChance)) return;
        if(isBlocking) damage *= blockDamage;
        damage = Math.max(0, damage - pDef);
        System.out.println(name + " received " + takeDamage(damage) + " physical damage.");
        checkAlive();
    }
    protected void receiveMDamage(double damage){
        if(dodged(dodgeChance/2)) return;
        damage *= 1-mDef/100;
        System.out.println(name + " received " + takeDamage(damage) + " magic damage.");
        checkAlive();
    }
    protected double takeDamage(double damage){
        hp -= damage;
        return damage;
    }
    public boolean checkAlive(){
        isAlive = hp > 0;
        if(!isAlive) System.out.println(name + " is dead");
        return isAlive;
    }
    public boolean isInvalidTarget(RPGCharacter target){
        if(target==null) {
            System.out.println("Target isn't right. Try again!");
            return true;
        }
        if(!target.checkAlive()) {
            System.out.println("Target is already dead!");
            return true;
        }
        return false;
    }
    public boolean notEnoughMana(double mana){
        if(this.mana < mana){
            System.out.println("Not enough mana for ultimate use");
            return true;
        }
        return false;
    }
    public void firstAid(){
        if(!checkAlive()) return;
        System.out.println(name + " is healing");
        heal(this, 10);
        return;
    }
    public void giveFirstAid(RPGCharacter target){
        if(!checkAlive()) return;
        if(isInvalidTarget(target)) return;
        System.out.println(name + " is healing " + target.name);
        heal(target, 10);
    }
    protected void heal(RPGCharacter target, double heal){
        if(checkAlive()) return;
        if(isInvalidTarget(target)) return;
        heal = Math.min(target.maxHp-target.hp, heal);
        System.out.println(target.name + " heals for " + heal);
        target.hp += heal;
    }
    public void equip(Equipment equipment){
        if(!checkAlive()) return;
        if(equipment==null) {
            System.out.println("What equipment???");
            return;
        }
        if(equipment.master!=null){
            System.out.println("Don't steal!");
            return;
        }
        equipment.master = this;
        switch (equipment) {
            case Weapon w -> {
                if (weapon != null) {
                    System.out.println(name + " already equipped a weapon!");
                    return;
                }
                weapon = w;
                updateEquipment(w,1);
                return;
            }
            case Armor a -> {
                if (armor != null) {
                    System.out.println(name + " already equipped an armor!");
                    return;
                }
                armor = a;
                updateEquipment(a,1);
                return;
            }
            case Accessory a -> {
                if (accessoriesCount >= accessoryMax) {
                    System.out.println("Accessory full");
                    return;
                }
                for (int i = 0; i < accessoryMax; i++) {
                    if (accessories[i] == null){
                        accessories[i] = a;
                        accessoriesCount++;
                        updateEquipment(a,1);
                        return;
                    }
                }
            }
            default -> {
            }
        }
    }
    public void unequip(Equipment equipment){
        if(!checkAlive()) return;
        if(equipment==null) {
            System.out.println("What equipment???");
            return;
        }
        if(equipment == weapon){
            weapon.master = null;
            weapon = null;
            updateEquipment(equipment, -1);
        }
        if(equipment == armor){
            armor.master = null;
            armor = null;
            updateEquipment(equipment, -1);
        }
        for(int i =0;i<accessoryMax;i++){
            if(equipment == accessories[i]){
                accessories[i].master = null;
                accessories[i] = null;
                accessoriesCount--;
                updateEquipment(equipment,-1);
            }
        }
    }
    protected void updateEquipment(Equipment equipment, int multiplier){
        if(equipment instanceof PhysicalWeapon) {
            basePAtk += equipment.stat[0] * multiplier;
            basePCriDmg += equipment.stat[1] * multiplier;
            baseCriChance += (int) (equipment.stat[2] * multiplier);
        }
        if(equipment instanceof MagicalWeapon) {
            baseMAtk += equipment.stat[0] * multiplier;
            baseMCriDmg += equipment.stat[1] * multiplier;
            baseCriChance += (int) (equipment.stat[2] * multiplier);
        }
        if(equipment instanceof Armor){
            basePDef += equipment.stat[0] * multiplier;
            baseMDef += equipment.stat[1] * multiplier;
        }
        if(equipment instanceof Accessory){
            baseManaRegen += equipment.stat[0] * multiplier;
            baseManaReturn  += equipment.stat[1] * multiplier;
            baseSpd += equipment.stat[2] * multiplier;
        }
        spd -= multiplier*baseSpd*equipment.weight/100;
        resetBuff();
    }
    protected void shoutSkillName(String skillName){
        if(!checkAlive()) return;
        System.out.println(name + " uses " + skillName + "!");
    }
    protected void useSkill(int skill){
        if(skill==1) cd1 = baseCd1+1;
        if(skill==2) cd2 = baseCd2+1;
        reduceCd1();
        reduceCd2();
    }
    protected void reduceCd1(){
        cd1 = Math.max(0, cd1-1);
    }
    protected void reduceCd2(){
        cd2 = Math.max(0, cd2-1);
    }
    public void fightTillDeath(RPGCharacter opponent){
        while(this.isAlive&&opponent.isAlive){
            this.attack(opponent);
            opponent.attack(this);
        }
    }
}

abstract class PhysicalClass extends RPGCharacter{
    PhysicalClass(String name, double hp, double mana, double pAtk, double mAtk, double pDef, double mDef, double spd, double hpGrowth, double pAtkGrowth, double mAtkGrowth, double blockDamage, int cd1, int cd2){
        super(name, hp, mana, pAtk, mAtk, pDef,mDef,spd,hpGrowth,pAtkGrowth,mAtkGrowth,blockDamage,cd1,cd2);
    }
    @Override
    public void attack(RPGCharacter target){
        super.attack(target);
        pAttack(target, pAtk);
    }
}

abstract class MagicalClass extends RPGCharacter{
    MagicalClass(String name, double hp, double mana, double pAtk, double mAtk, double pDef, double mDef, double spd, double hpGrowth, double pAtkGrowth, double mAtkGrowth, double blockDamage, int cd1, int cd2){
        super(name, hp, mana, pAtk, mAtk, pDef,mDef,spd,hpGrowth,pAtkGrowth,mAtkGrowth,blockDamage,cd1,cd2);
    }
    @Override
    public void attack(RPGCharacter target){
        super.attack(target);
        mAttack(target, mAtk);
    }
}
