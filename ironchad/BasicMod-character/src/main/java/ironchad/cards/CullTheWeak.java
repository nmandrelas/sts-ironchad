package ironchad.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ironchad.character.IronChad;
import ironchad.util.CardStats;

import java.util.ArrayList;

// Do damage X times to the enemy with the lowest hp
// If the enemy is bellow 50% do double damage
public class CullTheWeak extends BaseCard {
    public static final String ID = makeID(CullTheWeak.class.getSimpleName());
    private static final CardStats info = new CardStats(
            IronChad.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DMG = 4;
    private static final int UPG_DMG = 2;

    public CullTheWeak() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DMG, UPG_DMG);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(energyOnUse == 0){
            return;
        }
        ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
        AbstractMonster target = monsters.get(0);
        for (AbstractMonster candidate : monsters) {
            if (!candidate.isDeadOrEscaped() && candidate.currentHealth < target.currentHealth)
                target = candidate;
        }
        boolean fiftyThreshold = target.maxHealth - target.currentHealth > target.maxHealth / 2;
        int newDmg = this.damage * 2;
        for(int i = 0 ; i < energyOnUse; i++) {
            if (fiftyThreshold) {
                this.addToTop(new DamageAction(target, new DamageInfo(abstractPlayer, newDmg), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            } else {
                this.addToTop(new DamageAction(target, new DamageInfo(abstractPlayer, this.damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
    }
}