package ironchad.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ironchad.character.IronChad;
import ironchad.util.CardStats;

public class FueledByFire extends BaseCard{
    public static final String ID = makeID(FueledByFire.class.getSimpleName());
    private static final CardStats info = new CardStats(
            IronChad.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int CARDS_DRAWN = 2;
    private static final int UPG_CARDS_DRAWN = 1;

    public FueledByFire() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(CARDS_DRAWN, UPG_CARDS_DRAWN);
        this.cardsToPreview = new Burn();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(magicNumber));
        this.addToBot(new MakeTempCardInDiscardAction(new Burn(), 1));

    }
}

