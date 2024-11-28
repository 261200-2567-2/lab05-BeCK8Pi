package MyRPG;

public class Ranger extends PhysicalClass{
    public Ranger(String name) {
        super(name, 80.0, 50.0, 20.0, 10.0 , 5.0, 5.0, 12.0,  15.0, 10.0, 15.0, 0.9, 3,3);
        criChance = baseCriChance = 5;
        pCriDmg = basePCriDmg = 20;
        dodgeChance = baseDodgeChance = 5;
    }
    @Override
    protected void updateEquipment(Equipment equipment, int multiplier){
        if(equipment instanceof Bow) basePAtk += equipment.baseStat[0]*multiplier/5;
        super.updateEquipment(equipment, multiplier);
    }
    public void tripleShot(RPGCharacter target1, RPGCharacter target2, RPGCharacter target3) {
        if(cd1!=0) return;
        if(!checkAlive()) return;
        RPGCharacter[] target = {target1,target2,target3};
        for(RPGCharacter t : target) { if(isInvalidTarget(t)) return; }
        shoutSkillName("triple shot");
        for(RPGCharacter t : target) {
            double damage = pAtk*0.7;
            pAttack(t, damage);
        }
        regenMana(1.3);
        useSkill(1);
    }
    public void armorBreak(RPGCharacter target) {
        if(cd2!=0) return;
        if(!checkAlive()) return;
        if(isInvalidTarget(target)) return;
        shoutSkillName("armor break");
        pAttack(target, pAtk);
        target.pDef *= 0.8;
        regenMana(1);
        useSkill(2);
    }
    public void paralysisShot(RPGCharacter target) {
        if(!checkAlive()) return;
        if(isInvalidTarget(target)) return;
        if(notEnoughMana(maxMana)) return;
        shoutSkillName("paralysis shot");
        target.spd *= 0.5;
        pAttack(target,pAtk);
        mAttack(target,pAtk);
        manaBack();
        useSkill(0);
    }
}
