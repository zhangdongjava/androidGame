package com.zzz.game.sql;

import org.cocos2d.nodes.CCSprite;

import java.util.HashMap;
import java.util.Map;

/**
 * չʾ��ֲ��
 * 
 * @author Administrator
 * 
 */
public class ShowPlant {
	// id(����) ͼƬ��Դ ���ĵ������� չʾ�õ�ֲ���Ӧ�Ķ�սֲ����
	private int id;
	private CCSprite defaultImg;
	private CCSprite bgImg;// ����͸����
	private int sunNum = 25;

	// ������
	private static Map<Integer, HashMap<String, String>> data;
	static {
		data = new HashMap<Integer, HashMap<String, String>>();
		for (int id = 1; id <= 9; id++) {
			HashMap<String, String> item = new HashMap<String, String>();

			item.put("fileName", String.format(
					"image/fight/chose/choose_default%02d.png", id));
			item.put("sunNum", (id * 25) + "");

			data.put(id, item);
		}
	}

	public ShowPlant(int id) {
		super();
		this.id = id;

		HashMap<String, String> item = data.get(id);

		defaultImg = CCSprite.sprite(item.get("fileName"));
		bgImg = CCSprite.sprite(item.get("fileName"));

		defaultImg.setAnchorPoint(0, 0);
		bgImg.setAnchorPoint(0, 0);

		bgImg.setOpacity(150);

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CCSprite getDefaultImg() {
		return defaultImg;
	}

	public void setDefaultImg(CCSprite defaultImg) {
		this.defaultImg = defaultImg;
	}

	public CCSprite getBgImg() {
		return bgImg;
	}

	public void setBgImg(CCSprite bgImg) {
		this.bgImg = bgImg;
	}

	public int getSunNum() {
		return sunNum;
	}

	public void setSunNum(int sunNum) {
		this.sunNum = sunNum;
	}

}
