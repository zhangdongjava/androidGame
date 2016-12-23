package com.zzz.game.bean.impl;

import com.zzz.game.bean.DefancePlant;
import org.cocos2d.nodes.CCSpriteFrame;

import java.util.ArrayList;


public class PlantNut extends DefancePlant {
	private static ArrayList<CCSpriteFrame> shakeFrames;// ҡ������֡

	public PlantNut() {
		super("image/plant/nut/p_3_01.png");
		// TODO Auto-generated constructor stub

	}

	@Override
	public void BaseAction() {
		// TODO Auto-generated method stub
		this.runAction(com.zzz.game.tool.CommonUtil.getRepeatAnimation(shakeFrames, 11,
				"image/plant/nut/p_3_%02d.png"));
	}

}
