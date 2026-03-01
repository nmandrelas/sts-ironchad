package ironchad.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import ironchad.powers.MasochismPower;

import java.util.Objects;

public class BlockingFireAction extends AbstractGameAction {
    private DamageInfo info;
    private int block;
    private float startingDuration;

    public BlockingFireAction(AbstractCreature target, DamageInfo info, int block) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.block = block;
    }

    public void update() {
        int count = AbstractDungeon.player.hand.size();

        for (int i = 0; i < count; ++i) {
            this.addToTop(new GainBlockAction(AbstractDungeon.player, block));
        }
        info.base = AbstractDungeon.player.currentBlock;
        this.addToBot(new DamageAction(this.target, this.info, AttackEffect.FIRE));


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