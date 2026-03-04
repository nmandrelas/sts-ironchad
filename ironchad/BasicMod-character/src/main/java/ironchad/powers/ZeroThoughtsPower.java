package ironchad.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;

import static ironchad.BasicMod.makeID;

public class ZeroThoughtsPower extends BasePower {
    private static final String NAME = "Zero Thoughts";
    public static final String ID = makeID(ZeroThoughtsPower.class.getSimpleName());

    public ZeroThoughtsPower(int amount) {
        super(ID, PowerType.BUFF, false, AbstractDungeon.player, amount);
    }


    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if(card.type == AbstractCard.CardType.ATTACK){
            this.flash();
            this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}