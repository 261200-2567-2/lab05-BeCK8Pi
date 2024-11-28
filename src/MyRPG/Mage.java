package MyRPG;

public class Mage extends MagicalClass{
    public Mage(String name) {
        super(name, 60, 100, 5, 30, 5, 15, 9, 10, 5, 15, 0.95, 2, 4);
        manaRegen = 15;
        criChance = baseCriChance = 5;
        mCriDmg = baseMCriDmg = 20;
    }
    @Override
    protected void updateEquipment(Equipment equipment, int multiplier){
        if(equipment instanceof Staff) baseMAtk += equipment.baseStat[0]*multiplier/5;
        super.updateEquipment(equipment, multiplier);
    }
    public void fireBall(RPGCharacter target) {
        if(cd1!=0) return;
        if(!checkAlive()) return;
        if(isInvalidTarget(target)) return;
        shoutSkillName("fire ball");
        double damage = mAtk*2;
        mAttack(target, damage);
        regenMana(1.3);
        useSkill(1);
    }
    public void manaCharge(){
        if(cd2!=0) return;
        if(!checkAlive()) return;
        shoutSkillName("mana charge");
        regenMana(4);
        useSkill(2);
    }
    public void explosion(RPGCharacter target){
        if(!checkAlive()) return;
        if(isInvalidTarget(target)) return;
        if(notEnoughMana(maxMana)) return;
        shoutSkillName("EXPLOSION!!");
        double damage = mAtk*5;
        mAttack(target, damage);
        manaBack();
        spd = 1;
        useSkill(0);
    }
}
