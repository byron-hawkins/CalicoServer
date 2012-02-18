package calico.plugins.iip;

import java.awt.Point;

public class CCanvasLinkAnchor
{
	enum Type
	{
		FLOATING,
		INTENTION_CELL;
	}

	private long uuid;
	private long canvas_uuid;
	private long group_uuid;
	private Type type;
	private Point point;

	public CCanvasLinkAnchor(long uuid, long canvas_uuid)
	{
		this.uuid = uuid;
		this.canvas_uuid = canvas_uuid;
		this.group_uuid = 0L;
		type = Type.FLOATING;
		point = new Point();
	}

	public CCanvasLinkAnchor(long uuid, long canvas_uuid, Type type, int x, int y)
	{
		this(uuid, canvas_uuid);

		this.type = type;
		point.x = x;
		point.y = y;
	}

	public CCanvasLinkAnchor(long uuid, long canvas_uuid, long group_uuid, Type type, int x, int y)
	{
		this(uuid, canvas_uuid, type, x, y);

		this.group_uuid = group_uuid;
	}

	public long getId()
	{
		return uuid;
	}

	public long getCanvasId()
	{
		return canvas_uuid;
	}

	public long getGroupId()
	{
		return group_uuid;
	}

	public Type getType()
	{
		return type;
	}

	public Point getPoint()
	{
		return point;
	}

	public void move(long canvas_uuid, long group_uuid, int x, int y)
	{
		this.canvas_uuid = canvas_uuid;
		this.group_uuid = group_uuid;
		point.x = x;
		point.y = y;
	}
}
