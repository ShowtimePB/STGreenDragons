package ShowtimeScripts.dead.STGreenDragons.task.travel;

import ShowtimeScripts.dead.STGreenDragons.main.STGreenDragons;
import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.World;

public class WorldHop extends Task{

	public WorldHop(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.data = data;
	}

	private int CANNON_ID = 6;

	@Override
	public boolean activate(){

		if(ctx.objects.select().id(CANNON_ID).within(20).poll().valid() && !ctx.npcs.select().within(12).poll().interacting().equals(ctx.players.local().valid()) && !ctx.players.local().interacting().valid() && !ctx.npcs.select().within(6).poll().valid())
			return true;
		return false;
	}

	@Override
	public void execute(){

		int[] memWorlds = {2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 20, 21, 22, 23, 24, 27, 28, 29,
				30, 31, 32, 33, 34, 36, 38, 39, 40, 41, 42, 43, 44, 46, 47, 48, 51, 52, 54, 55, 56, 57, 58, 59, 60, 62, 65,
				67, 68, 69, 70, 74, 75, 76, 77, 78, 86, 87, 88, 89, 90, 92};


		Condition.sleep(Random.nextInt(75, 125));
		int num = Random.nextInt(0, memWorlds.length);
		System.out.println("Chosen world: " + num);
		STGreenDragons.status = "Hopping worlds...";
		World w;
		w = new World(ctx, num, World.NIL.size(), World.Type.MEMBERS, World.Server.RUNE_SCAPE, World.Specialty.NONE);
		w.hop();
		Condition.wait(() -> !ctx.game.loggedIn(), 100, 35);
		STGreenDragons.hopCount++;
		ctx.game.tab(Game.Tab.INVENTORY);


	}

	private DataContainer data;

	public DataContainer getData(){
		return data;
	}

	public void setData(DataContainer data){
		this.data = data;
	}
}
