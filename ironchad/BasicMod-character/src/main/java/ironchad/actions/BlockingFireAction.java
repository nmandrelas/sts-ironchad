package ironchad.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BlockingFireAction extends AbstractGameAction {
    private final DamageInfo info;
    private final int block;

    public BlockingFireAction(AbstractCreature target, DamageInfo info, int block) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.duration = Settings.ACTION_DUR_FAST;
        this.block = block;
    }

    public void update() {
        int count = AbstractDungeon.player.hand.size();

        for (int i = 0; i < count; ++i) {
            this.addToTop(new GainBlockAction(AbstractDungeon.player, block));
        }
        this.addToBot(new DamageBasedOnBlockAction(this.target, this.info));


        for (int i = 0; i < count; ++i) {
            if (Settings.FAST_MODE) {
                this.addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
            } else {
                this.addToTop(new ExhaustAction(1, true, true));
            }
        }

        this.isDone = true;
    }
}