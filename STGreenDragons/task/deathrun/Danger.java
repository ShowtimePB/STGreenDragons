package ShowtimeScripts.dead.STGreenDragons.task.deathrun;

import ShowtimeScripts.dead.STGreenDragons.main.STGreenDragons;
import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import ShowtimeScripts.dead.STGreenDragons.misc.Walker;
import ShowtimeScripts.dead.STGreenDragons.task.fighting.Eat;
import ShowtimeScripts.dead.STGreenDragons.task.travel.WorldHop;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Prayer;

public class Danger extends Task{

	private DataContainer data;

	Tile bottomLeftEdgevilleBank = new Tile(3085, 3486);
	Tile topRightEdgevilleBank = new Tile(3100, 3502);

	Area edgevilleBankArea = new Area(bottomLeftEdgevilleBank, topRightEdgevilleBank);

	public static final Tile[] pathToDragons = {new Tile(3093, 3494, 0), new Tile(3097, 3496, 0), new Tile(3099, 3501, 0), new Tile(3102, 3505, 0), new Tile(3104, 3509, 0), new Tile(3105, 3513, 0), new Tile(3104, 3517, 0), new Tile(3106, 3522, 0), new Tile(3106, 3526, 0), new Tile(3106, 3530, 0), new Tile(3107, 3534, 0), new Tile(3108, 3538, 0), new Tile(3108, 3542, 0), new Tile(3108, 3546, 0), new Tile(3105, 3549, 0), new Tile(3105, 3553, 0), new Tile(3105, 3557, 0), new Tile(3105, 3561, 0), new Tile(3105, 3565, 0), new Tile(3106, 3569, 0), new Tile(3106, 3574, 0), new Tile(3106, 3578, 0), new Tile(3105, 3582, 0), new Tile(3105, 3586, 0), new Tile(3105, 3590, 0), new Tile(3105, 3594, 0), new Tile(3105, 3598, 0), new Tile(3104, 3603, 0), new Tile(3104, 3607, 0), new Tile(3106, 3611, 0), new Tile(3109, 3614, 0), new Tile(3109, 3618, 0), new Tile(3109, 3622, 0), new Tile(3111, 3626, 0), new Tile(3114, 3629, 0), new Tile(3115, 3633, 0), new Tile(3117, 3637, 0), new Tile(3117, 3641, 0), new Tile(3117, 3645, 0), new Tile(3118, 3649, 0), new Tile(3120, 3653, 0), new Tile(3121, 3658, 0), new Tile(3123, 3662, 0), new Tile(3123, 3666, 0), new Tile(3126, 3670, 0), new Tile(3126, 3674, 0), new Tile(3130, 3676, 0), new Tile(3133, 3679, 0), new Tile(3135, 3683, 0), new Tile(3135, 3687, 0), new Tile(3135, 3692, 0), new Tile(3136, 3696, 0), new Tile(3138, 3700, 0), new Tile(3138, 3704, 0)};


	public DataContainer getData(){
		return data;
	}

	public void setData(DataContainer data){
		this.data = data;
	}

	public Danger(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.setData(data);
	}

	@Override
	public boolean activate(){
		return STGreenDragons.teleblocked;
	}

	@Override
	public void execute(){
		STGreenDragons.status = "Being PK'd, get out of here!";

		while(STGreenDragons.teleblocked && ctx.controller.isStopping()){

			Eat pleaseHealMe = new Eat(ctx, data);

			if(ctx.combat.healthPercent() < 70){
				pleaseHealMe.execute();
			}

			if(ctx.skills.level(Constants.SKILLS_PRAYER) >= 37 && !ctx.prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MAGIC) && ctx.prayer.prayerPoints() > 0){
				ctx.prayer.prayer(Prayer.Effect.PROTECT_FROM_MAGIC, true);
			}

			if(!ctx.movement.running() && ctx.client().getRunPercentage() > Random.nextInt(5, 15)){
				ctx.widgets.component(160, 27).click();
			}


			Condition.sleep(Random.nextInt(30, 60));
			Walker walker = new Walker(ctx);
			walker.walkPathReverse(pathToDragons);

			if(edgevilleBankArea.contains(ctx.players.local().tile())){
				STGreenDragons.teleblocked = false;
				System.out.println("Setting teleblocked to false");
				WorldHop wh = new WorldHop(ctx, data);
				wh.execute();
			}


		}


	}

}
