package MyRPG;

public class Paladin extends PhysicalClass {
    public Paladin(String name) {
        super(name, 200.0, 60.0, 15.0, 15.0 , 20.0, 15.0, 8.0,  25.0, 10.0, 7.0, 0.7, 1, 4);
    }
    @Override
    protected void updateEquipment(Equipment equipment, int multiplier){
        if(equipment instanceof Armor) {
            basePDef += equipment.baseStat[0]*multiplier/5;
            baseMDef += equipment.baseStat[1]*multiplier/5;
        }
        super.updateEquipment(equipment, multiplier);
    }
    public void hardHit(RPGCharacter target) {
        if(cd1!=0) return;
        if(!checkAlive()) return;
        if(cd1!=0) return;
        if(isInvalidTarget(target)) return;
        shoutSkillName("hard hit");
        double damage = pAtk*1.2;
        pAttack(target, damage);
        target.spd *= 0.95;
        regenMana(1);
        useSkill(1);
    }
    public void holyHeal(RPGCharacter target) {
        if(cd2!=0) return;
        if(!checkAlive()) return;
        if(isInvalidTarget(target)) return;
        shoutSkillName("holy heal");
        double heal = mAtk*3;
        heal(target, heal);
        regenMana(1.5);
        useSkill(2);
    }
    public void smite(RPGCharacter target){
        if(!checkAlive()) return;
        if(isInvalidTarget(target)) return;
        if(notEnoughMana(maxMana)) return;
        shoutSkillName("smite");
        double damage = (pAtk+mAtk)/2+hp/10;
        mAttack(target, damage);
        manaBack();
        useSkill(0);
    }
}
