package com.zzz.game.bean;

import com.zzz.game.base.BaseElement;

public abstract class Bullet extends BaseElement {
	protected int attack = 10;// ������
	protected int speed = 60;// �ƶ��ٶ�
	public Bullet(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}
	public abstract void BaseAction();
	/**
	 * �ƶ�
	 */
	public abstract void move();

	public int getAttack() {
		return attack;
	}
	
}
