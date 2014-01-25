package zafalconry;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
	
	private int startingExp;
	private int startingLevel;
	private long startingTime;
	
	@Override
	public void start()
	{
		taskList.add(new Hunt(ctx));
		taskList.add(new RetrieveFalcon(ctx));
		taskList.add(new DropItems(ctx));
		
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
		final int expGained = ctx.skills.getExperience(Skills.HUNTER) - startingExp;
		final int currentLevel = ctx.skills.getLevel(Skills.HUNTER);
		final int levelsGained = currentLevel - startingLevel;
		final long currentTime = System.currentTimeMillis();
		final long runTime = currentTime-startingTime;
		final long expPerHour = (expGained * 3600000L)/runTime;
		
		g.setColor(new Color(0,0,0,180));
		g.fillRect(0, 0, 400, 65);
		g.setColor(Color.WHITE);
		g.setFont(new Font(("Tahoma"),Font.PLAIN,12));
		g.drawString("Run Time: "+getRunTimeString(runTime),10,20);
		g.drawString("Experience: "+expGained,170,20);
		g.drawString("Exp/Hour: "+expPerHour,10,40);
		g.drawString("Level: "+currentLevel+"("+levelsGained+")",170 ,40 );
	}
	
	private String getRunTimeString(long millis)
	{
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        return hours+"h "+minutes+"m "+seconds+"s";
	}

}
