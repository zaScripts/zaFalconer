package task.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Npc;

import task.Task;
import zafalconry.ZaFalconry;

public class RetrieveFalcon extends Task
{

	public RetrieveFalcon(MethodContext arg0) 
	{
		super(arg0);
		
	}

	@Override
	public boolean activate()
	{
		final Npc falcon = ctx.npcs.select().id(ZaFalconry.falconID).poll();
		return falcon.isValid() && ctx.players.local().getAnimation()==-1;
			
	}

	@Override
	public void execute() 
	{
		final Npc falcon = ctx.npcs.select().id(ZaFalconry.falconID).poll();
			if(falcon.isOnScreen())
			{

				falcon.interact("Retrieve");
				Condition.wait(new Callable <Boolean>()
						{

					@Override
					public Boolean call() throws Exception 
					{
						return !falcon.isValid();
					}},Random.nextInt(500,800),5);
				
				if(falcon.isValid()) // if it couldn't be retrieved
				{
					return;
				}else
				{
					ZaFalconry.status = "Hunting";
				}
		
			}
			if(!falcon.isOnScreen())
			{

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
		
		

		
		
		
		
	}

}
