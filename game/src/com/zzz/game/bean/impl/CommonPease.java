package com.zzz.game.bean.impl;

import com.zzz.game.bean.Bullet;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

/**
 * ��ͨ�㶹
 * 
 * @author Administrator
 * 
 */
public class CommonPease extends Bullet {

	public CommonPease() {
		super("image/fight/bullet.png");
		setScale(0.65f);
		speed = 100;
		attack = 10;
	}

	@Override
	public void move() {
		CGPoint pos = CGPoint.ccp(
				CCDirector.sharedDirector().winSize().width - 20,
				this.getPosition().y);
		// ���
		// �յ㣺ƽ�ƣ�x���޸ģ�

		float t = CGPointUtil.distance(getPosition(), pos) / speed;
		CCMoveTo moveTo = CCMoveTo.action(t, pos);

		this.runAction(CCSequence.actions(moveTo,
				CCCallFunc.action(this, "destroy")));

	}



	@Override
	public void BaseAction() {
		// TODO Auto-generated method stub
		
	}




}
