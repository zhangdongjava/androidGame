package com.zzz.game.bean;

import com.zzz.game.base.BaseElement;
import com.zzz.game.zhiwuvsani.DieListener;

import java.util.ArrayList;
import java.util.List;


public abstract class AttackPlant extends Plant implements DieListener{
	// ����
	protected List<Bullet> bullets = new ArrayList<Bullet>();
	public AttackPlant(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}
	/**
	 * �������ڹ������ӵ�
	 * 
	 * @return
	 */
	public abstract Bullet createBullet();

	public List<Bullet> getBullets() {
		return bullets;
	}

	@Override
	public void onDie(BaseElement element) {

		if (element instanceof Bullet) {
			bullets.remove(element);
		}
	}
	@Override
	public void BaseAction() {
		// TODO Auto-generated method stub
		
	}
	
}
