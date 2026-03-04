package ironchad.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ironchad.actions.CorruptingStrikeAction;
import ironchad.character.IronChad;
import ironchad.util.CardStats;

/// Deal dmg, the next card you play is free and will be exhausted, add burns in your discard pile equal to its cost
public class CorruptingStrike extends BaseCard {
    public static final String ID = makeID(CorruptingStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            IronChad.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1//The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 9;
    private static final int BURNS_TO_ADD = 0; //EQUAL TO COST
    private static final int UPG_BURNS_TO_ADD = -1; //EQUAL TO COST -1

    public CorruptingStrike() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        tags.add(CardTags.STRIKE);

        setDamage(DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setMagic(BURNS_TO_ADD, UPG_BURNS_TO_ADD);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        this.addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.addToBot(new CorruptingStrikeAction(this.magicNumber));
    }
}