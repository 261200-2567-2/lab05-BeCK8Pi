package MyRPG;

public class Warrior extends PhysicalClass {
    public Warrior(String name) {
        super(name, 150, 50, 20, 0, 10, 5, 10, 20, 10, 0, 0.8, 3, 3);
    }
    @Override
    protected void updateEquipment(Equipment equipment, int multiplier){
        if(equipment instanceof Sword) basePAtk += equipment.baseStat[0]*multiplier/5;
        super.updateEquipment(equipment, multiplier);
    }
    public void doubleSlash(RPGCharacter target) {
        if(cd1!=0) return;
        if(!checkAlive()) return;
        if(isInvalidTarget(target)) return;
        shoutSkillName("double slash");
        for(int i=0;i<2;i++){
            double damage = pAtk*0.8;
            pAttack(target, damage);
        }
        regenMana(1.5);
        useSkill(1);
    }
    public void rage(){
        if(cd2!=0) return;
        if(!checkAlive()) return;
        shoutSkillName("rage");
        double atkUp = basePAtk*0.2;
        System.out.println(name + " gains " + atkUp + " physical attack");
        pAtk += atkUp;
        regenMana(1.2);
        useSkill(2);
    }
    public void swordBlast(RPGCharacter target){
        if(!checkAlive()) return;
        if(isInvalidTarget(target)) return;
        if(notEnoughMana(maxMana)) return;
        shoutSkillName("sword blast");
        double damage = pAtk*3;
        pAttack(target, damage);
        manaBack();
        useSkill(0);
    }
}
