package ironchad.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import ironchad.character.IronChad;
import ironchad.util.CardStats;

public class ZeroConsequences extends BaseCard{
    public static final String ID = makeID(ZeroConsequences.class.getSimpleName());
    private static final CardStats info = new CardStats(
            IronChad.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int HEALTH_LOST = 8;
    private static final int UPG_HEALTH_LOST = -2;
    public ZeroConsequences() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(HEALTH_LOST, UPG_HEALTH_LOST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(new LightningEffect(p.drawX, p.drawY), 0.05F));
        this.addToBot(new LoseHPAction(p, p, this.magicNumber));
        addToTop(new ApplyPowerAction(p ,p, new IntangiblePower(p,1)));

    }
}
