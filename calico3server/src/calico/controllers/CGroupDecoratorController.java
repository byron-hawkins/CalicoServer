package calico.controllers;

import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.util.ArrayList;

import it.unimi.dsi.fastutil.longs.Long2ReferenceArrayMap;
import calico.clients.ClientManager;
import calico.components.CGroup;
import calico.components.decorators.CListDecorator;
import calico.networking.netstuff.CalicoPacket;
import calico.networking.netstuff.NetworkCommand;

public class CGroupDecoratorController {
	
	public static Long2ReferenceArrayMap<Boolean> groupCheckValues = new Long2ReferenceArrayMap<Boolean>();
	public static ArrayList<Integer> groupDecoratorCommands = new ArrayList<Integer>();

	private static void no_notify_decorator_create(long guuid, long uuid,
			CGroup groupToCreate) {
		if (!CGroupController.exists(guuid)) { return; }
		long cuid = CGroupController.groups.get(guuid).getCanvasUUID();
		no_notify_decorator_load(uuid, cuid, groupToCreate);
		
	}

	private static void no_notify_decorator_load(long uuid, long cuuid,
			CGroup groupToLoad) {
		if(CGroupController.exists(uuid))
		{
			CGroupController.logger.debug("Need to delete group "+uuid);
		}
		
		CCanvasController.set_group_canvas(uuid, cuuid);
		
		// Add to the Groups
		CGroupController.groups.put(uuid, groupToLoad);
		
		CCanvasController.canvases.get(cuuid).addChildGroup(uuid);
	}
	
	public static void list_create(long guuid, long uuid)
	{
		no_notify_list_create(guuid, uuid);
		ClientManager.send(CalicoPacket.getPacket(NetworkCommand.LIST_CREATE, guuid, uuid));
	}
	
	public static void no_notify_list_create(long guuid, long uuid)
	{
		if (!CGroupController.groups.get(guuid).isPermanent())
			CGroupController.no_notify_set_permanent(guuid, true);
		
		long[] cstrokes = CGroupController.groups.get(guuid).getChildStrokes(); 
		if (cstrokes.length > 0)
		{
			for (int i = cstrokes.length - 1; i >= 0; i--)
			{
				CGroupController.groups.get(guuid).deleteChildStroke(cstrokes[i]);
				CStrokeController.strokes.get(cstrokes[i]).setParentUUID(0);
			}
		}
		
		Rectangle bounds = CGroupController.groups.get(guuid).getBoundsOfContents();
		CGroupController.no_notify_make_rectangle(guuid, bounds.x, bounds.y, bounds.width, bounds.height);
		
		CGroup groupToCreate = new CListDecorator(guuid, uuid);
		no_notify_decorator_create(guuid, uuid, groupToCreate);
		((CListDecorator)CGroupController.groups.get(uuid)).resetListElementPositions(true);
		groupToCreate.recomputeBounds();
		
		if (cstrokes.length > 0)
		{
			for (int i = cstrokes.length - 1; i >= 0; i--)
			{
				CStrokeController.strokes.get(cstrokes[i]).calculateParent();
			}
		}
	}
	
	public static void no_notify_list_load(long guuid, long uuid, long cuuid, long puuid)
	{
		CGroup groupToLoad = new CListDecorator(guuid, uuid, cuuid, puuid);
		no_notify_decorator_load(uuid, cuuid, groupToLoad);
	}
		
	public static void list_set_check(long luuid, long cuid, long puid, long guuid, boolean value)
	{
		no_notify_list_set_check(luuid, cuid, puid, guuid, value);
		ClientManager.send(CalicoPacket.getPacket(NetworkCommand.LIST_CHECK_SET, luuid, cuid, puid, guuid, value));
	}
	
	public static void no_notify_list_set_check(long luuid, long cuid, long puid, long guuid, boolean value)
	{
		if (!CGroupController.exists(luuid)) { return; }
		
		((CListDecorator)CGroupController.groups.get(luuid)).setCheck(guuid, value);
		((CListDecorator)CGroupController.groups.get(luuid)).recomputeValues();
	}
	
	public static void registerGroupDecoratorCommand(String type)
	{
		Class<?> rootClass = NetworkCommand.class;
		Field[] fields = rootClass.getFields();
		
		try
		{
			for (int i = 0; i < fields.length; i++)
			{
				if (fields[i].getType() == int.class)
				{
					if (fields[i].getName().length() >= type.length() && fields[i].getName().substring(0, type.length()).compareTo(type) == 0)
					{
						fields[i].setAccessible(true);
						int command = fields[i].getInt(NetworkCommand.class);
						groupDecoratorCommands.add(new Integer(command));
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static boolean isGroupDecoratorCommand(int comm)
	{
		for (Integer i : groupDecoratorCommands)
			if (i.intValue() == comm)
				return true;
		return false;
	}
	
}
