package ironchad.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import ironchad.character.IronChad;
import ironchad.powers.MasochismPower;

import java.util.Objects;
import java.util.Optional;

public class SappingAction extends AbstractGameAction {
    private DamageInfo info;
    private  int replicateTimes;
    private float startingDuration;
    /// replicates times <= 0 resets to 1
    public SappingAction(AbstractCreature target, DamageInfo info, int replicateTimes) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        if(replicateTimes <= 0){
            this.replicateTimes = 1;
        }else {
            this.replicateTimes = replicateTimes;
        }
    }

    @Override
    public void update() {
        AbstractPower masochismPower = null;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (Objects.equals(p.ID, MasochismPower.ID)) {
                masochismPower = p;
                break;
            }
        }
        int extraDamage = masochismPower == null ? 0 : masochismPower.amount;
        int healAmount = 0;
        for(int i =0; i < replicateTimes; i++) {
            this.info.base += extraDamage;
            healAmount = this.info.base;
            this.addToTop(new DamageAction(this.target, this.info, AttackEffect.FIRE));
            AbstractDungeon.player.heal(healAmount);
        }
        if(masochismPower != null){
            this.addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, MasochismPower.ID, masochismPower.amount));
        }
        this.isDone = true;
    }
}
