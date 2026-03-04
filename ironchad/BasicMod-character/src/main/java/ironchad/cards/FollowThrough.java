package ironchad.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ironchad.actions.SappingAction;
import ironchad.character.IronChad;
import ironchad.util.CardStats;

// Can only be played if the last card played was an attack
// Deal heavy damage and sap 2 times
public class FollowThrough extends BaseCard{
    public static final String ID = makeID(FollowThrough.class.getSimpleName());
    private static final CardStats info = new CardStats(
            IronChad.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DMG = 8;
    private static final int TIMES = 2;
    private static final int UPG_TIMES = 1;
    public FollowThrough() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DMG);
        setMagic(TIMES, UPG_TIMES);
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            if (AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()
                    || (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)).type != CardType.ATTACK) {
                    canUse = false;
                    this.cantUseMessage = "Last card played is not attack.";
                }

            return canUse;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SappingAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),this.magicNumber));
    }
}
