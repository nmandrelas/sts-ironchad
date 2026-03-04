package ironchad.powers;

import basemod.devcommands.debug.Debug;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import ironchad.BasicMod;
import ironchad.cards.FiendFire;

import static ironchad.BasicMod.makeID;

public class MasochismPower extends BasePower {
    private static final String NAME = "Masochism";
    public static final String ID = makeID(MasochismPower.class.getSimpleName());
    //    public BasePower(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount) {
    public MasochismPower(int amount){
        super(ID, PowerType.BUFF,false, AbstractDungeon.player,amount);
    }

    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.owner == this.owner) {
            this.flash();
            amount += 1;
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}
