package calico.plugins.iip;

import calico.networking.netstuff.CalicoPacket;

public class IntentionalInterfacesNetworkCommands
{
	public static final int CIC_CREATE = Command.CIC_CREATE.id;
	public static final int CIC_MOVE = Command.CIC_MOVE.id;
	public static final int CLINK_CREATE = Command.CLINK_CREATE.id;
	public static final int CLINK_RETYPE = Command.CLINK_RETYPE.id;
	public static final int CLINK_MOVE_ANCHOR = Command.CLINK_MOVE_ANCHOR.id;
	public static final int CLINK_DELETE = Command.CLINK_DELETE.id;

	public enum Command
	{
		/**
		 * Create a new CIntentionCell
		 */
		CIC_CREATE,
		/**
		 * Move a CIntentionCell's (x,y) position
		 */
		CIC_MOVE,
		/**
		 * Delete a CIntentionCell
		 */
		CIC_DELETE,
		/**
		 * Create a new CCanvasLink
		 */
		CLINK_CREATE,
		/**
		 * Change the type of a CCanvasLink
		 */
		CLINK_RETYPE,
		/**
		 * Move one enpoint of a CCanvasLink
		 */
		CLINK_MOVE_ANCHOR,
		/**
		 * Delete a CCanvasLink
		 */
		CLINK_DELETE;

		public final int id;

		private Command()
		{
			this.id = ordinal() + OFFSET;
		}

		public boolean verify(CalicoPacket p)
		{
			return forId(p.getInt()) == this;
		}

		private static final int OFFSET = 2300;

		public static Command forId(int id)
		{
			return Command.values()[id - OFFSET];
		}
	}
}
