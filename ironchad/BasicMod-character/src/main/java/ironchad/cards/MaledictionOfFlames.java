package ironchad.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ironchad.character.IronChad;
import ironchad.powers.MaledictionOfFlamesPower;
import ironchad.util.CardStats;

public class MaledictionOfFlames extends BaseCard{
    public static final String ID = makeID(MaledictionOfFlames.class.getSimpleName());
    private static final CardStats info = new CardStats(
            IronChad.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            AbstractCard.CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public MaledictionOfFlames() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.cardsToPreview = new Burn();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
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
                addToTop(new MakeTempCardInHandAction(new Burn(), 1));
            }
            i++;
        }
        this.addToBot(new ApplyPowerAction(p, p, new MaledictionOfFlamesPower()));
    }

}
