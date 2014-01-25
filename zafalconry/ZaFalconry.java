package zafalconry;

import java.util.ArrayList;

import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;

import task.Task;
import task.tasks.DropItems;
import task.tasks.Hunt;
import task.tasks.RetrieveFalcon;

@Manifest (authors={"theasd"} , name = "zaFalconry" , description = "Hunts spotted kebbits for experience")
public class ZaFalconry extends PollingScript
{
	private ArrayList<Task> taskList = new ArrayList<Task>();

	@Override
	public void start()
	{
		taskList.add(new Hunt(ctx));
		taskList.add(new RetrieveFalcon(ctx));
		taskList.add(new DropItems(ctx));
	}
	
	@Override
	public int poll() 
	{
		for(Task task:taskList)
		{
			if(task.activate())
				task.execute();
		}
		return 100;
	}

}
