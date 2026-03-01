package ironchad.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static ironchad.BasicMod.logger;

public class DamageBasedOnBlockAction extends AbstractGameAction {
    private final DamageInfo info;

    public DamageBasedOnBlockAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        info.base = AbstractDungeon.player.currentBlock;
        info.output = AbstractDungeon.player.currentBlock;
        logger.info("Test {}", info.base);
        this.addToTop(new DamageAction(this.target, this.info, AttackEffect.BLUNT_HEAVY));

        this.isDone = true;
    }
}