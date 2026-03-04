package ironchad.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ironchad.character.IronChad;
import ironchad.util.CardStats;

public class Inferno extends BaseCard{
    public static final String ID = makeID(Inferno.class.getSimpleName());
    private static final CardStats info = new CardStats(
            IronChad.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DMG = 6;
    private static final int UPG_DMG = 3;

    public Inferno() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DMG, UPG_DMG);
        this.cardsToPreview = new Burn();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int statusCount = 0;
        String burnId = new Burn().cardID;
        for (int i = 0; i < p.drawPile.size(); ) {
            AbstractCard c = p.drawPile.group.get(i);
            if (c.type == CardType.STATUS && c.cardID == burnId ) {
                statusCount++;
                p.drawPile.removeCard(c);
                p.limbo.addToTop(c);
                c.targetDrawScale = 0.5F;
                c.setAngle(0, true);
                c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                addToBot(new ExhaustSpecificCardAction(c, p.limbo));
                addToBot(new WaitAction(0.1F));
            } else {
                i++;
            }
        }

        for (int i = 0; i < p.discardPile.size(); ) {
            AbstractCard c = p.discardPile.group.get(i);
            if (c.type == CardType.STATUS && c.cardID == burnId ) {
                statusCount++;
                p.discardPile.removeCard(c);
                p.limbo.addToTop(c);
                c.targetDrawScale = 0.5F;
                c.setAngle(0, true);
                c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                addToBot(new ExhaustSpecificCardAction(c, p.limbo));
                addToBot(new WaitAction(0.1F));
            } else {
                i++;
            }
        }

        for (int i = 0; i < p.hand.size(); ) {
            AbstractCard c = p.hand.group.get(i);
            if (c.type == CardType.STATUS && c.cardID == burnId ) {
                statusCount++;
                p.hand.removeCard(c);
                p.limbo.addToTop(c);
                c.targetDrawScale = 0.5F;
                c.setAngle(0, true);
                c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                addToBot(new ExhaustSpecificCardAction(c, p.limbo));
                addToBot(new WaitAction(0.1F));
            } else {
                i++;
            }
        }

        if (statusCount > 0) {
            for (int i = 0; i < statusCount; i++) {
                addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }


    public void applyPowers() {
        super.applyPowers();
        String burnId = new Burn().cardID;

        if (AbstractDungeon.player != null) {
            this.rawDescription = cardStrings.DESCRIPTION;

            int statusCount = 0;
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.type == CardType.STATUS && c.cardID == burnId ) {
                    statusCount++;
                }
            }

            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c.type == CardType.STATUS && c.cardID == burnId ) {
                    statusCount++;
                }
            }

            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.type == CardType.STATUS && c.cardID == burnId ) {
                    statusCount++;
                }
            }

            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + statusCount + cardStrings.EXTENDED_DESCRIPTION[1];

            this.initializeDescription();
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
}
