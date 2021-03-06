package net.k3rnel.unsealed.screens.battle.magic;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.battle.BattleEntity;
import net.k3rnel.unsealed.screens.battle.BattleGrid;
import net.k3rnel.unsealed.screens.battle.BattleHero;

public class PoisonDart extends MagicEntity {
    
    /**
     * 0 = gray. 1 = blue. 2 = red. 3 = green.
     * @param color
     */
    public PoisonDart(TextureAtlas atlas, int color, float speed, BattleEntity entity) {
        super(speed,0,entity);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/fireball" );
        TextureRegion[][] spriteSheet = atlasRegion.split(34, 25);
        TextureRegion[] frames = new TextureRegion[3];
        frames[0] = spriteSheet[color][0];
        frames[1] = spriteSheet[color][1];
        frames[2] = spriteSheet[color][2];
        Animation attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.LOOP);
        this.animations.put("attacking",attacking);
        this.setState(BattleEntity.stateAttacking);

        this.setHeight(30);this.setWidth(30);
        offsetX = (int)entity.getWidth()-120;
        offsetY = 0;
        this.addAction(color(Color.MAGENTA));
        this.setGrid(entity.getGridXInt()-1,entity.getGridYInt());
    }
    
    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.getGridX()<0)
            destroyMe=true;
        for(BattleHero hero : BattleGrid.heroes){
            if(hero.getGridYInt() == this.getGridYInt() && hero.getGridXInt() == this.getGridXInt()){
                Gdx.app.log(Unsealed.LOG,"SMACK!");
                if(hero.getState()==BattleEntity.stateBlocking){
                    
                }else{
                    if( hero.setHp(hero.getHp()-5)){
                        Gdx.app.log(Unsealed.LOG, "The clams have avenged themselves! You died a miserable death");
                        hero.setHp(0);
                    }
                    hero.setStatus(BattleEntity.statusPoisoned);
                }
                destroyMe=true;
            }
        }
    }
}
