package thePackmaster.cards.spherespack;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.orbs.downfallpack.Ghostflame;
import thePackmaster.orbs.spherespack.Blaze;
import thePackmaster.orbs.spherespack.Scourge;

public class Sphere extends AbstractSpheresCard implements CustomSavable<Integer> {
    public static final String ID = SpireAnniversary5Mod.makeID("Sphere");
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;

    private SphereOrb orb;

    public Sphere() {
        super(ID, COST, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.orb = !CardCrawlGame.isInARun() || AbstractDungeon.miscRng == null ? null : intToOrb(AbstractDungeon.miscRng.random(SphereOrb.values().length - 1));
        this.baseBlock = BLOCK;
        this.updateNameAndDescription();
    }

    @Override
    public void upp() {
        this.upgradeBlock(UPGRADE_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, this.block));
        AbstractOrb orbToChannel = getOrb(this.orb);
        if (orbToChannel != null) {
            this.addToBot(new ChannelAction(orbToChannel));
        }
    }

    private void updateNameAndDescription() {
        if (this.orb != null) {
            this.name = cardStrings.EXTENDED_DESCRIPTION[0].replace("{0}", this.orb.name());
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1].replace("{0}", this.orb.name());
            this.initializeTitle();
            this.initializeDescription();
        }

    }

    private static AbstractOrb getOrb(SphereOrb orb) {
        switch (orb) {
            case Lightning:
                return new Lightning();
            case Frost:
                return new Frost();
            case Dark:
                return new Dark();
            case Ghostflame:
                return new Ghostflame();
            case Blaze:
                return new Blaze();
            case Snow:
                return new Scourge();
            default:
                return null;
        }
    }

    private static SphereOrb intToOrb(int n) {
        switch (n) {
            case 0:
                return SphereOrb.Lightning;
            case 1:
                return SphereOrb.Frost;
            case 2:
                return SphereOrb.Dark;
            case 3:
                return SphereOrb.Ghostflame;
            case 4:
                return SphereOrb.Blaze;
            case 5:
                return SphereOrb.Snow;
            default:
                return null;
        }
    }

    private static Integer orbToInt(SphereOrb orb) {
        switch (orb) {
            case Lightning:
                return 0;
            case Frost:
                return 1;
            case Dark:
                return 2;
            case Ghostflame:
                return 3;
            case Blaze:
                return 4;
            case Snow:
                return 5;
            default:
                return null;
        }
    }

    @Override
    public Integer onSave() {
        return orbToInt(this.orb);
    }

    @Override
    public void onLoad(Integer n) {
        this.orb = intToOrb(n);
    }

    public enum SphereOrb {
        Lightning,
        Dark,
        Frost,
        Ghostflame,
        Blaze,
        Snow
    }
}
