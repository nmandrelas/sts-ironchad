package ironchad.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePower;

import static ironchad.BasicMod.makeID;

public class PainRemainsPower extends BasePower {
    private static final String NAME = "Pain Remains";
    public static final String ID = makeID(PainRemainsPower.class.getSimpleName());
    //    public BasePower(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount) {
    public PainRemainsPower(int amount){
        super(ID, PowerType.BUFF,false, AbstractDungeon.player,amount);
    }

    public void wasHPLost(DamageInfo info, int damageAmount) {
        if(damageAmount > 0 && info.owner == this.owner){
            this.flash();

            this.addToBot(new ReducePowerAction(info.owner, info.owner, PainRemainsPower.ID, damageAmount));
            if(amount <= 0){
                amount = 0;
                addToTop(new ApplyPowerAction(info.owner,info.owner, new IntangiblePower(this.owner,2)));
            }
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}