package ironchad.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DoubleTapPower;
import ironchad.character.IronChad;
import ironchad.powers.TripleTapPower;
import ironchad.util.CardStats;

///  Lose HP, your next attack is played twice - thrice
public class Multitap extends BaseCard {
    public static final String ID = makeID(Multitap.class.getSimpleName());
    private static final CardStats info = new CardStats(
            IronChad.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int AMOUNT_OF_BUFF = 1;
    public Multitap() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(this.upgraded){
            this.addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new TripleTapPower(AMOUNT_OF_BUFF), AMOUNT_OF_BUFF));

        }else{
            this.addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DoubleTapPower(abstractPlayer, AMOUNT_OF_BUFF), AMOUNT_OF_BUFF));
        }
    }

}
