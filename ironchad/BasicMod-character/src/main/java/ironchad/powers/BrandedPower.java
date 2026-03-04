package ironchad.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static ironchad.BasicMod.makeID;

public class BrandedPower extends BasePower {
    private static final String NAME = "BrandedPower";
    public static final String ID = makeID(BrandedPower.class.getSimpleName());

    //    public BasePower(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount) {
    public BrandedPower(int amount) {
        super(ID, PowerType.BUFF, false, AbstractDungeon.player, amount);
    }

    public void onCardDraw(AbstractCard card) {
        String burnId = new Burn().cardID;
        if (card.type == AbstractCard.CardType.STATUS && card.cardID == burnId) {
            this.flash();
            this.addToBot(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
        }

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}