package ShowtimeScripts.dead.STGreenDragons.task.travel;

import ShowtimeScripts.dead.STGreenDragons.main.STGreenDragons;
import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import ShowtimeScripts.dead.STGreenDragons.misc.Walker;
import org.powerbot.script.Area;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class WalkToDragons extends Task{
	
	public WalkToDragons(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.data = data;
	}
	
	private DataContainer data;
	
	Tile bottomLeftEdgevilleBank = new Tile(3089, 3486);
	Tile topRightEdgevilleBank = new Tile(3100, 3502);
	Area edgevilleBankArea = new Area(bottomLeftEdgevilleBank, topRightEdgevilleBank);
	
	Tile bottomLeftGreenDragon = new Tile(3134, 3690);
	Tile topRightGreenDragon = new Tile(3168, 3717);
	Area greenDragonArea = new Area(bottomLeftGreenDragon, topRightGreenDragon);
	
	public static final Tile[] pathToDragons = {new Tile(3093, 3494, 0), new Tile(3097, 3496, 0), new Tile(3099, 3501, 0), new Tile(3102, 3505, 0), new Tile(3104, 3509, 0), new Tile(3105, 3513, 0), new Tile(3104, 3517, 0), new Tile(3106, 3522, 0), new Tile(3106, 3526, 0), new Tile(3106, 3530, 0), new Tile(3107, 3534, 0), new Tile(3108, 3538, 0), new Tile(3108, 3542, 0), new Tile(3108, 3546, 0), new Tile(3105, 3549, 0), new Tile(3105, 3553, 0), new Tile(3105, 3557, 0), new Tile(3105, 3561, 0), new Tile(3105, 3565, 0), new Tile(3106, 3569, 0), new Tile(3106, 3574, 0), new Tile(3106, 3578, 0), new Tile(3105, 3582, 0), new Tile(3105, 3586, 0), new Tile(3105, 3590, 0), new Tile(3105, 3594, 0), new Tile(3105, 3598, 0), new Tile(3104, 3603, 0), new Tile(3104, 3607, 0), new Tile(3106, 3611, 0), new Tile(3109, 3614, 0), new Tile(3109, 3618, 0), new Tile(3109, 3622, 0), new Tile(3111, 3626, 0), new Tile(3114, 3629, 0), new Tile(3115, 3633, 0), new Tile(3117, 3637, 0), new Tile(3117, 3641, 0), new Tile(3117, 3645, 0), new Tile(3118, 3649, 0), new Tile(3120, 3653, 0), new Tile(3121, 3658, 0), new Tile(3123, 3662, 0), new Tile(3123, 3666, 0), new Tile(3126, 3670, 0), new Tile(3126, 3674, 0), new Tile(3130, 3676, 0), new Tile(3133, 3679, 0), new Tile(3135, 3683, 0), new Tile(3135, 3687, 0), new Tile(3135, 3692, 0), new Tile(3136, 3696, 0), new Tile(3138, 3700, 0), new Tile(3138, 3704, 0)};
	
	
	@Override
	public boolean activate(){
		
		if(!data.hasGloryEquipped(ctx, data)) return false;
		if(!edgevilleBankArea.contains(ctx.players.local().tile()) && !greenDragonArea.contains(ctx.players.local().tile()))
			return true;
		if(edgevilleBankArea.contains(ctx.players.local().tile()) && ctx.inventory.select().id(data.getFoodID()).count() >= data.getStartingFoodAmount())
			return true;
		return false;
	}
	
	@Override
	public void execute(){
		
		STGreenDragons.status = "Walking to Dragons";
		
		if(!ctx.movement.running() && (ctx.client().getRunPercentage() > Random.nextInt(25, 50))){
			ctx.widgets.component(160, 27).click();
		}
		
		if(!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL)){
			Walker walker = new Walker(ctx);
			walker.walkPath(pathToDragons);
			
		}
		
		
	}
}
