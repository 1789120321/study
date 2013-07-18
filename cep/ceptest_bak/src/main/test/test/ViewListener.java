package test;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import example.model.User;

public class ViewListener implements UpdateListener {
	public static String[] user = new String[3];
	public static int i = 0;

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			user[i] = "";
			Object users = newEvents[0].get("prevwindow(user)");
			for (User u : (User[]) users) 
				user[i] += u + " ";
			i++;
		}
	}

}
