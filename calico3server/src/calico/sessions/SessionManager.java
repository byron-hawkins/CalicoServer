package calico.sessions;

import java.util.*;
import java.net.*;
import java.io.*;

import calico.*;
import calico.networking.*;
import calico.admin.*;
import calico.clients.*;
import calico.components.*;
import calico.networking.netstuff.*;

import it.unimi.dsi.fastutil.objects.*;
import it.unimi.dsi.fastutil.ints.*;

import org.apache.log4j.*;


public class SessionManager
{
	// Maps the names of sessions to their actual ID
	private static ObjectArrayList<String> name2sessionid = new ObjectArrayList<String>();
	
	// This holds the sessions
	private static Int2ReferenceArrayMap<Session> sessions = new Int2ReferenceArrayMap<Session>();


	/**
	 * Converts a session name to the actual SessionID number
	 * @param name
	 * @deprecated
	 * @return session ID
	 */
	public static int name2session(String name)
	{
		return name2sessionid.indexOf(name);
	}
	
	/**
	 * Maps a sessionid->session name
	 * @param sid
	 * @deprecated
	 * @return name of the session
	 */
	public static String sessionid2name(int sid)
	{
		return name2sessionid.get(sid);
	}

	@Deprecated
	public static ArrayList<SessionInfo> getSessionList()
	{
		ArrayList<SessionInfo> slist = new ArrayList<SessionInfo>();

		for(int i=0;i<name2sessionid.size();i++)
		{
			slist.add( sessions.get(i).getSessionInfo() );
		}

		return slist;
	}

	@Deprecated
	public static boolean checkAuth(int sid, String user, String pass)
	{
		// TODO We should probably implement this
		return true;
	}

	@Deprecated
	public static void joinClient(int sid, Client c, String username)
	{
		//int clientid = ClientManager.getClientID(c);

		// add to session
		//ClientManager.setSession(clientid, sid);
		
		// Join them to the session
		//sessions.get(sid).joinClient(clientid);
	}

	@Deprecated
	public static void dropClient(int sid, int clientid)
	{
		// Join them to the session
		sessions.get(sid).dropClient(clientid);
	}

	@Deprecated
	public static SessionInfo getSessionInfo(int sid)
	{
		return sessions.get(sid).getSessionInfo();
	}

	@Deprecated
	public static Session getSession(int sid)
	{
		return sessions.get(sid);
	}

	@Deprecated
	public static int[] getClientsInSession(int sid)
	{
		return sessions.get(sid).getClientIDList();
	}

	@Deprecated
	public static void log_info(int sid, String m)
	{
		//sessionLogs.get(sid).info(m);
	}
	
	
	/**
	 * This will send the specified packet to all clients in the specified session
	 * @param sessionid
	 * @deprecated
	 * @param p
	 */
	public static void send(int sessionid, CalicoPacket p)
	{
		/*if(p==null || sessionid==-1)
			return;
		
		ArrayList<Client> clist = ClientManager.getClientsInSession( sessionid );

		for(int i=0;i<clist.size();i++)
		{
			ClientManager.send(clist.get(i), p);
		}*/
	}


	@Deprecated
	public static void createSession(String sname, int rows, int cols)
	{
		// add the session
		name2sessionid.add(sname);
		int sessionid = name2session(sname);

		// Create the session

		Session sess = new Session(sessionid,sname,rows,cols);

		sessions.put(sessionid, sess);


		// Need to setup the logger
	}

}
