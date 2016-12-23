package com.zzz.game.bean.impl;

import com.zzz.game.bean.AttackPlant;
import com.zzz.game.bean.Bullet;
import com.zzz.game.tool.CommonUtil;
import org.cocos2d.nodes.CCSpriteFrame;

import java.util.ArrayList;

import static org.cocos2d.types.CGPoint.ccp;


public class PeacePlant extends AttackPlant {
	private static ArrayList<CCSpriteFrame> shakeFrames;// ҡ��
	public PeacePlant() {
		super("image/plant/pease/p_2_01.png");
		baseAction();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Bullet createBullet() {
		// TODO Auto-generated method stub
		if(bullets.size()<1){
			CommonPease pease = new CommonPease();
			pease.setPosition(ccp(getPosition().x + 20, getPosition().y + 35));
			bullets.add(pease);
			pease.setDieListener(this);
			this.getParent().addChild(pease);
			pease.move();
		}
		return null;
	}

	public void baseAction() {
		this.runAction(CommonUtil.getRepeatAnimation(shakeFrames, 8,
				"image/plant/pease/p_2_%02d.png"));

	}
}
