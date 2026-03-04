package ironchad.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ironchad.actions.SappingAction;
import ironchad.character.IronChad;
import ironchad.util.CardStats;

public class Bloodlust extends BaseCard {
    public static final String ID = makeID(Bloodlust.class.getSimpleName());
    private static final CardStats info = new CardStats(
            IronChad.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DMG = 8;
    private static final int UPG_DMG = 2;

    public Bloodlust() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DMG, UPG_DMG);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SappingAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, DamageInfo.DamageType.HP_LOSS), this.energyOnUse));
    }
}