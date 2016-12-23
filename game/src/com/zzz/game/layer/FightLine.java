package com.zzz.game.layer;

import com.zzz.game.base.BaseElement;
import com.zzz.game.base.BaseLayer;
import com.zzz.game.bean.AttackPlant;
import com.zzz.game.bean.Bullet;
import com.zzz.game.bean.Plant;
import com.zzz.game.bean.Zombies;
import com.zzz.game.zhiwuvsani.DieListener;
import org.cocos2d.actions.CCScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FightLine extends BaseLayer implements DieListener {
	private int lineNum;
	private List<Zombies> zombiesList;// ��ǰ����ӵĽ�ʬ����

	private Map<Integer, Plant> plants;// ��ǰ����ӵ�ֲ�Ｏ��
	private List<AttackPlant> attackPlantList;// ��ǰ����ӵĹ�����ֲ��

	public FightLine(int lineNum) {
		super();
		this.lineNum = lineNum;
		zombiesList = new ArrayList<Zombies>();

		plants = new HashMap<Integer, Plant>();
		attackPlantList = new ArrayList<AttackPlant>();
		// ��ʬ����ֲ��
				// ÿ��һ��ʱ�䣬�жϵ�ǰ���Ƿ��н�ʬ���жϵ�ǰ���Ƿ���ֲ������н�ʬѭ������ʬ�Ľ����Ƿ���ֲ��
				CCScheduler.sharedScheduler()
						.schedule("attackPlant", this, 0.5f, false);

				// ÿ��һ��ʱ�䣬�жϵ�ǰ���Ƿ��н�ʬ���жϵ�ǰ���Ƿ��й���ֲ������й�����ֲ��ѭ����������ʬ
				CCScheduler.sharedScheduler().schedule("attackZombies", this, 0.5f,
						false);

				// ÿ��һ��ʱ�䣬�жϵ�ǰ���Ƿ��н�ʬ���жϵ�ǰ���Ƿ��й���ֲ���ȡ�����й�����ֲ��ĵ��У��ж������ӵ����Ƿ���н�ʬ
				CCScheduler.sharedScheduler()
						.schedule("checkBullet", this, 0.2f, false);
}
	//��ӣ�����ɹ�����true
	public void addPlant(Plant plant){
		plant.setDieListener(this);
			plants.put(plant.getRow(), plant);
			//����ǹ����͵ģ���Ҫ����attackPlantList
			if(plant instanceof AttackPlant){
				attackPlantList.add((AttackPlant)plant);
			}

	}
	//������ʬ
	public void attackZombies(float t){
		if (zombiesList.size() > 0 && attackPlantList.size() > 0) {
			for (AttackPlant item : attackPlantList) {
				item.createBullet();
			}
		}
	}
	//�ӵ���ʬ
	public void checkBullet(float t){
		if(zombiesList.size()>0&&attackPlantList.size()>0){
			for(AttackPlant attackPlant:attackPlantList){
				List<Bullet> bullets = attackPlant.getBullets();//��ȡ�ӵ�������ֲͬ���в�ͬ�ӵ�
				for(Bullet bullet :bullets){
					for(Zombies zombies :zombiesList){
						float left = zombies.getPosition().x-20;
						float right = zombies.getPosition().x +20;
						float position = bullet.getPosition().x;
						//ȡһ����Χ�����Ƿ�ֹ����0.2s���´��һЩ�ж�
						if(left<position&&position<right){
							zombies.attacked(bullet.getAttack());
							bullet.destroy();
						}
					}
				}
			}
		}
	}
	public boolean isAdd(Plant plant){
		return !plants.containsKey(plant.getRow());
	}
	public void addZombies(Zombies zombies){
		zombiesList.add(zombies);
		zombies.setDieListener(this);
	}
	//��ʬ����ֲ��
	public void attackPlant(float t){
		if(plants.size()>0&&zombiesList.size()>0){
			for(Zombies zombies :zombiesList){
				int key = (int)zombies.getPosition().x/46- 1;
				Plant plant = plants.get(key);
				if (plant != null) {
					zombies.attack(plant);
				}
			}
		}
	}
	@Override
	public void onDie(BaseElement element) {
		// TODO Auto-generated method stub
		if (element instanceof Plant) {
			int key = ((Plant) element).getRow();
			plants.remove(key);

			if (element instanceof AttackPlant) {
				attackPlantList.remove(element);
			}
		} else if(element instanceof Zombies){
			zombiesList.remove(element);
		}
	}
}
