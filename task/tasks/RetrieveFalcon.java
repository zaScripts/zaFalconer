package task.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Npc;

import task.Task;

public class RetrieveFalcon extends Task
{

	public RetrieveFalcon(MethodContext arg0) 
	{
		super(arg0);
		
	}

	@Override
	public boolean activate()
	{
		return ctx.players.local().getAnimation() == -1;
	}

	@Override
	public void execute() 
	{
		final Npc falcon = ctx.npcs.select().id(5094).nearest().poll();
		
		if(falcon.isValid())
		{
			System.out.println("retrieving falcon!");
			if(falcon.isOnScreen())
		
			{
		falcon.interact("Retrieve");
		Condition.wait(new Callable <Boolean>()
		{

			@Override
			public Boolean call() throws Exception 
			{
				return !falcon.isValid();
			}},Random.nextInt(400, 500),5);
		
			}else //if falcon is not on the screen
			{
				ctx.camera.turnTo(falcon.getLocation());
				if(falcon.isOnScreen())
					return;
				ctx.movement.stepTowards(falcon.getLocation());
				Condition.wait(new Callable <Boolean>()
						{

							@Override
							public Boolean call() throws Exception 
							{
								return ctx.players.local().getAnimation() ==-1;
							}
							
						},Random.nextInt(400, 500),5);
			}
		
		//if falcon is still valid, try retrieving it again
		if(falcon.isValid())
			execute();
		}
		
		
		
		
	}

}
