package ironchad.powers;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static ironchad.BasicMod.makeID;

public class MaledictionOfFlamesPower extends BasePower{
    private static final String NAME = "MaledictionOfFlamesPower";
    public static final String ID = makeID(MaledictionOfFlamesPower.class.getSimpleName());

    public MaledictionOfFlamesPower() {
        super(ID, PowerType.BUFF, false, AbstractDungeon.player,0);
    }
    public void onAfterCardPlayed(AbstractCard usedCard) {
        AbstractPlayer p = AbstractDungeon.player;
        String burnId = new Burn().cardID;
        for (int i = 0; i < p.drawPile.size(); ) {
            AbstractCard c = p.drawPile.group.get(i);
            if (c.type == AbstractCard.CardType.CURSE || (c.type == AbstractCard.CardType.STATUS && c.cardID != burnId )) {
                addToBot(new ExhaustSpecificCardAction(c, p.drawPile));
                addToTop(new MakeTempCardInDrawPileAction(new Burn(), 1, true, true));

            }
            i++;
        }

        for (int i = 0; i < p.discardPile.size(); ) {
            AbstractCard c = p.discardPile.group.get(i);
            if (c.type == AbstractCard.CardType.CURSE || (c.type == AbstractCard.CardType.STATUS && c.cardID != burnId )) {
                addToBot(new ExhaustSpecificCardAction(c, p.discardPile));
                addToTop(new MakeTempCardInDiscardAction(new Burn(), 1));
            }
            i++;
        }

        for (int i = 0; i < p.hand.size(); ) {
            AbstractCard c = p.hand.group.get(i);
            if (c.type == AbstractCard.CardType.CURSE || (c.type == AbstractCard.CardType.STATUS && c.cardID != burnId )) {
                addToBot(new ExhaustSpecificCardAction(c, p.hand));
                c = new Burn();
            }
            i++;
        }
    }

}
