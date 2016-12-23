package com.zzz.game.tool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.MotionEvent;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ͨ�ù���
 * 
 * @author Administrator
 * 
 */
public class CommonUtil {
	/**
	 * �汾��Ϣ��ȡ
	 * 
	 * @return
	 */
	public static String getVersion(Context context) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * �������Ƿ��а汾����
	 * 
	 * @return
	 */
	@Deprecated
	public static boolean isUpdate(Context context, String newVersionName) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
			String oldVersionName = packageInfo.versionName;
			if (oldVersionName.compareTo(newVersionName) < 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ���汾����
	 * 
	 * @param context
	 * @param newVersionCode
	 * @return
	 */
	public static boolean isUpdate(Context context, int newVersionCode) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
			int oldVersionCode = packageInfo.versionCode;
			if (oldVersionCode < newVersionCode) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * ������Ϸ��ͼ
	 * @param tmxFile
	 * @return
	 */
	public static CCTMXTiledMap loadMap(String tmxFile)
	{
		CCTMXTiledMap gameMap = CCTMXTiledMap.tiledMap(tmxFile);
		
		//�����ֶ�ƽ�Ƶ�ͼ
		gameMap.setAnchorPoint(0.5f, 0.5f);
		CGSize contentSize = gameMap.getContentSize();
		gameMap.setPosition(contentSize.width / 2, contentSize.height / 2);
		
		return gameMap;
	}
	/**
	 * �ӵ�ͼ�м���ָ�����Ƶĵ㼯��
	 * @param map
	 * @param name
	 * @return
	 */
	public static List<CGPoint> loadPoint(CCTMXTiledMap map,String name)
	{
		CCTMXObjectGroup zombiesGroup = map.objectGroupNamed(name);
		// ��ȡ��ʬλ����Ϣ
		ArrayList<HashMap<String, String>> zombies = zombiesGroup.objects;
		// �ֱ���x��yΪ������ȡ����ֵ��Ϣ---->��װ���㼯����
		List<CGPoint> points = new ArrayList<CGPoint>();
		for (HashMap<String, String> item : zombies) {
			float x = Float.parseFloat(item.get("x"));
			float y = Float.parseFloat(item.get("y"));
			points.add(CGPoint.ccp(x, y));
		}
		return points;
	}

	/**
	 * �ж��Ƿ񱻵��
	 * 
	 * @param event
	 * @param node
	 * @return
	 */
	public static boolean isClicke(MotionEvent event, CCLayer layer, CCNode node) {
		CGPoint point = layer.convertTouchToNodeSpace(event);
		return CGRect.containsPoint(node.getBoundingBox(), point);
	}
	/**
	 * ����һ������֡
	 */
	public static CCAnimate getAnimation(ArrayList<CCSpriteFrame> frames, int num, String filepath){
		if (frames == null) {
			frames = new ArrayList<CCSpriteFrame>();
			// frames��Ϣ����
			for (int i = 1; i <= num; i++) {
				frames.add(CCSprite.sprite(String.format(filepath, i)).displayedFrame());
			}
		}
		CCAnimation animation = CCAnimation.animation("", 0.2f, frames);
		return CCAnimate.action(animation, false);// ֻ����һ��
	}
	/**
	 * ����ѭ������֡
	 */
	public static CCAction getRepeatAnimation(ArrayList<CCSpriteFrame> frames, int num, String filepath){
		if (frames == null) {
			frames = new ArrayList<CCSpriteFrame>();
			for (int i = 1; i <= num; i++) {
				frames.add(CCSprite.sprite(String.format(filepath, i)).displayedFrame());
			}
		}
		CCAnimation anim = CCAnimation.animation("", 0.2f, frames);

		CCAnimate animate = CCAnimate.action(anim);
		return CCRepeatForever.action(animate);
	}
}
