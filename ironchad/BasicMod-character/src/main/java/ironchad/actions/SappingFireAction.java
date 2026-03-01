package ironchad.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import ironchad.powers.MasochismPower;

import java.util.Objects;

public class SappingFireAction extends AbstractGameAction {
    private final DamageInfo info;

    public SappingFireAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        int count = AbstractDungeon.player.hand.size();
        AbstractPower masochismPower = null;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (Objects.equals(p.ID, MasochismPower.ID)) {
                masochismPower = p;
                break;
            }
        }
        int extraDamage = masochismPower == null ? 0 : masochismPower.amount;
        int healAmount = 0;
        this.info.base += extraDamage;

        for(int i = 0; i < count; ++i) {
            this.addToTop(new DamageAction(this.target, this.info, AttackEffect.FIRE));
            AbstractDungeon.player.heal(healAmount);
        }

        for(int i = 0; i < count; ++i) {
            if (Settings.FAST_MODE) {
                this.addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
            } else {
                this.addToTop(new ExhaustAction(1, true, true));
            }
        }
        if(masochismPower != null){
            this.addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, MasochismPower.ID, masochismPower.amount));
        }

        this.isDone = true;
    }

}
