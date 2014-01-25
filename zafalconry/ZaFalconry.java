package zafalconry;

import gui.Painter;


import java.awt.Graphics;
import java.util.ArrayList;

import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;
import org.powerbot.script.methods.Skills;

import task.Task;
import task.tasks.DropItems;
import task.tasks.Hunt;
import task.tasks.RetrieveFalcon;

@Manifest (authors={"theasd"} , name = "zaFalconry" , description = "Hunts spotted kebbits for experience")
public class ZaFalconry extends PollingScript implements PaintListener
{
	private ArrayList<Task> taskList = new ArrayList<Task>();
	
	public static int startingExp;
	public static int startingLevel;
	public static long startingTime;
	public static final int falconID =5094;
	public static final int kebbitID =5098;
	private final Painter painter = new Painter(ctx);
	
	public static String status;

	
	@Override
	public void start()
	{
		taskList.add(new DropItems(ctx));
		taskList.add(new RetrieveFalcon(ctx));
		taskList.add(new Hunt(ctx));
		
		status ="Starting";
		startingTime = System.currentTimeMillis();
		startingExp = ctx.skills.getExperience(Skills.HUNTER);
		startingLevel = ctx.skills.getLevel(Skills.HUNTER);
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

	@Override
	public void repaint(Graphics g) 
	{
		painter.draw(g);
	}
	


}
