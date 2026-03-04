package ironchad.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CorruptingStrikeAction extends AbstractGameAction {
    private final int burnsToAdd;
    private final AbstractPlayer player;

    public CorruptingStrikeAction(int amountOfBurnsToAdd) {
        burnsToAdd = amountOfBurnsToAdd;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.player = AbstractDungeon.player;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.player.hand.isEmpty()) {
                this.isDone = true;
            } else {
                CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                for (AbstractCard c : this.player.hand.group) {
                    temp.addToTop(c);
                }

                temp.sortAlphabetically(false);
                temp.sortByRarityPlusStatusCardType(false);
                AbstractDungeon.gridSelectScreen.open(temp, 1, "Play for free and exhaust", false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    c.exhaust = true;
                    int cost = c.cost;
                    AbstractDungeon.player.hand.group.remove(c);
                    AbstractDungeon.getCurrRoom().souls.remove(c);
                    this.addToBot(new NewQueueCardAction(c, true, false, true));
                    int totalBurnsToAdd = c.cost - burnsToAdd;
                    if(totalBurnsToAdd > 0){
                        addToTop(new MakeTempCardInDiscardAction(new Burn(), totalBurnsToAdd));
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }
}
