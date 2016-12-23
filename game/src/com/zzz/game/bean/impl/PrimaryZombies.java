package com.zzz.game.bean.impl;

import com.zzz.game.base.BaseElement;
import com.zzz.game.bean.Plant;
import com.zzz.game.bean.Zombies;
import com.zzz.game.control.GameController;
import com.zzz.game.tool.CommonUtil;
import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import java.util.ArrayList;

public class PrimaryZombies extends Zombies{
	private ArrayList<CCSpriteFrame> moveFrams;
	private ArrayList<CCSpriteFrame> attackFrame;
	private boolean isAttack = false;// ��¼��ʬ�Ĺ���״̬
	private BaseElement target;// ��¼����Ŀ��
	private static ArrayList<CCSpriteFrame> headFrame;// ͷ������
	private static ArrayList<CCSpriteFrame> dieFrame;// ſ�ŵ���
	private static String headRes = "image/zombies/zombies_1/head/z_1_head_%02d.png";
	private static String dieRes = "image/zombies/zombies_1/die/z_1_die_%02d.png";
	
	private boolean isDie = false;

	public PrimaryZombies(CGPoint start, CGPoint end) {
		super("image/zombies/zombies_1/walk/z_1_01.png");
		// TODO Auto-generated constructor stub
		this.startPoint = start;
		this.endPoint = end;
		//����㿪ʼ��
		this.setPosition(start);
		move();
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		//�����ٶ�
		float t = CGPointUtil.distance(startPoint, endPoint)/speed;
		CCMoveTo ccMoveTo = CCMoveTo.action(t, endPoint);
		CCSequence ccSequence = CCSequence.actions(ccMoveTo, CCCallFunc.action(this, "gameOver"));
		this.runAction(ccSequence);
		//���߱߶�
		this.runAction(CommonUtil.getRepeatAnimation(moveFrams, 7, "image/zombies/zombies_1/walk/z_1_%02d.png"));
	}
	//��Ϸ����
	public void gameOver(){
		this.removeSelf();
		GameController.getInstance().GameOver();
	}
	
	
	@Override
	public void attack(BaseElement element) {
		// TODO Auto-generated method stub
		if(!isAttack){
			isAttack = true;
			this.target = element;
			this.stopAllActions();
			// ���Ź�������֡
			this.runAction(CommonUtil.getRepeatAnimation(attackFrame, 10,
								"image/zombies/zombies_1/attack/z_1_attack_%02d.png"));
			CCScheduler.sharedScheduler().schedule("attackPlant", this, 0.5f,
					false);
		}
		
		
	}
	//����Ҫ�Ӳ���float t
	public void attackPlant(float t){
		if(target instanceof Plant){
			Plant plant = (Plant) target;
			plant.attacked(attack);
			//ֲ�ﱻ������
			if(plant.getLife()<0){
				plant.destroy();
				this.stopAllActions();
				this.move();
			}
		}
		
	}

	@Override
	public void attacked(int attack) {
		// TODO Auto-generated method stub
		life -= attack;
	
		if (life <= 0 && !isDie) {
			isDie = true;
			// �Դ�����
			// ������ſ�ڵ���
			
			// �Դ����������������������ϣ�����
			this.stopAllActions();
			CCAnimate head = (CCAnimate) CommonUtil.getAnimation(headFrame, 6,
					headRes);
			CCAnimate die = (CCAnimate) CommonUtil.getAnimation(dieFrame, 6,
					dieRes);
			// ���Ž�ʬ���µĶ���
			this.runAction(CCSequence.actions(head, die,
					CCCallFunc.action(this, "destroy")));
			
		}
	}

	@Override
	public void BaseAction() {
		// TODO Auto-generated method stub
		
	}
}
