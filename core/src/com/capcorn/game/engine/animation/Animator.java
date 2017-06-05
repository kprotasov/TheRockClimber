package com.capcorn.game.engine.animation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kprotasov on 06.02.2016.
 */
public class Animator {

    /**
     * Use to move
     */

    public HashMap<TweenAnimation, TweenAnimation> animationMap;
    public Animator(){
        animationMap = new HashMap<TweenAnimation, TweenAnimation>();
    }

    public void createAnimation(final TweenAnimation animation){
        if(!animationMap.containsKey(animation)){
            animationMap.put(animation, animation);
        }
    }

    public void removeAnimation(final TweenAnimation animation){
        if(!animationMap.containsKey(animation)){
            animationMap.remove(animation);
        }
    }

    public void update (final float delta){
        for (Map.Entry<TweenAnimation, TweenAnimation> entry : animationMap.entrySet()){
            entry.getValue().update(delta);
        }
    }



}
