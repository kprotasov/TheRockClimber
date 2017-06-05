package com.capcorn.game.engine.pool;

import com.badlogic.gdx.Gdx;
import com.capcorn.game.engine.sprite.SpriteBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kprotasov on 14.07.2016.
 */
public class ObjectPool {

    private HashMap<SpriteBase, Class> objectsMap;
    private HashMap<SpriteBase, Class> usedObjectsMap;
    private HashMap<SpriteBase, Class> unusedObjectsMap;

    public ObjectPool() {
        objectsMap = new HashMap<SpriteBase, Class>();
        usedObjectsMap = new HashMap<SpriteBase, Class>();
        unusedObjectsMap = new HashMap<SpriteBase, Class>();
    }

    // add n elements of given class
    public void create(final Class<? extends SpriteBase> clazz, final int size) throws IllegalAccessException, InstantiationException {
        for (int i = 0; i < size; i++) {
            final SpriteBase spriteBase = clazz.newInstance();
            objectsMap.put(spriteBase, clazz);
            unusedObjectsMap.put(spriteBase, clazz);
            Gdx.app.log("ObjectPool", "create object for class" + clazz);
        }
        Gdx.app.log("ObjectPool", "objectsMap size " + objectsMap.size());
    }

    public synchronized SpriteBase get(final Class<? extends SpriteBase> clazz) throws IllegalAccessException, InstantiationException {
        SpriteBase spriteBase;
        // есть в общем списке но при этом не в используемых
        if (objectsMap.containsValue(clazz) && unusedObjectsMap.containsValue(clazz)) {
            spriteBase = getFirstAtClass(clazz);
            synchronized (unusedObjectsMap) {
                unusedObjectsMap.remove(spriteBase);
            }
            if (spriteBase == null) {
                spriteBase = createNewSpriteBase(clazz);
                Gdx.app.log("ObjectPool", "create new because is null");
            }else{
                Gdx.app.log("ObjectPool", "get instance from map");
            }
        } else {
            spriteBase = createNewSpriteBase(clazz);
            Gdx.app.log("ObjectPool", "not enaugh items, create new");
        }
        spriteBase.release();
        return spriteBase;
    }

    private SpriteBase createNewSpriteBase(final Class<? extends SpriteBase> clazz) throws IllegalAccessException, InstantiationException {
        SpriteBase spriteBase = clazz.newInstance();
        objectsMap.put(spriteBase, clazz);
        return spriteBase;
    }

    private SpriteBase getFirstAtClass(final Class<? extends SpriteBase> clazz) {
        for (Map.Entry<SpriteBase, Class> entry : unusedObjectsMap.entrySet()) {
            if (entry.getValue() == clazz) {
                return entry.getKey();
            }
        }
        return null;
    }

    public synchronized void release(final SpriteBase spriteBase) {
        synchronized (unusedObjectsMap) {
            unusedObjectsMap.put(spriteBase, objectsMap.get(spriteBase));
        }
        Gdx.app.log("ObjectPool", "release item");
    }

}
